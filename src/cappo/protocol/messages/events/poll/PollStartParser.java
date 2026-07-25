package cappo.protocol.messages.events.poll;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.polls.Poll;
import cappo.game.polls.PollManager;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.poll.PollContentsMessageComposer;
import cappo.protocol.messages.composers.poll.PollErrorMessageComposer;
import java.util.Map;

public class PollStartParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Poll poll = (Poll)PollManager.polls.get(Integer.valueOf(Main.currentPacket.readInt()));
    if (poll == null) {
      QueueWriter.write(Main.socket, PollErrorMessageComposer.compose());
    } else {
      QueueWriter.write(Main.socket, PollContentsMessageComposer.compose(poll));
    }
  }
}


