package cappo.protocol.messages.events.sound;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;

public class SetSoundSettingsParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Main.avatarData.volume1 = Main.currentPacket.readInt();
    Main.avatarData.volume2 = Main.currentPacket.readInt();
    Main.avatarData.volume3 = Main.currentPacket.readInt();
  }
}


