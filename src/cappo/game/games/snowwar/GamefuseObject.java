package cappo.game.games.snowwar;

import cappo.game.roomengine.entity.item.Item;
import cappo.game.roomengine.entity.item.RoomItemData;
import cappo.game.roomengine.entity.item.extradata.StringStuffData;

public class GamefuseObject
  extends Item
{
  public int X;
  public int Y;
  public int Rot;
  public int Z;
  
  public GamefuseObject()
  {
    this.extraData = new StringStuffData(null);
  }
  
  public void setRoomData(RoomItemData data) {}
  
  public void cleanRoomData() {}
  
  public void roomDataSave(boolean moved) {}
  
  public int getRoomId()
  {
    return 0;
  }
  
  public void insertItem()
    throws Exception
  {}
}


