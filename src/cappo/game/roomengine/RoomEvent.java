package cappo.game.roomengine;

import java.util.List;

public class RoomEvent
{
  public int category;
  public String description;
  public String name;
  public int roomId;
  public int startTime;
  public List<String> tags;
  
  public RoomEvent(int RoomId, String eventName, String eventDescription, int eventCategory, List<String> eventTags, int time)
  {
    this.roomId = RoomId;
    this.name = eventName;
    this.description = eventDescription;
    this.category = eventCategory;
    this.tags = eventTags;
    this.startTime = time;
  }
}


