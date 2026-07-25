package cappo.protocol.messages.events.inventory.badges;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.Badge;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.users.UserBadgesComposer;
import java.util.Map;

public class SetActivatedBadgesParser
  extends IncomingMessageEvent
{
  public static final int SLOTS = 5;
  
  public void messageReceived(Connection Main)
  {
    for (Badge badge : Main.badgesSelected.values())
    {
      badge.badgeSlot = 0;
      badge.needInsert = true;
    }
    Main.badgesSelected.clear();
    for (int i = 0; i < 5; i++)
    {
      int slot = Main.currentPacket.readInt();
      Badge badge = (Badge)Main.badges.get(Main.currentPacket.readString());
      if (badge != null)
      {
        badge.badgeSlot = slot;
        badge.needInsert = true;
        Main.badgesSelected.put(Integer.valueOf(badge.badgeSlot), badge);
      }
    }
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    avatar.room.sendMessage(UserBadgesComposer.compose(Main.playerData.userId, Main.badgesSelected.values()));
  }
}


