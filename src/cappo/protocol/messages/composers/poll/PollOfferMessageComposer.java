package cappo.protocol.messages.composers.poll;

import cappo.engine.network.MessageWriter;
import cappo.game.polls.Poll;
import cappo.protocol.messages.Composer;

public class PollOfferMessageComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(Poll poll)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(poll.id), ClientMessage);
    Composer.add(poll.title, ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


