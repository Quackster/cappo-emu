package cappo.protocol.messages.events.navigator;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.navigator.FlatCategoriesComposer;

public class GetUserFlatCatsParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    QueueWriter.write(Main.socket, FlatCategoriesComposer.compose());
  }
}


