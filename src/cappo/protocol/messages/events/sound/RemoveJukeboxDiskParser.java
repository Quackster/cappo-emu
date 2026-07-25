package cappo.protocol.messages.events.sound;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.player.PlayerData;
import cappo.game.player.inventory.PlayerInventory;
import cappo.game.roomengine.entity.item.floor.SongItem;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.sound.trax.TraxPlaylist;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.sound.JukeboxSongDisksComposer;
import cappo.protocol.messages.composers.sound.UserSongDisksInventoryComposer;
import java.util.List;

public class RemoveJukeboxDiskParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    RoomTask room = avatar.room;
    
    int index = Main.currentPacket.readInt();
    
    SongItem item = (SongItem)room.traxPlaylist.PlaylistByIndex.get(index);
    if (item.owner.userId != Main.playerData.userId) {
      return;
    }
    Main.inventoryAddFloorItem(item);
    item.setMysqlState(2);
    
    room.traxPlaylist.PlaylistByIndex.remove(index);
    
    QueueWriter.write(Main.socket, UserSongDisksInventoryComposer.compose(Main.inventory.getSongs()));
    QueueWriter.write(Main.socket, JukeboxSongDisksComposer.compose(room.traxPlaylist.PlaylistByIndex));
  }
}


