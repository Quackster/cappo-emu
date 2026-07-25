package cappo.protocol.messages.events.advertisement;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.advertisement.InterstitialComposer;

public class GetInterstitialParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    QueueWriter.write(Main.socket, InterstitialComposer.compose("http://www.opera.com/bitmaps/company/education/wsc_728x90.jpg", "http://google.com"));
  }
}


