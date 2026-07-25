package cappo.protocol.messages.events.games.snowwar;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.game.games.snowwar.SnowWarRoom;
import cappo.game.games.snowwar.gameobjects.HumanGameObject;
import cappo.game.player.PlayerData;
import cappo.game.player.SnowWarPlayerData;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.games.snowwar.GameChatFromPlayerComposer;

public class GameChatParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    SnowWarPlayerData snowPlayer = Main.snowWarPlayerData;
    if (snowPlayer == null) {
      return;
    }
    SnowWarRoom room = snowPlayer.currentSnowWar;
    if (room == null) {
      return;
    }
    String say = Main.currentPacket.readString();
    if ((say.startsWith(":")) && 
      (Main.playerData.allowDataReload()) && 
      (say.startsWith(":info")))
    {
      HumanGameObject human = Main.snowWarPlayerData.humanObject;
      String local5 = "";
      local5 = local5 + human.getVariable(2) + ",";
      local5 = local5 + human.getVariable(3) + ",";
      local5 = local5 + human.getVariable(6) + ",";
      
      room.broadcast(GameChatFromPlayerComposer.compose(snowPlayer.player.userId, local5));
    }
    room.broadcast(GameChatFromPlayerComposer.compose(snowPlayer.player.userId, say));
  }
}


