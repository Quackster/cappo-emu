package cappo.game.roomengine.entity.item.floor;

import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.entity.item.extradata.CrackableExtraData;

public class CrackeableItem
  extends GenericFloorItem
{
  private void updateHits(int hits)
  {
    CrackableExtraData data = (CrackableExtraData)this.extraData;
    data.hits = hits;
    
    RoomTask room = getRoom();
    if (room == null) {
      return;
    }
    room.floorItemUpdateNeeded(this);
  }
  
  public void setIntData(int data)
  {
    super.setIntData(data);
    updateHits(data);
  }
  
  public int incIntData(int ammount)
  {
    int data = super.incIntData(ammount);
    updateHits(data);
    return data;
  }
  
  public int incIntDataMod(int ammount, int modulus)
  {
    int data = super.incIntDataMod(ammount, modulus);
    updateHits(data);
    return data;
  }
  
  public int decIntData(int ammount)
  {
    int data = super.decIntData(ammount);
    updateHits(data);
    return data;
  }
}


