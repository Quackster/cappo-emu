package cappo.protocol.messages;

import cappo.engine.player.Connection;

public abstract class IncomingMessageEvent
{
  public int HEADER;
  public static final IncomingMessageEvent[] callBacks = new IncomingMessageEvent[5000];
  
  public abstract void messageReceived(Connection paramConnection)
    throws Exception;
}


