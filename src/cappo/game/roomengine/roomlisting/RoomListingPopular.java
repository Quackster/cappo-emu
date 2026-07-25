package cappo.game.roomengine.roomlisting;

import cappo.engine.threadpools.RoomTask;

public class RoomListingPopular
  extends RoomListingBase
{
  public static final RoomListingPopular mainInstance = new RoomListingPopular(0);
  
  public RoomListingPopular(int id)
  {
    super(id);
  }
  
  public int compare(RoomTask o1, RoomTask o2)
  {
    if (o1 == o2) {
      return 0;
    }
    return o1.userCount == o2.userCount ? 0 : o1.userCount > o2.userCount ? -1 : 1;
  }
}


