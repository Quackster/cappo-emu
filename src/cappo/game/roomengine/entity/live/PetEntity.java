package cappo.game.roomengine.entity.live;

import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.Utils;
import cappo.game.games.snowwar.Direction8;
import cappo.game.pets.Pet;
import cappo.game.pets.PetBase;
import cappo.game.roomengine.gamemap.GameMapBase;
import cappo.protocol.messages.composers.notifications.PetLevelNotificationComposer;

public class PetEntity
  extends LiveEntity
{
  public Pet petData;
  public String motto;
  public String look;
  private long NextThink = 0L;
  
  public PetEntity(RoomTask room, short virtualId)
  {
    super(room, virtualId);
  }
  
  public void OnSelfEnterRoom(RoomTask room)
  {
    moveTo(Utils.GetRandomNumber(0, room.model.widthX), Utils.GetRandomNumber(0, room.model.heightY));
  }
  
  public void OnTimerTick(RoomTask room)
  {
    long timenow = Utils.getTimestamp();
    if (timenow < this.NextThink) {
      return;
    }
    int r = Utils.GetRandomNumber(1, 50);
    if (r < 4)
    {
      moveTo(Utils.GetRandomNumber(1, room.model.widthX), Utils.GetRandomNumber(1, room.model.heightY));
      this.NextThink = (timenow + 4L);
    }
    else if (r == 8)
    {
      String spech = this.petData.base.getSpeech((short)3);
      if (!spech.isEmpty()) {
        if (spech.length() == 3) {
          setStatus(spech, Float.toString(this.z));
        } else {
          say(spech, 0, -1, false);
        }
      }
      this.NextThink = (timenow + 3L);
    }
    else if (r == 35)
    {
      this.petData.Experience += 10;
      if (this.petData.base.checkLevel(this.petData)) {
        room.sendMessage(PetLevelNotificationComposer.compose(this.virtualId, this.petData));
      }
    }
  }
  
  public void OnUserEnterRoom(RoomTask room, Avatar connection) {}
  
  public void OnUserLeaveRoom(RoomTask room, Connection connection) {}
  
  public void OnUserSay(RoomTask room, Avatar User, String Say)
  {
    Say = Say.toLowerCase();
    
    String petName = this.petData.name.toLowerCase();
    if (Say.equals(petName))
    {
      SetRot(Direction8.getRot(this.x, this.y, User.x, User.y));
      return;
    }
    if (Say.startsWith(petName + " "))
    {
      if (User.id != this.petData.ownerId) {
        return;
      }
      Say = Say.substring(petName.length() + 1);
      if (Utils.GetRandomNumber(1, 8) < 5)
      {
        if (this.petData.Energy < 10)
        {
          String rSpeech = this.petData.base.getSpeech((short)1);
          if (rSpeech.length() != 3) {
            say(rSpeech, 0, -1, false);
          } else {
            setStatus(rSpeech, Float.toString(this.z));
          }
        }
        else if (Say.charAt(0) == 's')
        {
          if (Say.equals("silent"))
          {
            setStatus("", "");
            
            this.petData.Experience += 8;
            this.petData.Nutrition -= 2;
            this.petData.Energy += 5;
            if (this.petData.base.checkLevel(this.petData)) {
              room.sendMessage(PetLevelNotificationComposer.compose(this.virtualId, this.petData));
            }
          }
          else if (Say.equals("stand"))
          {
            setStatus("", "");
            this.petData.Experience += 10;
            this.petData.Nutrition -= 1;
            this.petData.Energy += 5;
            if (this.petData.base.checkLevel(this.petData)) {
              room.sendMessage(PetLevelNotificationComposer.compose(this.virtualId, this.petData));
            }
          }
          else if (Say.equals("sit"))
          {
            setStatus("sit", Float.toString(this.z));
            this.petData.Experience += 10;
            this.petData.Nutrition -= 5;
            this.petData.Energy -= 5;
            if (this.petData.base.checkLevel(this.petData)) {
              room.sendMessage(PetLevelNotificationComposer.compose(this.virtualId, this.petData));
            }
          }
          else if (Say.equals("sleep"))
          {
            say("ZzzZZZzzzzZzz", 0, -1, false);
            setStatus("lay", Float.toString(this.z));
            this.petData.Experience += 5;
            this.petData.Nutrition += 10;
            this.petData.Energy += 10;
            if (this.petData.base.checkLevel(this.petData)) {
              room.sendMessage(PetLevelNotificationComposer.compose(this.virtualId, this.petData));
            }
          }
        }
        else if (Say.charAt(0) == 'h')
        {
          if (Say.equals("here"))
          {
            setStatus("", "");
            this.petData.Experience += 10;
            this.petData.Nutrition -= 10;
            this.petData.Energy -= 10;
            if (this.petData.base.checkLevel(this.petData)) {
              room.sendMessage(PetLevelNotificationComposer.compose(this.virtualId, this.petData));
            }
            moveTo(User.x + User.RotBody.getDiffX(), User.y + User.RotBody.getDiffY());
          }
        }
        else if (Say.equals("play dead"))
        {
          setStatus("ded", Float.toString(this.z));
          this.petData.Experience += 10;
          this.petData.Nutrition -= 3;
          this.petData.Energy -= 5;
          if (this.petData.base.checkLevel(this.petData)) {
            room.sendMessage(PetLevelNotificationComposer.compose(this.virtualId, this.petData));
          }
        }
        else if (Say.charAt(0) == 'd')
        {
          if (Say.equals("dead"))
          {
            setStatus("ded", Float.toString(this.z));
            this.petData.Experience += 10;
            this.petData.Nutrition -= 3;
            this.petData.Energy -= 5;
            if (this.petData.base.checkLevel(this.petData)) {
              room.sendMessage(PetLevelNotificationComposer.compose(this.virtualId, this.petData));
            }
          }
          else if (Say.equals("drink"))
          {
            setStatus("drk", Float.toString(this.z));
            say("*Drinks*", 0, -1, false);
            this.petData.Experience += 10;
            this.petData.Nutrition += 30;
            this.petData.Energy += 40;
            if (this.petData.base.checkLevel(this.petData)) {
              room.sendMessage(PetLevelNotificationComposer.compose(this.virtualId, this.petData));
            }
          }
        }
        else if (Say.equals("move"))
        {
          setStatus("", "");
          moveTo(Utils.GetRandomNumber(1, room.model.widthX), Utils.GetRandomNumber(1, room.model.heightY));
          this.petData.Experience += 10;
          this.petData.Nutrition -= 5;
          this.petData.Energy -= 10;
          if (this.petData.base.checkLevel(this.petData)) {
            room.sendMessage(PetLevelNotificationComposer.compose(this.virtualId, this.petData));
          }
        }
        else if (Say.equals("jump"))
        {
          setStatus("jmp", Float.toString(this.z));
          this.petData.Experience += 15;
          this.petData.Nutrition -= 20;
          this.petData.Energy -= 20;
          if (this.petData.base.checkLevel(this.petData)) {
            room.sendMessage(PetLevelNotificationComposer.compose(this.virtualId, this.petData));
          }
        }
        else if (Say.equals("eat"))
        {
          setStatus("eat", Float.toString(this.z));
          say("*Eats*", 0, -1, false);
          this.petData.Experience += 10;
          this.petData.Nutrition += 10;
          this.petData.Energy += 10;
          if (this.petData.base.checkLevel(this.petData)) {
            room.sendMessage(PetLevelNotificationComposer.compose(this.virtualId, this.petData));
          }
        }
        else if (Say.equals("free"))
        {
          this.petData.Experience += 5;
          this.petData.Nutrition += 5;
          this.petData.Energy -= 20;
          if (this.petData.base.checkLevel(this.petData)) {
            room.sendMessage(PetLevelNotificationComposer.compose(this.virtualId, this.petData));
          }
        }
        else
        {
          String rSpeech = this.petData.base.getSpeech((short)0);
          if (rSpeech.length() != 3) {
            say(rSpeech, 0, -1, false);
          } else {
            setStatus(rSpeech, Float.toString(this.z));
          }
        }
      }
      else
      {
        String rSpeech = this.petData.base.getSpeech((short)2);
        if (rSpeech.length() != 3) {
          say(rSpeech, 0, -1, false);
        } else {
          setStatus(rSpeech, Float.toString(this.z));
        }
        this.petData.Energy -= 10;
      }
    }
  }
}


