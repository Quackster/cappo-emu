package cappo.protocol.messages.events.inventory.badges;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.inventory.badges.BadgesComposer;
import java.util.Map;

public class GetBadgesParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    QueueWriter.write(Main.socket, BadgesComposer.compose(Main.badges.values(), Main.badgesSelected.values()));
  }
}


