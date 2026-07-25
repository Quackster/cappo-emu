package cappo.protocol.messages.events.games.snowwar;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.game.player.SnowWarPlayerData;
import cappo.protocol.messages.IncomingMessageEvent;

public class ThrowSnowballAtHumanParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Main.snowWarPlayerData.throwSnowballAtHuman(Main.currentPacket.readInt(), Main.currentPacket.readInt());
  }
}


