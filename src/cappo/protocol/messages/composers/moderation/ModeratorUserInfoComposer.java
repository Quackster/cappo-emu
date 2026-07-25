package cappo.protocol.messages.composers.moderation;

import cappo.engine.network.MessageWriter;
import cappo.game.collections.Utils;
import cappo.game.player.AvatarLook;
import cappo.game.player.PlayerData;
import cappo.protocol.messages.Composer;

public class ModeratorUserInfoComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(PlayerData playerData)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(playerData.userId), ClientMessage);
    Composer.add(playerData.userName, ClientMessage);
    Composer.add(playerData.avatarLook.toString(), ClientMessage);
    Composer.add(Long.valueOf((Utils.getTimestamp() - playerData.registerDate) / 60L), ClientMessage);
    Composer.add(Long.valueOf((Utils.getTimestamp() - playerData.lastVisit) / 60L), ClientMessage);
    Composer.add(Boolean.valueOf(playerData.connection != null), ClientMessage);
    Composer.add(Integer.valueOf(playerData.cfhs), ClientMessage);
    Composer.add(Integer.valueOf(playerData.cfhs_abusive), ClientMessage);
    Composer.add(Integer.valueOf(playerData.cautions), ClientMessage);
    Composer.add(Integer.valueOf(playerData.bans), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add("lastPurchase", ClientMessage);
    Composer.add(Integer.valueOf(playerData.userId), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(playerData.email, ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


