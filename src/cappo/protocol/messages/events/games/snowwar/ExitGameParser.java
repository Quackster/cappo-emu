package cappo.protocol.messages.events.games.snowwar;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.game.player.SnowWarPlayerData;
import cappo.protocol.messages.IncomingMessageEvent;

public class ExitGameParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Main.currentPacket.readBoolean();
    Main.snowWarPlayerData.userLeft();
  }
}


