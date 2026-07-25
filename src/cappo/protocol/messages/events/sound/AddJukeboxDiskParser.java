package cappo.protocol.messages.events.sound;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.player.inventory.PlayerInventory;
import cappo.game.roomengine.entity.item.floor.SongItem;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.sound.trax.TraxPlaylist;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.sound.JukeboxSongDisksComposer;
import java.util.List;

public class AddJukeboxDiskParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if (avatar == null) {
      return;
    }
    int itemid = Main.currentPacket.readInt();
    
    SongItem item = Main.inventory.getSong(itemid);
    if (item == null) {
      return;
    }
    Main.inventoryRemoveItem(itemid, false);
    item.setMysqlState(2);
    avatar.room.traxPlaylist.PlaylistByIndex.add(item);
    QueueWriter.write(Main.socket, JukeboxSongDisksComposer.compose(avatar.room.traxPlaylist.PlaylistByIndex));
  }
}


