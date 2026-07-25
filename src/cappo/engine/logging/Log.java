package cappo.engine.logging;

import cappo.engine.Server;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class Log
  extends PrintStream
{
  private static PrintStream out;
  private static PrintStream outExceptions;
  private static String lastLog;
  
  public Log(OutputStream f)
  {
    super(f);
  }
  
  public static void Init(boolean dev, String Date)
  {
    if (!dev) {
      try
      {
        System.setOut(new Log(new FileOutputStream("./" + Server.serverId + "/log-" + Date.replace(":", "-") + ".txt", true)));
        System.setErr(new Log(new FileOutputStream("./" + Server.serverId + "/log-" + Date.replace(":", "-") + "-Exceptions.txt", true)));
      }
      catch (Exception ex)
      {
        ex.printStackTrace(System.err);
      }
    }
    outExceptions = System.err;
    out = System.out;
  }
  
  public static void printException(String Caller, Exception ex)
  {
    outExceptions.print(System.currentTimeMillis() + ": ");
    outExceptions.println("New Exception in " + Caller);
    ex.printStackTrace(outExceptions);
    outExceptions.flush();
  }
  
  public static void printThrowable(String Caller, Throwable ex)
  {
    outExceptions.print(System.currentTimeMillis() + ": ");
    outExceptions.println("New Exception in " + Caller);
    ex.printStackTrace(outExceptions);
    outExceptions.flush();
  }
  
  public static void printLog(String ex)
  {
    if ((lastLog != null) && (lastLog.equals(ex))) {
      return;
    }
    lastLog = ex;
    
    out.print(System.currentTimeMillis() + ": ");
    out.println(ex);
    out.flush();
  }
}


