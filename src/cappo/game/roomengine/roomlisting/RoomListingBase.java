package cappo.game.roomengine.roomlisting;

import cappo.engine.threadpools.RoomTask;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class RoomListingBase
  implements Comparator<RoomTask>
{
  public static final int POPULAR_MAIN = 0;
  public static final int SCORE_MAIN = 1;
  public static final int CUSTOM = 2;
  public List<RoomTask> rankedList = new ArrayList(50);
  public final int listingID;
  
  public RoomListingBase(int id)
  {
    this.listingID = id;
  }
}


