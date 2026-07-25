package cappo.protocol.messages.events.poll;

import cappo.engine.network.MessageWriter;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.IncomingMessageEvent;

public class VoteAnswerParser
  extends IncomingMessageEvent
{
  private final int[] Results = new int[6];
  private int Total;
  
  public void messageReceived(Connection Main)
  {
    this.Results[(Main.currentPacket.readInt() - 1)] += 1;
    this.Total += 1;
    
    MessageWriter ClientMessage = new MessageWriter();
    
    Composer.initPacket(80, ClientMessage);
    Composer.add("Que puntaje le das al Emu", ClientMessage);
    Composer.add(Integer.valueOf(6), ClientMessage);
    
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add("5", ClientMessage);
    Composer.add(Integer.valueOf(this.Results[0]), ClientMessage);
    
    Composer.add(Integer.valueOf(1), ClientMessage);
    Composer.add("6", ClientMessage);
    Composer.add(Integer.valueOf(this.Results[1]), ClientMessage);
    
    Composer.add(Integer.valueOf(2), ClientMessage);
    Composer.add("7", ClientMessage);
    Composer.add(Integer.valueOf(this.Results[2]), ClientMessage);
    
    Composer.add(Integer.valueOf(3), ClientMessage);
    Composer.add("8", ClientMessage);
    Composer.add(Integer.valueOf(this.Results[3]), ClientMessage);
    
    Composer.add(Integer.valueOf(4), ClientMessage);
    Composer.add("9", ClientMessage);
    Composer.add(Integer.valueOf(this.Results[4]), ClientMessage);
    
    Composer.add(Integer.valueOf(5), ClientMessage);
    Composer.add("10", ClientMessage);
    Composer.add(Integer.valueOf(this.Results[5]), ClientMessage);
    
    Composer.add(Integer.valueOf(this.Total), ClientMessage);
    
    QueueWriter.write(Main.socket, ClientMessage);
  }
}


