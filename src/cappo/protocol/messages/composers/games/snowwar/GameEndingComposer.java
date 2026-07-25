package cappo.protocol.messages.composers.games.snowwar;

import cappo.engine.network.MessageWriter;
import cappo.game.games.snowwar.SnowWar;
import cappo.game.games.snowwar.SnowWarRoom;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializeGame2GameResult;
import cappo.protocol.messages.composers.serializers.SerializeGame2SnowWarGameStats;
import cappo.protocol.messages.composers.serializers.SerializeGame2TeamScoreData;
import java.util.Map;

public class GameEndingComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(SnowWarRoom arena)
  {
    MessageWriter ClientMessage = new MessageWriter(10000);
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    SerializeGame2GameResult.parse(ClientMessage, arena);
    Composer.add(Integer.valueOf(SnowWar.TEAMS.length), ClientMessage);
    for (int teamId : SnowWar.TEAMS) {
      SerializeGame2TeamScoreData.parse(ClientMessage, teamId, arena.TeamScore[(teamId - 1)], ((Map)arena.TeamPlayers.get(Integer.valueOf(teamId))).values());
    }
    SerializeGame2SnowWarGameStats.parse(ClientMessage, arena);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


