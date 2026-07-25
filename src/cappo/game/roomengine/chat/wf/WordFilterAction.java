package cappo.game.roomengine.chat.wf;

import cappo.engine.player.Connection;

public abstract class WordFilterAction
{
  public static final int ALERT = 0;
  public static final int SPAM = 1;
  public static final int COUNT = 2;
  public static WordFilterAction[] actions = new WordFilterAction[2];
  
  public static void init()
  {
    actions[0] = new WordFilterActionAlert();
    actions[1] = new WordFilterActionSpammer();
  }
  
  public abstract boolean run(Connection paramConnection);
}


