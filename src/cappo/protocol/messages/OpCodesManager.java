package cappo.protocol.messages;

import cappo.engine.Server;
import cappo.engine.logging.Log;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class OpCodesManager
{
  private static Map<String, Class<?>> composersMap;
  private static Map<String, IncomingMessageEvent> parsersMap;
  private static String composerChecksum;
  private static String parserChecksum;
  
  public static void init()
    throws Exception
  {
    getObjects();
    OpCodes.registerComposers();
    OpCodes.registerParsers();
    checkComposerOverrides();
    checkParserOverrides();
  }
  
  public static void checkComposerOverrides()
    throws Exception
  {
    ClassLoader classLoader = Server.class.getClassLoader();
    URL resURL = classLoader.getResource("Composers.properties");
    if (resURL == null) {
      return;
    }
    URLConnection resConn = resURL.openConnection();
    resConn.setUseCaches(false);
    InputStream in = resConn.getInputStream();
    
    Properties props = new Properties();
    props.load(in);
    in.close();
    
    String check = props.getProperty("checksum", "1");
    if ((composerChecksum != null) && (composerChecksum.equals(check))) {
      return;
    }
    composerChecksum = check;
    for (Map.Entry<Object, Object> e : props.entrySet())
    {
      String key = (String)e.getKey();
      if (!key.equals("checksum"))
      {
        int header = Integer.parseInt((String)e.getValue());
        setComposerId(key, header);
      }
    }
  }
  
  public static void checkParserOverrides()
    throws Exception
  {
    ClassLoader classLoader = Server.class.getClassLoader();
    URL resURL = classLoader.getResource("Parsers.properties");
    if (resURL == null) {
      return;
    }
    URLConnection resConn = resURL.openConnection();
    resConn.setUseCaches(false);
    InputStream in = resConn.getInputStream();
    
    Properties props = new Properties();
    props.load(in);
    in.close();
    
    String check = props.getProperty("checksum", "1");
    if ((parserChecksum != null) && (parserChecksum.equals(check))) {
      return;
    }
    parserChecksum = check;
    for (Map.Entry<Object, Object> e : props.entrySet())
    {
      String key = (String)e.getKey();
      if (!key.equals("checksum"))
      {
        int header = Integer.parseInt((String)e.getValue());
        setParserId(header, key);
      }
    }
  }
  
  public static void setComposerId(String key, int header)
    throws Exception
  {
    Class<?> cls = (Class)composersMap.get(key);
    if (cls == null)
    {
      Log.printLog("NULL setComposerId:" + key);
      return;
    }
    Log.printLog("setComposerId:" + key + ":" + header);
    cls.getField("HEADER").setInt(null, header);
  }
  
  public static void setParserId(int header, String key)
    throws Exception
  {
    IncomingMessageEvent event = (IncomingMessageEvent)parsersMap.get(key);
    if (event == null)
    {
      Log.printLog("NULL setParserId:" + key);
      return;
    }
    Log.printLog("setParserId:" + key + ":" + header);
    IncomingMessageEvent.callBacks[event.HEADER] = null;
    IncomingMessageEvent.callBacks[header] = event;
    event.HEADER = header;
  }
  
  private static void getObjects()
    throws Exception
  {
    composersMap = new HashMap();
    parsersMap = new HashMap();
    
    ClassLoader classLoader = Server.class.getClassLoader();
    URL packageURL = classLoader.getResource("cappo/protocol/messages/");
    
    String prefix1 = "cappo/protocol/messages/composers/";
    String prefix2 = "cappo/protocol/messages/events/";
    int len1 = prefix1.length();int len2 = prefix2.length();
    if (packageURL.getProtocol().equals("jar"))
    {
      String jarFileName = URLDecoder.decode(packageURL.getFile(), "UTF-8");
      jarFileName = jarFileName.substring(5, jarFileName.indexOf("!"));
      
      JarFile jf = new JarFile(jarFileName);
      Enumeration<JarEntry> jarEntries = jf.entries();
      while (jarEntries.hasMoreElements())
      {
        String entryName = ((JarEntry)jarEntries.nextElement()).getName();
        if (entryName.endsWith(".class"))
        {
          entryName = entryName.substring(0, entryName.length() - 6);
          if (entryName.startsWith(prefix1))
          {
            entryName = entryName.replaceAll("/", "\\.");
            String key = entryName.substring(len1);
            Class<?> value = Class.forName(entryName);
            composersMap.put(key, value);
          }
          else if (entryName.startsWith(prefix2))
          {
            entryName = entryName.replaceAll("/", "\\.");
            String key = entryName.substring(len2);
            Class<?> cls = Class.forName(entryName);
            Object instance = cls.newInstance();
            if ((instance instanceof IncomingMessageEvent))
            {
              IncomingMessageEvent value = (IncomingMessageEvent)instance;
              parsersMap.put(key, value);
            }
          }
        }
      }
      jf.close();
    }
    else if (packageURL.getProtocol().equals("file"))
    {
      File packageDir = new File(packageURL.toURI());
      java.util.ArrayDeque<File> stack = new java.util.ArrayDeque<File>();
      stack.push(packageDir);
      while (!stack.isEmpty())
      {
        File f = stack.pop();
        if (f.isDirectory())
        {
          File[] children = f.listFiles();
          if (children != null)
          {
            for (int i = 0; i < children.length; i++) stack.push(children[i]);
          }
        }
        else if (f.getName().endsWith(".class"))
        {
          String entryName = packageDir.toURI().relativize(f.toURI()).getPath();
          entryName = ("cappo/protocol/messages/" + entryName).substring(0, ("cappo/protocol/messages/" + entryName).length() - 6);
          if (entryName.startsWith(prefix1))
          {
            entryName = entryName.replaceAll("/", "\\.");
            String key = entryName.substring(len1);
            Class<?> value = Class.forName(entryName);
            composersMap.put(key, value);
          }
          else if (entryName.startsWith(prefix2))
          {
            entryName = entryName.replaceAll("/", "\\.");
            String key = entryName.substring(len2);
            Class<?> cls = Class.forName(entryName);
            Object instance = cls.newInstance();
            if ((instance instanceof IncomingMessageEvent))
            {
              IncomingMessageEvent value = (IncomingMessageEvent)instance;
              parsersMap.put(key, value);
          }
          }
        }
      }
    }
  }
}


