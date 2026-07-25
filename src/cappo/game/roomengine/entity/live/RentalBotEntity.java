package cappo.game.roomengine.entity.live;

import cappo.engine.threadpools.RoomTask;
import cappo.game.bots.RentalBot;
import cappo.game.collections.Utils;
import cappo.game.roomengine.gamemap.GameMapBase;

public class RentalBotEntity
  extends LiveEntity
{
  public RentalBot botData;
  private long ticks = 0L;
  private long nextTick = 0L;
  
  public RentalBotEntity(RoomTask room, short virtualId)
  {
    super(room, virtualId);
  }
  
  public void OnSelfEnterRoom(RoomTask room)
  {
    moveTo(Utils.GetRandomNumber(0, room.model.widthX), Utils.GetRandomNumber(0, room.model.heightY));
  }
  
  public void OnTimerTick(RoomTask room)
  {
    if (++this.ticks < this.nextTick) {
      return;
    }
    this.nextTick = (this.ticks + 2L);
    if ((this.botData.walkRandomEnabled) && 
      (Utils.GetRandomNumber(0, 10) < 2)) {
      moveTo(Utils.GetRandomNumber(1, room.model.widthX), Utils.GetRandomNumber(1, room.model.heightY));
    }
    if ((this.botData.chatAuto) && 
      (this.ticks >= this.botData.nextChat))
    {
      String txt = this.botData.getSpeech();
      if ((txt != null) && (!txt.isEmpty())) {
        say(txt, 2, -1, false);
      }
      this.botData.nextChat = (this.ticks + this.botData.chatDelay * 2);
    }
  }
}


