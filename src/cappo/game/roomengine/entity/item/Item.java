package cappo.game.roomengine.entity.item;

import cappo.engine.database.Database;
import cappo.engine.logging.Log;
import cappo.game.collections.BaseItem;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.entity.item.extradata.ExtraDataBase;

public abstract class Item
{
  public static final int UPDATE = 1;
  public static final int MOVE = 2;
  public static final int INSERT = 3;
  public static final int DELETE = 4;
  public int refId;
  private int mysqlAction;
  public int itemId;
  public BaseItem baseItem;
  public PlayerData owner;
  public ExtraDataBase extraData;
  
  public abstract int getRoomId();
  
  public abstract void insertItem()
    throws Exception;
  
  public abstract void roomDataSave(boolean paramBoolean)
    throws Exception;
  
  public abstract void setRoomData(RoomItemData paramRoomItemData);
  
  public abstract void cleanRoomData();
  
  public void setMysqlState(int newState)
  {
    if (this.mysqlAction == 0)
    {
      this.mysqlAction = newState;
      return;
    }
    if (newState == 4)
    {
      this.mysqlAction = 4;
      try
      {
        Database.exec(
        


          "DELETE da,db,dc FROM furnis AS da LEFT JOIN furnis_roomdata AS db ON db.id=da.id LEFT JOIN furnis_floorextra AS dc ON dc.id=da.id WHERE da.id = " + this.itemId + ";", new Object[0]);
      }
      catch (Exception ex)
      {
        Log.printException("Item-Delete", ex);
      }
      return;
    }
    if (this.mysqlAction == 4) {
      return;
    }
    if (this.mysqlAction == 3) {
      return;
    }
    if (newState == 2)
    {
      if (this.mysqlAction == 2)
      {
        this.mysqlAction = 1;
        return;
      }
      this.mysqlAction = 2;
    }
  }
  
  public void mysqlSave()
    throws Exception
  {
    if (this.mysqlAction == 0) {
      return;
    }
    if (this.mysqlAction == 3)
    {
      try
      {
        insertItem();
      }
      catch (Exception ex)
      {
        Log.printException("mysqlSave", ex);
      }
      this.mysqlAction = 0;
      return;
    }
    if (this.mysqlAction != 4)
    {
      int roomId = getRoomId();
      if (this.extraData != null)
      {
        byte[] data = this.extraData.data();
        if (data == null) {
          Database.exec("UPDATE furnis SET roomid=" + roomId + ",userid=" + this.owner.userId + ",data=NULL WHERE id=" + this.itemId + ";", new Object[0]);
        } else {
          Database.exec("UPDATE furnis SET roomid=" + roomId + ",userid=" + this.owner.userId + ",data=? WHERE id=" + this.itemId + ";", new Object[] { data });
        }
      }
      else
      {
        Database.exec("UPDATE furnis SET roomid=" + roomId + ",userid=" + this.owner.userId + ",data=NULL WHERE id=" + this.itemId + ";", new Object[0]);
      }
      if (roomId < 1)
      {
        if (this.mysqlAction == 2) {
          Database.exec("DELETE FROM furnis_roomdata WHERE id=" + this.itemId + ";", new Object[0]);
        }
      }
      else {
        roomDataSave(this.mysqlAction == 2);
      }
    }
    this.mysqlAction = 0;
  }
  
  public int hashCode()
  {
    return this.itemId;
  }
}


