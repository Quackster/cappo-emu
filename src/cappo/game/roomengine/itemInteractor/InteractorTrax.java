package cappo.game.roomengine.itemInteractor;

import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.item.floor.GenericFloorItem;
import cappo.game.roomengine.entity.item.floor.SongItem;
import cappo.game.roomengine.entity.item.wall.GenericWallItem;
import cappo.game.roomengine.roomevents.Trax_NEXTSONG;
import cappo.game.sound.trax.TraxDisc;
import cappo.game.sound.trax.TraxPlaylist;
import cappo.protocol.messages.composers.sound.NowPlayingComposer;

public class InteractorTrax
  extends Interactor
{
  public void OnPlace(RoomTask room, Connection User, GenericFloorItem Item) {}
  
  public void OnTriggerFloor(RoomTask room, Connection User, FloorItem fitem, int Request, boolean UserHasRights)
  {
    if (!UserHasRights) {
      return;
    }
    if (Request == -1) {
      return;
    }
    GenericFloorItem Item = (GenericFloorItem)fitem;
    if (!room.traxPlaylist.Playing)
    {
      room.traxPlaylist.StartPlaying();
      if (room.traxPlaylist.CurrentSong != null)
      {
        room.traxPlaylist.Playing = true;
        
        Item.setIntData(1);
        room.floorItemUpdateNeeded(Item);
        
        room.sendMessage(NowPlayingComposer.compose(room.traxPlaylist, 0));
        
        room.addItemEvent(new Trax_NEXTSONG(Item), room.traxPlaylist.CurrentSong.Disc.Length / 500);
      }
    }
    else
    {
      Item.setIntData(0);
      room.floorItemUpdateNeeded(Item);
      room.traxPlaylist.Playing = false;
      room.sendMessage(NowPlayingComposer.compose());
    }
  }
  
  public void OnTriggerWall(RoomTask room, Connection User, GenericWallItem Item, int Request, boolean UserHasRights) {}
  
  public void OnPickUp(RoomTask room, Connection User, GenericFloorItem Item) {}
}


