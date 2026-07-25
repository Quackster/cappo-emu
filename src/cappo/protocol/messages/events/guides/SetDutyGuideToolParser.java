package cappo.protocol.messages.events.guides;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.guides.UpdateGuideToolComposer;

public class SetDutyGuideToolParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
    throws Exception
  {
    boolean onDuty = Main.currentPacket.readBoolean();
    Main.currentPacket.readBoolean();
    Main.currentPacket.readBoolean();
    QueueWriter.write(Main.socket, UpdateGuideToolComposer.compose(onDuty, 99, 99));
  }
}


