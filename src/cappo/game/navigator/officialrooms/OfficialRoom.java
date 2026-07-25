package cappo.game.navigator.officialrooms;

import cappo.engine.network.MessageWriter;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.RoomManager;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializeRoom;
import java.sql.ResultSet;

public class OfficialRoom
  extends Official
{
  public int roomId;
  
  public OfficialRoom(ResultSet data)
    throws Exception
  {
    super(data);
    
    this.roomId = Integer.parseInt(data.getString("extra"));
  }
  
  public void compose(MessageWriter clientMessage)
    throws Exception
  {
    RoomData room = RoomManager.getRoom(this.roomId);
    if (room == null)
    {
      room = RoomManager.loadRoom(this.roomId);
      if (room == null) {
        throw new Exception("Not found room:" + this.roomId);
      }
    }
    Composer.add(Integer.valueOf(room.room != null ? room.room.userCount : 0), clientMessage);
    Composer.add(Integer.valueOf(this.type), clientMessage);
    SerializeRoom.parse(clientMessage, room);
  }
}


