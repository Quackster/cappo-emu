package cappo.game.navigator.officialrooms;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;
import java.sql.ResultSet;

public class OfficialRoomTagSearch
  extends Official
{
  public String search;
  
  public OfficialRoomTagSearch(ResultSet data)
    throws Exception
  {
    super(data);
    
    this.search = data.getString("extra");
  }
  
  public void compose(MessageWriter clientMessage)
  {
    Composer.add(Integer.valueOf(0), clientMessage);
    Composer.add(Integer.valueOf(this.type), clientMessage);
    Composer.add(this.search, clientMessage);
  }
}


