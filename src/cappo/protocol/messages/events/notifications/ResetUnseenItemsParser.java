package cappo.protocol.messages.events.notifications;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.game.collections.UnseenItems;
import cappo.game.player.data.AvatarData;
import cappo.protocol.messages.IncomingMessageEvent;

public class ResetUnseenItemsParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Main.avatarData.UnseenItems.ResetItems(Main.currentPacket.readInt());
  }
}


