package cappo.protocol.messages.composers.games.snowwar;

import cappo.engine.network.MessageWriter;
import cappo.game.games.GamesLeaderboard;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializeGame2Leaderboard;

public class WeeklyLeaderboardComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(GamesLeaderboard leaderboard)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(2012), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(Integer.valueOf(23), ClientMessage);
    SerializeGame2Leaderboard.parse(ClientMessage, leaderboard.rankedList, leaderboard.gameId);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


