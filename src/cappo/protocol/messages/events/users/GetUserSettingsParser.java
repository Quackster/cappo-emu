package cappo.protocol.messages.events.users;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.users.UserSettingsComposer;

public class GetUserSettingsParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    QueueWriter.write(Main.socket, UserSettingsComposer.compose(Main.avatarData));
  }
}


