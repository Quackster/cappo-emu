package cappo.protocol.messages.events.friendlist;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Clients;
import cappo.engine.player.Connection;
import cappo.game.player.PlayerData;
import cappo.game.player.messenger.PlayerMessenger;
import cappo.game.roomengine.chat.wf.WordFilter;
import cappo.game.roomengine.chat.wf.WordFilterAction;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.friendlist.BuddyMessageComposer;
import cappo.protocol.messages.composers.friendlist.InstantMessageErrorComposer;

public class SendMsgParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection cn)
  {
    int userId = cn.currentPacket.readInt();
    
    PlayerData client = Clients.getPlayerDataLoaded(userId);
    if ((client == null) || (client.connection == null))
    {
      QueueWriter.write(cn.socket, InstantMessageErrorComposer.compose(5, userId, ""));
      return;
    }
    if (!cn.playerData.messenger.haveFriend(client.userId))
    {
      QueueWriter.write(cn.socket, InstantMessageErrorComposer.compose(6, userId, ""));
      return;
    }
    String text = cn.currentPacket.readString();
    if (text.isEmpty()) {
      return;
    }
    WordFilterAction action = WordFilter.getAction(text);
    if ((action != null) && (action.run(cn))) {
      return;
    }
    QueueWriter.writeAndFlush(client.connection.socket, BuddyMessageComposer.compose(cn.playerData.userId, text));
  }
}


