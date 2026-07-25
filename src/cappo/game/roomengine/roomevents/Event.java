package cappo.game.roomengine.roomevents;

import cappo.engine.threadpools.RoomTask;

public abstract class Event
{
  public int Ticks;
  public Integer eventId;
  
  public abstract void run(RoomTask paramRoomTask);
}


