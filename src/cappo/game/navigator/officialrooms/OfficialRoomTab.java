package cappo.game.navigator.officialrooms;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;
import java.sql.ResultSet;

public class OfficialRoomTab
  extends Official
{
  public boolean isOpen;
  
  public OfficialRoomTab(ResultSet data)
    throws Exception
  {
    super(data);
    
    this.isOpen = data.getString("extra").equals("1");
  }
  
  public void compose(MessageWriter clientMessage)
  {
    Composer.add(Integer.valueOf(0), clientMessage);
    Composer.add(Integer.valueOf(this.type), clientMessage);
    Composer.add(Boolean.valueOf(this.isOpen), clientMessage);
  }
}


