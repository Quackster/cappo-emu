package cappo.protocol.messages.events.friendlist;

import cappo.engine.network.MessageReader;
import cappo.engine.network.MessageWriter;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Clients;
import cappo.engine.player.Connection;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.chat.wf.WordFilter;
import cappo.game.roomengine.chat.wf.WordFilterAction;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.friendlist.RoomInviteComposer;
import cappo.protocol.messages.composers.friendlist.RoomInviteErrorComposer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SendRoomInviteParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection cn)
  {
    List<Integer> SendTo = new ArrayList();
    List<Integer> Failed = new ArrayList();
    
    int count = cn.currentPacket.readInt();
    if (count > 100) {
      return;
    }
    for (int i = 0; i < count; i++) {
      SendTo.add(Integer.valueOf(cn.currentPacket.readInt()));
    }
    String message = cn.currentPacket.readString();
    
    WordFilterAction action = WordFilter.getAction(message);
    if ((action != null) && (action.run(cn))) {
      return;
    }
    MessageWriter Message = RoomInviteComposer.compose(cn.playerData.userId, message);
    for (Iterator localIterator = SendTo.iterator(); localIterator.hasNext();)
    {
      int UserId = ((Integer)localIterator.next()).intValue();
      PlayerData client = Clients.getPlayerData(UserId);
      if ((client == null) || (client.connection == null)) {
        Failed.add(Integer.valueOf(UserId));
      } else {
        QueueWriter.writeAndFlush(client.connection.socket, Message);
      }
    }
    if (!Failed.isEmpty()) {
      QueueWriter.write(cn.socket, RoomInviteErrorComposer.compose(1, Failed));
    }
  }
}


