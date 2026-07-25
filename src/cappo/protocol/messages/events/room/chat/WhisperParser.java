package cappo.protocol.messages.events.room.chat;

import cappo.engine.network.MessageReader;
import cappo.engine.network.MessageWriter;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Clients;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.chat.wf.WordFilter;
import cappo.game.roomengine.chat.wf.WordFilterAction;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.room.chat.WhisperComposer;

public class WhisperParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection cn)
  {
    Avatar avatar = cn.avatar;
    if (avatar == null) {
      return;
    }
    String Params = cn.currentPacket.readString();
    
    int find = Params.indexOf(" ");
    
    PlayerData playerData = Clients.getPlayerData(Params.substring(0, find));
    if ((playerData == null) || (playerData.connection == null)) {
      return;
    }
    Avatar clientAvatar = playerData.connection.avatar;
    if (clientAvatar == null) {
      return;
    }
    if (avatar.room != clientAvatar.room) {
      return;
    }
    if (!avatar.canChat()) {
      return;
    }
    String text = Params.substring(find + 1);
    if (text.length() > 100) {
      return;
    }
    WordFilterAction action = WordFilter.getAction(text);
    if ((action != null) && (action.run(cn))) {
      return;
    }
    int styleId = cn.currentPacket.readInt();
    
    MessageWriter message = WhisperComposer.compose(avatar.virtualId, text, 0, styleId, null, -1);
    QueueWriter.write(cn.socket, message);
    QueueWriter.writeAndFlush(playerData.connection.socket, message);
    MessageWriter toStaff = WhisperComposer.compose(avatar.virtualId, "TO (" + playerData.userName + "): " + text, 0, styleId, null, -1);
    avatar.room.sendMessage(toStaff, new int[] { 5 });
  }
}


