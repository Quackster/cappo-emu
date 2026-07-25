package cappo.protocol.messages.events.navigator;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.navigator.PopularRoomTagsResultComposer;
import java.util.HashMap;
import java.util.Map;

public class GetPopularRoomTagsParser
  extends IncomingMessageEvent
{
  static final Map<String, Integer> tmp = new HashMap(0);
  
  public void messageReceived(Connection Main)
  {
    QueueWriter.write(Main.socket, PopularRoomTagsResultComposer.compose(tmp));
  }
}


