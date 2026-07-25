package cappo.protocol.messages.composers.handshake;

import cappo.engine.Server;
import cappo.engine.network.MessageWriter;
import cappo.engine.player.Connection;
import cappo.game.collections.Utils;
import cappo.game.player.AvatarLook;
import cappo.game.player.PlayerData;
import cappo.protocol.messages.Composer;
import java.text.SimpleDateFormat;

public class UserInfoComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(Connection cn, Boolean ClientStreamEventsAllow)
  {
    PlayerData playerData = cn.getPlayerData();
    
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(playerData.userId), ClientMessage);
    Composer.add(playerData.userName, ClientMessage);
    Composer.add(playerData.avatarLook.toString(), ClientMessage);
    Composer.add(playerData.sex == 1 ? "M" : "F", ClientMessage);
    Composer.add(playerData.motto, ClientMessage);
    Composer.add(playerData.getRealName(), ClientMessage);
    Composer.add(Boolean.valueOf(false), ClientMessage);
    Composer.add(Integer.valueOf(cn.respects), ClientMessage);
    Composer.add(Integer.valueOf(cn.dailyRespectPoints), ClientMessage);
    Composer.add(Integer.valueOf(cn.dailyPetRespectPoints), ClientMessage);
    Composer.add(ClientStreamEventsAllow, ClientMessage);
    Composer.add(Server.date.format(Utils.GetDate(playerData.lastVisit * 1000L)), ClientMessage);
    Composer.add(Boolean.valueOf(cn.haveFlag(4)), ClientMessage);
    Composer.add(Boolean.valueOf(false), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


