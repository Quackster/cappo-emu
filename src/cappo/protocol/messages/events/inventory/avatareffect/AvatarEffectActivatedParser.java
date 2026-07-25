package cappo.protocol.messages.events.inventory.avatareffect;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;

public class AvatarEffectActivatedParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Main.EnableEffect(Main.currentPacket.readInt());
  }
}


