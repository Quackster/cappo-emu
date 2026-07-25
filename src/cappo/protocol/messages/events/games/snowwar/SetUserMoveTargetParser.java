package cappo.protocol.messages.events.games.snowwar;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.game.games.snowwar.gameobjects.HumanGameObject;
import cappo.game.player.SnowWarPlayerData;
import cappo.protocol.messages.IncomingMessageEvent;

public class SetUserMoveTargetParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    if (Main.snowWarPlayerData.currentSnowWar == null) {
      return;
    }
    int x = Main.currentPacket.readInt();
    int y = Main.currentPacket.readInt();
    
    Main.currentPacket.readInt();
    Main.currentPacket.readInt();
    if (Main.snowWarPlayerData.humanObject.canWalkTo(x, y)) {
      Main.snowWarPlayerData.playerMove(x, y);
    }
  }
}


