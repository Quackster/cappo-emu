package cappo.protocol.messages.events.landing;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.landing.UpdateLandingComposer;

public class RefreshLandingViewParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    String code = "";
    String data = Main.currentPacket.readString();
    if (!data.isEmpty())
    {
      String[] tmp = data.split(";");
      String[] first = tmp[0].split(",");
      code = first[1];
    }
    QueueWriter.write(Main.socket, UpdateLandingComposer.compose(data, code));
  }
}


