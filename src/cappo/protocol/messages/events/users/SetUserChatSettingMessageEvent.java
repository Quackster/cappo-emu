package cappo.protocol.messages.events.users;

import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.parsers.users.SetUserChatSettingMessageParser;

public class SetUserChatSettingMessageEvent
  extends IncomingMessageEvent
{
  public void messageReceived(Connection cn)
    throws Exception
  {
    SetUserChatSettingMessageParser parser = new SetUserChatSettingMessageParser(
      cn.currentPacket, 
      cn.avatarData);
    if (!parser.isValid) {
      return;
    }
    parser.setChatStyle();
  }
}


