package cappo.game.roomengine.chat.wf;

import cappo.engine.player.Connection;
import cappo.game.collections.Utils;

public class WordFilterActionAlert
  extends WordFilterAction
{
  public boolean run(Connection cn)
  {
    Utils.AlertFromHotel(cn.socket, cappo.game.utils.lang.LangTexts.texts[7]);
    return true;
  }
}


