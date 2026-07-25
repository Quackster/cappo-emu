package cappo.protocol.messages.events.poll;

import cappo.engine.database.Database;
import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.player.PlayerData;
import cappo.game.polls.Poll;
import cappo.game.polls.PollManager;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.poll.PollErrorMessageComposer;
import java.util.Map;

public class PollRejectParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Poll poll = (Poll)PollManager.polls.get(Integer.valueOf(Main.currentPacket.readInt()));
    if (poll == null) {
      QueueWriter.write(Main.socket, PollErrorMessageComposer.compose());
    } else {
      Database.exec("INSERT INTO poll_answers (userid,poll,question,answer)VALUES(" + Main.playerData.userId + "," + poll.id + ", 0, NULL);", new Object[0]);
    }
  }
}


