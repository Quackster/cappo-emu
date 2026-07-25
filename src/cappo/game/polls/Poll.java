package cappo.game.polls;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Poll
{
  public int id;
  public String title;
  public String thanks;
  public Map<Integer, PollQuestion> questions = new ConcurrentHashMap();
}


