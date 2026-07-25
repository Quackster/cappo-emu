package cappo.protocol.messages.composers.poll;

import cappo.engine.network.MessageWriter;
import cappo.game.polls.Poll;
import cappo.game.polls.PollQuestion;
import cappo.protocol.messages.Composer;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class PollContentsMessageComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(Poll poll)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.writeInt32(poll.id, ClientMessage);
    Composer.add(poll.title, ClientMessage);
    Composer.add(poll.thanks, ClientMessage);
    Composer.writeInt32(poll.questions.size(), ClientMessage);
    for (PollQuestion question : poll.questions.values())
    {
      Composer.writeInt32(question.id, ClientMessage);
      Composer.writeInt32(question.orderid, ClientMessage);
      Composer.writeInt32(question.type, ClientMessage);
      Composer.add(question.text, ClientMessage);
      if ((question.type != 1) && (question.type != 2)) {
        break;
      }
      Composer.writeInt32(0, ClientMessage);
      Composer.writeInt32(question.answers.length, ClientMessage);
      int count = 0;
      for (String answer : question.answers) {
        Composer.add(Integer.toString(count++), ClientMessage);
        Composer.add(answer, ClientMessage);
      }
/* 44b:  */     }
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


