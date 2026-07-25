package cappo.protocol.messages.events.landing;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.landing.RewardResultComposer;

public class RequestBadgeParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    String req = Main.currentPacket.readString();
    if (req.equals("REQ001"))
    {
      Main.giveBadge("ancients_start");
      QueueWriter.write(Main.socket, RewardResultComposer.compose(7));
    }
  }
}


