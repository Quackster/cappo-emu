package cappo.protocol.messages.events.moderator;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Clients;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.Utils;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;

public class ModKickParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection cn)
  {
    if (!cn.playerData.allowModTools()) {
      return;
    }
    int UserId = cn.currentPacket.readInt();
    
    PlayerData playerData = Clients.getPlayerData(UserId);
    if ((playerData == null) || (playerData.connection == null)) {
      return;
    }
    Connection plrConnection = playerData.connection;
    if (plrConnection == null) {
      return;
    }
    Avatar avatar = plrConnection.avatar;
    if (avatar == null) {
      return;
    }
    avatar.room.removeUserFromRoom(plrConnection, true, false);
    
    String Message = cn.currentPacket.readString();
    
    Utils.AlertFromHotel(plrConnection.socket, Message);
  }
}


