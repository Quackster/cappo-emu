package cappo.protocol.messages.events.users;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.notifications.MOTDComposer;

public class GetMOTDParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    if (!cappo.game.utils.lang.LangTexts.texts[5].isEmpty()) {
      QueueWriter.write(Main.socket, MOTDComposer.compose(new String[] { cappo.game.utils.lang.LangTexts.texts[5] }));
    }
  }
}


