package cappo.game.roomengine.roomevents;

import cappo.engine.network.MessageWriter;
import cappo.engine.threadpools.RoomTask;

public class UserChat
  extends Event
{
  private MessageWriter message;
  private boolean isTalking;
  
  public void run(RoomTask room)
  {
    if (this.isTalking)
    {
      this.isTalking = false;
      room.sendMessage(this.message);
    }
  }
  
  public void talk(RoomTask room, MessageWriter msg)
  {
    if (!this.isTalking)
    {
      this.isTalking = true;
      
      this.message = msg;
      room.addUserEvent(this, 0);
    }
  }
  
  public void stop(RoomTask room)
  {
    this.isTalking = false;
  }
}


