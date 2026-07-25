package cappo.protocol.messages.events.avatar;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.avatar.WardrobeComposer;
import java.util.Map;

public class GetWardrobeParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    QueueWriter.write(Main.socket, WardrobeComposer.compose(2, Main.Wardrobes.values()));
  }
}


