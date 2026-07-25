package cappo.protocol.messages.composers.serializers;

import cappo.engine.network.MessageWriter;
import cappo.game.player.AvatarLook;
import cappo.game.player.PlayerData;
import cappo.game.player.SnowWarPlayerData;
import cappo.protocol.messages.Composer;
import java.util.List;

public class SerializeGame2Leaderboard
{
  public static void parse(MessageWriter clientMessage, List<SnowWarPlayerData> rankedList, int gameId)
  {
    Composer.add(Integer.valueOf(rankedList.size()), clientMessage);
    for (SnowWarPlayerData player : rankedList)
    {
      Composer.add(Integer.valueOf(player.player.userId), clientMessage);
      Composer.add(Integer.valueOf(player.score), clientMessage);
      Composer.add(Integer.valueOf(player.rank), clientMessage);
      Composer.add(player.player.userName, clientMessage);
      Composer.add(player.player.avatarLook.toString(), clientMessage);
      Composer.add(player.player.sex == 1 ? "M" : "F", clientMessage);
    }
    Composer.add(Integer.valueOf(0), clientMessage);
    Composer.add(Integer.valueOf(gameId), clientMessage);
  }
}


