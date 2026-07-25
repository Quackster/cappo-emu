package cappo.protocol.messages.events.sound;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.player.inventory.PlayerInventory;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.sound.UserSongDisksInventoryComposer;

public class GetUserSongDisksParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    QueueWriter.write(Main.socket, UserSongDisksInventoryComposer.compose(Main.inventory.getSongs()));
  }
}


