package cappo.game.roomengine.chat.wf;

import cappo.engine.database.DBResult;
import cappo.engine.database.Database;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class WordFilter
{
  private static Map<String, WordFilterAction> wordMap = new HashMap();
  
  public static void init(DBResult result)
    throws Exception
  {
    wordMap.clear();
    
    WordFilterAction.init();
    
    Database.query(result, "SELECT * FROM wordfilter;", new Object[0]);
    while (result.data.next()) {
      wordMap.put(result.data.getString("word"), WordFilterAction.actions[result.data.getInt("action")]);
    }
  }
  
  public static WordFilterAction getAction(String text)
  {
    String tmp = cleanText(text);
    for (String key : wordMap.keySet()) {
      if (tmp.contains(key)) {
        return (WordFilterAction)wordMap.get(key);
      }
    }
    return null;
  }
  
  public static String cleanText(String text)
  {
    StringBuilder str = new StringBuilder(text.length());
    Character lastChar = null;
    for (char c : text.toLowerCase().toCharArray()) {
      if ((lastChar == null) || (c != lastChar.charValue())) {
        if ((c > '`') && (c < '{'))
        {
          lastChar = Character.valueOf(c);
          str.append(c);
        }
      }
    }
    return str.toString();
  }
}


