package cappo.game.roomengine.entity.live;

import cappo.engine.threadpools.RoomTask;
import cappo.game.games.snowwar.Direction8;
import cappo.game.roomengine.gamemap.GameMapBase;
import cappo.game.roomengine.roomevents.User_WALK;
import cappo.protocol.messages.composers.room.chat.ChatComposer;
import cappo.protocol.messages.composers.room.chat.ShoutComposer;
import java.util.ArrayList;

public class LiveEntity
{
  public static final int ENTITY_USER = 1;
  public static final int ENTITY_PET = 2;
  public static final int ENTITY_BOT = 3;
  public static final int ENTITY_RENTABLE_BOT = 4;
  public int entityType;
  public boolean allowOverride;
  public short virtualId;
  public RoomTask room;
  public User_WALK evtWalk;
  public int x;
  public int y;
  public int xy;
  public float z;
  public Direction8 RotBody = Direction8.N;
  public Direction8 RotHead = Direction8.N;
  public String Status = "";
  public LiveEntity ridingEntity;
  
  public LiveEntity(RoomTask curentRoom, short VirtualId)
  {
    this.room = curentRoom;
    this.virtualId = VirtualId;
    this.evtWalk = new User_WALK(this);
    
    this.room.userUpdateNeeded(this);
  }
  
  public int hashCode()
  {
    return this.virtualId;
  }
  
  public boolean HaveStatus(String Key)
  {
    return this.Status.contains(Key);
  }
  
  public void SetPos(int X, int Y, float Z)
  {
    this.x = X;
    this.y = Y;
    this.xy = (X + Y * this.room.model.widthX);
    this.z = Z;
  }
  
  public void SetRot(Direction8 Rotation)
  {
    SetRot(Rotation, false);
  }
  
  public void SetRot(Direction8 Rotation, boolean HeadOnly)
  {
    if (this.Status.contains("lay")) {
      return;
    }
    int diff = this.RotBody.getRot() - Rotation.getRot();
    this.RotHead = this.RotBody;
    if ((this.Status.contains("sit")) || (HeadOnly))
    {
      if (diff > 0) {
        this.RotHead = this.RotBody.rotateDirection45Degrees(false);
      } else if (diff < 0) {
        this.RotHead = this.RotBody.rotateDirection45Degrees(true);
      }
    }
    else if ((diff < -1) || (diff > 1))
    {
      this.RotBody = Rotation;
      this.RotHead = Rotation;
    }
    else
    {
      this.RotHead = Rotation;
    }
    this.room.userUpdateNeeded(this);
  }
  
  public void setStatus(String Key, String Value)
  {
    String status = "/";
    if (!Key.isEmpty()) {
      status = status + Key + " " + Value + "/";
    }
    status = status + "/";
    
    this.Status = status;
    this.room.userUpdateNeeded(this);
  }
  
  public void say(String message, int styleId, int sayId, boolean isShout)
  {
    if (isShout) {
      this.room.sendMessage(ShoutComposer.compose(this.virtualId, message, 0, styleId, new ArrayList(), sayId));
    } else {
      this.room.sendMessage(ChatComposer.compose(this.virtualId, message, 0, styleId, new ArrayList(), sayId));
    }
  }
  
  public void clearMovement()
  {
    if (this.evtWalk.isWalking) {
      this.evtWalk.isWalking = false;
    }
  }
  
  public void moveTo(int X, int Y)
  {
    this.evtWalk.walk(this.room, X, Y);
  }
}


