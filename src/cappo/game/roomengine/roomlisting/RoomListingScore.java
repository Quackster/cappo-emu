package cappo.game.roomengine.roomlisting;

import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.RoomData;

public class RoomListingScore
  extends RoomListingBase
{
  public static final RoomListingScore mainInstance = new RoomListingScore(1);
  
  public RoomListingScore(int id)
  {
    super(id);
  }
  
  public int compare(RoomTask o1, RoomTask o2)
  {
    if (o1 == o2) {
      return 0;
    }
    return o1.roomData.rating == o2.roomData.rating ? 0 : o1.roomData.rating > o2.roomData.rating ? -1 : 1;
  }
}


