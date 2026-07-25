package cappo.game.roomengine.entity.item.floor;

import cappo.engine.database.Database;
import cappo.engine.logging.Log;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.BaseItem;
import cappo.game.collections.BaseItem.FurniLogic;
import cappo.game.collections.BaseItem.ItemType;
import cappo.game.collections.BflyData;
import cappo.game.games.snowwar.Direction8;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.SquareFlagManager;
import cappo.game.roomengine.entity.item.Item;
import cappo.game.roomengine.entity.item.RoomItemData;
import cappo.game.roomengine.entity.item.extradata.CrackableExtraData;
import cappo.game.roomengine.entity.item.extradata.ExtraData1;
import cappo.game.roomengine.entity.item.extradata.ExtraDataBase;
import cappo.game.roomengine.entity.item.extradata.IntArrayStuffData;
import cappo.game.roomengine.entity.item.extradata.MapStuffData;
import cappo.game.roomengine.entity.item.extradata.StringArrayStuffData;
import cappo.game.roomengine.entity.item.extradata.StringStuffData;
import cappo.game.roomengine.entity.item.extradata.StuffDataReader;
import cappo.game.roomengine.entity.item.floor.wired.AtachedWireds;
import cappo.game.roomengine.entity.item.floor.wired.WiredItemBase;
import cappo.game.roomengine.entity.item.floor.wired.condition.FurniHasUser;
import cappo.game.roomengine.entity.item.floor.wired.effect.GiveReward;
import cappo.game.roomengine.entity.item.floor.wired.effect.MoveRotateItemAction;
import cappo.game.roomengine.entity.item.floor.wired.effect.ShowMessageAction;
import cappo.game.roomengine.entity.item.floor.wired.effect.TeleportToItemAction;
import cappo.game.roomengine.entity.item.floor.wired.effect.ToggleItemStateAction;
import cappo.game.roomengine.entity.item.floor.wired.trigger.GameEndsTrigger;
import cappo.game.roomengine.entity.item.floor.wired.trigger.GameStartsTrigger;
import cappo.game.roomengine.entity.item.floor.wired.trigger.RepeatTrigger;
import cappo.game.roomengine.entity.item.floor.wired.trigger.TimerResetTrigger;
import cappo.game.roomengine.entity.item.floor.wired.trigger.UserEntersRoomTrigger;
import cappo.game.roomengine.entity.item.floor.wired.trigger.UserSaysPhraseTrigger;
import cappo.game.roomengine.entity.item.floor.wired.trigger.UserStepsOffItemTrigger;
import cappo.game.roomengine.entity.item.floor.wired.trigger.UserStepsOnItemTrigger;
import cappo.game.roomengine.entity.item.floor.wired.trigger.UserUsesItemTrigger;
import cappo.game.roomengine.itemInteractor.Interactor;
import cappo.game.roomengine.itemInteractor.Interactor.InteractorType;
import java.util.List;

public abstract class FloorItem
  extends Item
{
  private int extraParam;
  private RoomFloorItemData data;
  private AtachedWireds attachedWireds;
  
  public static FloorItem createItem(PlayerData owner, int ref, int Id, StuffDataReader data, int extraparam, BaseItem baseItem)
  {
    FloorItem userItem = null;
    if (baseItem.logic == BaseItem.FurniLogic.CRACKABLE)
    {
      userItem = new CrackeableItem();
      if (data.type != 7)
      {
        Log.printLog("BAD StuffDataReader = " + data.type + " - Logic = " + baseItem.logic);
        return null;
      }
    }
    else if (baseItem.interactorType == Interactor.InteractorType.walkeablechange)
    {
      userItem = new WalkeableChangeItem();
    }
    else if (baseItem.interactorType == Interactor.InteractorType.roller)
    {
      userItem = new RollerItem();
    }
    else if (baseItem.interactorType == Interactor.InteractorType.gift)
    {
      userItem = new PresentItem();
    }
    else if (baseItem.logic == BaseItem.FurniLogic.MANNEQUIN)
    {
      userItem = new OutFitItem();
      if (data.type != 1)
      {
        Log.printLog("BAD StuffDataReader = " + data.type + " - Logic = " + baseItem.logic);
        return null;
      }
    }
    else if (baseItem.itemCategory == 8)
    {
      userItem = new SongItem();
    }
    else if (baseItem.interactor == Interactor.iterWired)
    {
      if (baseItem.itemType == BaseItem.ItemType.WIRED_CONDITION)
      {
        if (baseItem.interactorType == Interactor.InteractorType.conditionfurnishaveusers) {
          userItem = new FurniHasUser();
        }
      }
      else if (baseItem.itemType == BaseItem.ItemType.WIRED_EFFECT)
      {
        if (baseItem.interactorType == Interactor.InteractorType.actionshowmessage) {
          userItem = new ShowMessageAction();
        } else if (baseItem.interactorType == Interactor.InteractorType.actionteleportto) {
          userItem = new TeleportToItemAction();
        } else if (baseItem.interactorType == Interactor.InteractorType.actionmoverotate) {
          userItem = new MoveRotateItemAction();
        } else if (baseItem.interactorType == Interactor.InteractorType.actiontogglestate) {
          userItem = new ToggleItemStateAction();
        } else if (baseItem.interactorType == Interactor.InteractorType.actiongivereward) {
          userItem = new GiveReward();
        }
      }
      else if (baseItem.itemType == BaseItem.ItemType.WIRED_TRIGGER) {
        if (baseItem.interactorType == Interactor.InteractorType.triggerroomenter) {
          userItem = new UserEntersRoomTrigger();
        } else if (baseItem.interactorType == Interactor.InteractorType.triggergameend) {
          userItem = new GameEndsTrigger();
        } else if (baseItem.interactorType == Interactor.InteractorType.triggergamestart) {
          userItem = new GameStartsTrigger();
        } else if (baseItem.interactorType == Interactor.InteractorType.triggerstatechanged) {
          userItem = new UserUsesItemTrigger();
        } else if (baseItem.interactorType == Interactor.InteractorType.triggeronusersay) {
          userItem = new UserSaysPhraseTrigger();
        } else if (baseItem.interactorType == Interactor.InteractorType.triggerwalkofffurni) {
          userItem = new UserStepsOffItemTrigger();
        } else if (baseItem.interactorType == Interactor.InteractorType.triggerwalkonfurni) {
          userItem = new UserStepsOnItemTrigger();
        } else if (baseItem.interactorType == Interactor.InteractorType.triggerrepeater) {
          userItem = new RepeatTrigger();
        } else if (baseItem.interactorType == Interactor.InteractorType.triggertimer) {
          userItem = new TimerResetTrigger();
        }
      }
      if (userItem == null) {
        Log.printLog("UnRegistered Wired Type: " + baseItem.interactorType);
      }
    }
    if (userItem == null) {
      userItem = new GenericFloorItem();
    }
    userItem.refId = ref;
    userItem.itemId = Id;
    userItem.baseItem = baseItem;
    userItem.owner = owner;
    
    userItem.setExtraParam(extraparam);
    if (data.type == 0)
    {
      StringStuffData stuffdata = new StringStuffData(data);
      userItem.extraData = stuffdata;
      try
      {
        GenericFloorItem floor = (GenericFloorItem)userItem;
        floor.setIntData(Integer.parseInt(stuffdata.extraData));
      }
      catch (Exception localException) {}
    }
    else if (data.type == 1)
    {
      userItem.extraData = new MapStuffData(data);
      if (baseItem.logic == BaseItem.FurniLogic.MANNEQUIN)
      {
        OutFitItem outfit = (OutFitItem)userItem;
        outfit.getAvatarLook();
      }
    }
    else if (data.type == 2)
    {
      userItem.extraData = new StringArrayStuffData(data);
    }
    else if (data.type == 3)
    {
      userItem.extraData = new ExtraData1(data);
    }
    else if (data.type == 5)
    {
      userItem.extraData = new IntArrayStuffData(data);
    }
    else if (data.type == 7)
    {
      userItem.extraData = new CrackableExtraData(data);
    }
    else
    {
      Log.printLog("BAD EXTRATYPE = " + data.type + " - BASEID = " + baseItem.Id);
      return null;
    }
    return userItem;
  }
  
  public void addAttachedWired(WiredItemBase wired)
  {
    if (this.attachedWireds == null) {
      this.attachedWireds = new AtachedWireds(wired);
    } else {
      this.attachedWireds.addWired(wired);
    }
  }
  
  public void removeAttachedWired(int itemId)
  {
    if (this.attachedWireds != null) {
      this.attachedWireds.removeWired(itemId);
    }
  }
  
  public void itemMoved(int prevXy, Direction8 prevDir)
  {
    if (this.attachedWireds != null) {
      this.attachedWireds.itemMoved(this, prevXy, prevDir);
    }
  }
  
  public void itemPick(int prevXy, Direction8 prevDir)
  {
    if (this.attachedWireds != null)
    {
      this.attachedWireds.itemPicked(this, prevXy, prevDir);
      this.attachedWireds = null;
    }
    cleanRoomData();
  }
  
  public void setRoomData(RoomItemData dat)
  {
    this.data = ((RoomFloorItemData)dat);
  }
  
  public void cleanRoomData()
  {
    this.data = null;
  }
  
  public int getRoomId()
  {
    if (this.data == null) {
      return 0;
    }
    return this.data.getRoomId();
  }
  
  public void insertItem()
    throws Exception
  {
    int roomId = getRoomId();
    
    byte[] data = this.extraData.data();
    if (data == null) {
      Database.exec("INSERT IGNORE INTO furnis(id,userid,baseid,roomid,data)VALUES(" + this.itemId + "," + this.owner.userId + "," + this.baseItem.Id + "," + roomId + ",NULL);", new Object[0]);
    } else {
      Database.exec("INSERT IGNORE INTO furnis(id,userid,baseid,roomid,data)VALUES(" + this.itemId + "," + this.owner.userId + "," + this.baseItem.Id + "," + roomId + ",?);", new Object[] { data });
    }
    if (roomId > 0) {
      Database.exec("INSERT IGNORE INTO furnis_roomdata(id,a,b,r)VALUES(" + this.itemId + "," + BflyData.Combine(getX(), getY()) + "," + getZ() + "," + getDir().getRot() + ");", new Object[0]);
    }
    if (this.extraParam > 0) {
      Database.exec("INSERT IGNORE INTO furnis_floorextra(id,param)VALUES(" + this.itemId + "," + this.extraParam + ");", new Object[0]);
    }
  }
  
  public void roomDataSave(boolean moved)
    throws Exception
  {
    if (moved)
    {
      float a = BflyData.Combine(getX(), getY());
      float b = getZ();
      int r = getDir().getRot();
      Database.exec("INSERT INTO furnis_roomdata(id,a,b,r)VALUES(" + this.itemId + "," + a + "," + b + "," + r + ") on DUPLICATE KEY UPDATE `a`='" + a + "',`b`='" + b + "',`r`='" + r + "';", new Object[0]);
      if ((this instanceof SongItem)) {
        Database.exec("INSERT IGNORE INTO room_discs (roomid,songid,itemid)VALUES(" + getRoomId() + "," + getExtraParam() + "," + this.itemId + ");", new Object[0]);
      }
    }
    else
    {
      this.data.save();
    }
  }
  
  public byte[] SquareInFront()
  {
    return this.data.SquareInFront();
  }
  
  public byte[] SquareBehind()
  {
    return this.data.SquareBehind();
  }
  
  public void finishPlace(List<RoomFloorItemData.AffectedTile> Points)
  {
    this.data.finishPlace(Points);
  }
  
  public void finishPlace(Connection user, List<RoomFloorItemData.AffectedTile> Points, boolean add)
  {
    this.data.finishPlace(user, Points, add);
  }
  
  public void setPosition()
  {
    this.data.setPosition();
  }
  
  public void setPosition(int argX, int argY, int argXY)
  {
    this.data.setPosition(argX, argY, argXY);
  }
  
  public void setPosition(int arg1, int arg2)
  {
    this.data.setPosition(arg1, arg2);
  }
  
  public void setPosition(int val)
  {
    this.data.setPosition(val);
  }
  
  public void setPosition(float val)
  {
    this.data.setPosition(val);
  }
  
  public void setDir(Direction8 val)
  {
    this.data.setDir(val);
  }
  
  public Direction8 getDir()
  {
    return this.data.getDir();
  }
  
  public List<RoomFloorItemData.AffectedTile> getAffectedTiles()
  {
    return this.data.getAffectedTiles();
  }
  
  public List<RoomFloorItemData.AffectedTile> getAffectedTiles(boolean asd)
  {
    return this.data.getAffectedTiles(asd);
  }
  
  public List<RoomFloorItemData.AffectedTile> getAffectedTiles(int xy, Direction8 dir)
  {
    return this.data.getAffectedTiles(xy, dir);
  }
  
  public List<RoomFloorItemData.AffectedTile> getOutSideTiles()
  {
    return this.data.getOutSideTiles();
  }
  
  public RoomTask getRoom()
  {
    if (this.data == null) {
      return null;
    }
    return this.data.currentRoom;
  }
  
  public final void eventSetFlag(int xy, int flag, boolean Add)
  {
    this.data.currentRoom.squareFlag.eventSetFlag(xy, flag, Add);
  }
  
  public void setExtraParam(int extraparam)
  {
    this.extraParam = extraparam;
  }
  
  public int getExtraParam()
  {
    return this.extraParam;
  }
  
  public int getX()
  {
    return this.data.getX();
  }
  
  public int getY()
  {
    return this.data.getY();
  }
  
  public float getZ()
  {
    return this.data.getZ();
  }
  
  public int getXy()
  {
    return this.data.getXy();
  }
  
  public boolean itemsOnTop()
  {
    return this.data.itemsOnTop();
  }
}


