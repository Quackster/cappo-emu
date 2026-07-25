package cappo.protocol.messages.events.games.snowwar;

import cappo.engine.player.Connection;
import cappo.game.games.snowwar.SnowWarRoom;
import cappo.game.player.SnowWarPlayerData;
import cappo.protocol.messages.IncomingMessageEvent;
import java.util.List;

public class RequestFullStatusUpdateParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    SnowWarRoom room = Main.snowWarPlayerData.currentSnowWar;
    if (room == null) {
      return;
    }
    synchronized (room.fullGameStatusQueue)
    {
      room.fullGameStatusQueue.add(Main.socket);
    }
  }
}


