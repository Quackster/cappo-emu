package cappo.protocol.messages.composers.serializers;

import cappo.engine.network.MessageWriter;
import cappo.game.games.snowwar.SnowWarRoom;
import cappo.game.games.snowwar.gameevents.AddBallToMachine;
import cappo.game.games.snowwar.gameevents.BallThrowToHuman;
import cappo.game.games.snowwar.gameevents.BallThrowToPosition;
import cappo.game.games.snowwar.gameevents.CreateSnowBall;
import cappo.game.games.snowwar.gameevents.Event;
import cappo.game.games.snowwar.gameevents.MakeSnowBall;
import cappo.game.games.snowwar.gameevents.PickBallFromGameItem;
import cappo.game.games.snowwar.gameevents.PlayerLeft;
import cappo.game.games.snowwar.gameevents.UserMove;
import cappo.protocol.messages.Composer;

public class SerializeGameStatus
{
  public static void parse(MessageWriter ClientMessage, SnowWarRoom arena, boolean isFull)
  {
    int i = 0;
    
    Composer.add(Integer.valueOf(arena.Turn), ClientMessage);
    Composer.add(Integer.valueOf(seed(arena.Turn) + arena.checksum), ClientMessage);
    
    Composer.add(Integer.valueOf(1), ClientMessage);
    
    Composer.add(ClientMessage.setSaved(Integer.valueOf(0)), ClientMessage);
    for (Event evt : arena.gameEvents)
    {
      Composer.add(Integer.valueOf(evt.EventType), ClientMessage);
      if (evt.EventType == 1) {
        SerializeGame2EventPlayerLeft.parse(ClientMessage, (PlayerLeft)evt);
      } else if (evt.EventType == 2) {
        SerializeGame2EventMove.parse(ClientMessage, (UserMove)evt);
      } else if (evt.EventType == 7) {
        SerializeGame2EventPickSnowBall.parse(ClientMessage, (MakeSnowBall)evt);
      } else if (evt.EventType == 8) {
        SerializeGame2EventCreateSnowBall.parse(ClientMessage, (CreateSnowBall)evt);
      } else if (evt.EventType == 4) {
        SerializeGame2EventBallThrowToPosition.parse(ClientMessage, (BallThrowToPosition)evt);
      } else if (evt.EventType == 3) {
        SerializeGame2EventBallThrowToHuman.parse(ClientMessage, (BallThrowToHuman)evt);
      } else if (evt.EventType == 12) {
        SerializeGame2EventPickBallFromGameItem.parse(ClientMessage, (PickBallFromGameItem)evt);
      } else if (evt.EventType == 11) {
        SerializeGame2EventAddBallToMachine.parse(ClientMessage, (AddBallToMachine)evt);
      } else {
        throw new UnsupportedOperationException("Not yet implemented");
      }
      if (!isFull) {
        evt.apply();
      }
      i++;
    }
    ClientMessage.writeSaved(Integer.valueOf(i));
  }
  
  public static int seed(int Turn)
  {
    if (Turn == 0) {
      Turn = -1;
    }
    int k = Turn << 13;
    Turn ^= k;
    k = Turn >> 17;
    Turn ^= k;
    k = Turn << 5;
    Turn ^= k;
    return Turn;
  }
}


