package cappo.protocol.messages.events.room.action;

import cappo.engine.player.Connection;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;

public class DropCarryObjectParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    if (avatar.carryItemID > 0) {
      avatar.CarryItem(0);
    }
  }
}


