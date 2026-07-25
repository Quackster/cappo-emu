package cappo.game.navigator.officialrooms;

import cappo.engine.network.MessageWriter;
import java.sql.ResultSet;

public abstract class Official
{
  public int id;
  public String caption;
  public String desc;
  public boolean showDetails;
  public String image;
  public int parentId;
  public int type;
  
  public Official(ResultSet data)
    throws Exception
  {
    this.id = data.getInt("id");
    this.caption = data.getString("caption");
    this.desc = data.getString("desc");
    this.showDetails = (data.getInt("show_details") == 1);
    this.image = data.getString("image");
    this.parentId = data.getInt("parent_id");
    this.type = data.getInt("type");
  }
  
  public abstract void compose(MessageWriter paramMessageWriter)
    throws Exception;
}


