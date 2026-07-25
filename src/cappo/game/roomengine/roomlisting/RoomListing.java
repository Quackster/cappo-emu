package cappo.game.roomengine.roomlisting;

import cappo.engine.network.MessageWriter;
import cappo.engine.threadpools.RoomTask;
import cappo.game.navigator.NavigatorCategories;
import cappo.game.roomengine.RoomData;
import cappo.protocol.messages.composers.navigator.GuestRoomSearchResultComposer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RoomListing
{
  public static enum ListingRoomState
  {
    RANKED,  UNRANKED;
  }
  
  public static MessageWriter PopularRooms = GuestRoomSearchResultComposer.compose(-1, "1", new ArrayList(1));
  public static MessageWriter ScoreRooms = GuestRoomSearchResultComposer.compose(-1, "2", new ArrayList(1));
  public static Map<Integer, MessageWriter> ByCatRooms;
  public static Map<Integer, RoomListingPopular> listingByCategory;
  
  public static void Init()
  {
    ByCatRooms = new ConcurrentHashMap();
    listingByCategory = new ConcurrentHashMap();
    for (NavigatorCategories cat : NavigatorCategories.roomCategories.values())
    {
      listingByCategory.put(Integer.valueOf(cat.id), new RoomListingPopular(2 + cat.id));
      ByCatRooms.put(Integer.valueOf(cat.id), GuestRoomSearchResultComposer.compose(-1, "1", new ArrayList(1)));
    }
  }
  
  private static MessageWriter updateRoom(RoomTask room, RoomListingBase listing, String type)
  {
    synchronized (listing)
    {
      if (room.roomListingState[listing.listingID] != ListingRoomState.RANKED)
      {
        int size = listing.rankedList.size();
        if (size > 49)
        {
          RoomTask lastRoom = (RoomTask)listing.rankedList.get(49);
          if (listing.compare(lastRoom, room) != 1) {
            return null;
          }
          listing.rankedList.remove(49);
          lastRoom.roomListingState[listing.listingID] = ListingRoomState.UNRANKED;
        }
        listing.rankedList.add(room);
        room.roomListingState[listing.listingID] = ListingRoomState.RANKED;
      }
      Collections.sort(listing.rankedList, listing);
      for (int i = listing.rankedList.size() - 1; i >= 0; i--)
      {
        RoomTask last = (RoomTask)listing.rankedList.get(i);
        if ((last.userCount < 1) || (last.roomData.room != last)) {
          listing.rankedList.remove(i);
        }
      }
      return GuestRoomSearchResultComposer.compose2(-1, type, listing.rankedList);
    }
  }
  
  public static void updatePopularRooms(RoomTask room)
  {
    MessageWriter tmp = updateRoom(room, RoomListingPopular.mainInstance, "1");
    if (tmp != null) {
      PopularRooms = tmp;
    }
    RoomListingPopular listing = (RoomListingPopular)listingByCategory.get(Integer.valueOf(room.roomData.category));
    if (listing != null)
    {
      tmp = updateRoom(room, listing, "1");
      if (tmp != null) {
        ByCatRooms.put(Integer.valueOf(room.roomData.category), tmp);
      }
    }
  }
  
  public static void updateMostScoreRooms(RoomTask room)
  {
    MessageWriter tmp = updateRoom(room, RoomListingScore.mainInstance, "2");
    if (tmp != null) {
      ScoreRooms = tmp;
    }
  }
}


