package cappo.game.polls;

import cappo.engine.database.DBResult;
import cappo.engine.database.Database;
import java.sql.ResultSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PollManager
{
  public static Map<Integer, Poll> roomPolls = new ConcurrentHashMap();
  public static Map<Integer, Poll> polls = new ConcurrentHashMap();
  
  public static void load(DBResult result)
    throws Exception
  {
    roomPolls.clear();
    polls.clear();
    
    Database.query(result, "SELECT * FROM poll_data WHERE active = 1;", new Object[0]);
    while (result.data.next())
    {
      Poll poll = new Poll();
      poll.id = result.data.getInt("id");
      poll.title = result.data.getString("title");
      poll.thanks = result.data.getString("thanks");
      polls.put(Integer.valueOf(poll.id), poll);
    }
    Database.query(result, "SELECT * FROM poll_questions;", new Object[0]);
    while (result.data.next())
    {
      Poll poll = (Poll)polls.get(Integer.valueOf(result.data.getInt("poll")));
      if (poll != null)
      {
        PollQuestion question = new PollQuestion();
        question.id = result.data.getInt("id");
        question.orderid = (poll.questions.size() + 1);
        question.type = ((byte)result.data.getInt("type"));
        question.text = result.data.getString("question");
        if ((question.type == 1) || (question.type == 2)) {
          question.answers = result.data.getString("answers").split(";");
        }
        poll.questions.put(Integer.valueOf(question.id), question);
      }
    }
    Database.query(result, "SELECT * FROM room_poll;", new Object[0]);
    while (result.data.next())
    {
      Poll poll = (Poll)polls.get(Integer.valueOf(result.data.getInt("poll")));
      if (poll != null) {
        roomPolls.put(Integer.valueOf(result.data.getInt("roomid")), poll);
      }
    }
  }
}


