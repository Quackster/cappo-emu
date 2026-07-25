package cappo.protocol.messages.composers.serializers;

import cappo.engine.network.MessageWriter;
import cappo.engine.player.Connection;
import cappo.game.games.snowwar.gameobjects.HumanGameObject;
import cappo.game.player.AvatarLook;
import cappo.game.player.PlayerData;
import cappo.game.player.SnowWarPlayerData;
import cappo.protocol.messages.Composer;

public class SerializeGame2Player
{
  public static void parse(MessageWriter ClientMessage, Connection cn)
  {
    Composer.add(Integer.valueOf(cn.playerData.userId), ClientMessage);
    Composer.add(cn.playerData.userName, ClientMessage);
    Composer.add(cn.playerData.avatarLook.toString(), ClientMessage);
    Composer.add(cn.playerData.sex == 1 ? "M" : "F", ClientMessage);
    Composer.add(Integer.valueOf(cn.snowWarPlayerData.humanObject.team), ClientMessage);
    Composer.add(Integer.valueOf(cn.snowWarPlayerData.snowLevel), ClientMessage);
    Composer.add(Integer.valueOf(cn.snowWarPlayerData.score), ClientMessage);
    Composer.add(Integer.valueOf(cn.snowWarPlayerData.PointsNeed), ClientMessage);
  }
}


