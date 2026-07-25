package cappo.game.roomengine.entity.item.floor;

import cappo.engine.logging.Log;
import cappo.game.sound.trax.Trax;
import cappo.game.sound.trax.TraxDisc;
import java.util.Map;

public class SongItem
  extends GenericFloorItem
{
  public TraxDisc Disc;
  
  public void setExtraParam(int extraparam)
  {
    if (extraparam == 0) {
      extraparam = 1;
    }
    super.setExtraParam(extraparam);
    
    this.Disc = ((TraxDisc)Trax.songDiscs.get(Integer.valueOf(extraparam)));
    if (this.Disc == null) {
      Log.printLog("UnRegistered SongId: " + extraparam);
    }
  }
}


