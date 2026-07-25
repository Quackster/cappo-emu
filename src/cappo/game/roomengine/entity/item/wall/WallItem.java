package cappo.game.roomengine.entity.item.wall;

import cappo.engine.database.Database;
import cappo.engine.logging.Log;
import cappo.game.collections.BaseItem;
import cappo.game.player.PlayerData;
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

public abstract class WallItem
  extends Item
{
  private RoomWallItemData data;
  
  public static WallItem createItem(PlayerData owner, int ref, int Id, StuffDataReader reader, BaseItem baseItem)
  {
    WallItem userItem = new GenericWallItem();
    
    userItem.refId = ref;
    userItem.itemId = Id;
    userItem.baseItem = baseItem;
    userItem.owner = owner;
    if (reader.type == 0)
    {
      StringStuffData stuffdata = new StringStuffData(reader);
      userItem.extraData = stuffdata;
      try
      {
        GenericWallItem wall = (GenericWallItem)userItem;
        wall.setIntData(Integer.parseInt(stuffdata.extraData));
      }
      catch (Exception localException) {}
    }
    else if (reader.type == 1)
    {
      userItem.extraData = new MapStuffData(reader);
    }
    else if (reader.type == 2)
    {
      userItem.extraData = new StringArrayStuffData(reader);
    }
    else if (reader.type == 3)
    {
      userItem.extraData = new ExtraData1(reader);
    }
    else if (reader.type == 5)
    {
      userItem.extraData = new IntArrayStuffData(reader);
    }
    else if (reader.type == 7)
    {
      userItem.extraData = new CrackableExtraData(reader);
    }
    else
    {
      Log.printLog("BAD EXTRATYPE = " + reader.type + " - BASEID = " + baseItem.Id);
    }
    return userItem;
  }
  
  public String roomDataString()
  {
    return this.data.toString();
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
    
    byte[] dat = this.extraData.data();
    if (dat == null) {
      Database.exec("INSERT IGNORE INTO furnis(id,userid,baseid,roomid,data)VALUES(" + this.itemId + "," + this.owner.userId + "," + this.baseItem.Id + "," + roomId + ",NULL);", new Object[0]);
    } else {
      Database.exec("INSERT IGNORE INTO furnis(id,userid,baseid,roomid,data)VALUES(" + this.itemId + "," + this.owner.userId + "," + this.baseItem.Id + "," + roomId + ",?);", new Object[] { dat });
    }
    if (roomId > 0) {
      Database.exec("INSERT IGNORE INTO furnis_roomdata(id,a,b,r)VALUES(" + this.itemId + "," + this.data.GetXValue() + "," + this.data.GetYValue() + "," + this.data.n() + ");", new Object[0]);
    }
  }
  
  public void roomDataSave(boolean moved)
    throws Exception
  {
    if (moved)
    {
      float a = this.data.GetXValue();
      float b = this.data.GetYValue();
      int r = this.data.n();
      Database.exec("INSERT INTO furnis_roomdata(id,a,b,r)VALUES(" + this.itemId + "," + a + "," + b + "," + r + ") on DUPLICATE KEY UPDATE `a`='" + a + "',`b`='" + b + "',`r`='" + r + "';", new Object[0]);
    }
    else
    {
      this.data.save();
    }
  }
  
  public void setRoomData(RoomItemData dat)
  {
    this.data = ((RoomWallItemData)dat);
  }
  
  public void cleanRoomData()
  {
    this.data = null;
  }
}


