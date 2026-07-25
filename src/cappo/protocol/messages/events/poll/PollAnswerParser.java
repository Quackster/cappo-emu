package cappo.protocol.messages.events.poll;

import cappo.engine.database.Database;
import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.player.PlayerData;
import cappo.game.polls.Poll;
import cappo.game.polls.PollManager;
import cappo.game.polls.PollQuestion;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.poll.PollErrorMessageComposer;
import java.util.Map;

public class PollAnswerParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Poll poll = (Poll)PollManager.polls.get(Integer.valueOf(Main.currentPacket.readInt()));
    if (poll == null)
    {
      QueueWriter.write(Main.socket, PollErrorMessageComposer.compose());
      return;
    }
    PollQuestion question = (PollQuestion)poll.questions.get(Integer.valueOf(Main.currentPacket.readInt()));
    if (question == null)
    {
      QueueWriter.write(Main.socket, PollErrorMessageComposer.compose());
      return;
    }
    int count = Main.currentPacket.readInt();
    for (int i = 0; i < count; i++)
    {
      String answer = Main.currentPacket.readString();
      Database.exec("INSERT INTO poll_answers (userid,poll,question,answer)VALUES(" + Main.playerData.userId + "," + poll.id + "," + question.id + ",?);", new Object[] { answer });
    }
  }
}


