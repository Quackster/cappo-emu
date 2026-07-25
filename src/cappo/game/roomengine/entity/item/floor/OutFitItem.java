package cappo.game.roomengine.entity.item.floor;

import cappo.game.player.AvatarLook;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.entity.item.extradata.MapStuffData;
import java.util.Map;

public class OutFitItem
  extends FloorItem
{
  public AvatarLook look;
  
  public void setName(String name)
  {
    MapStuffData data = (MapStuffData)this.extraData;
    data.setExtraData("OUTFIT_NAME=" + name);
  }
  
  public void setLook(PlayerData playerData)
  {
    String outLook = playerData.avatarLook.filterLook(new int[] { 10, 7 });
    this.look = new AvatarLook(outLook);
    MapStuffData data = (MapStuffData)this.extraData;
    data.setExtraData("FIGURE=" + outLook + "\t" + "GENDER=" + (playerData.sex == 1 ? "M" : "F"));
  }
  
  public void generateLook(PlayerData playerData)
  {
    MapStuffData data = (MapStuffData)this.extraData;
    playerData.sex = (((String)data.extraData.get("GENDER")).equals("M") ? 1 : 0);
    playerData.avatarLook.setPart(10, this.look);
    playerData.avatarLook.setPart(7, this.look);
  }
  
  public void getAvatarLook()
  {
    MapStuffData data = (MapStuffData)this.extraData;
    this.look = new AvatarLook((String)data.extraData.get("FIGURE"));
  }
}


