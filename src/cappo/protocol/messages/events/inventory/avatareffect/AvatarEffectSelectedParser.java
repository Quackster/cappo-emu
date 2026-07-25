package cappo.protocol.messages.events.inventory.avatareffect;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;

public class AvatarEffectSelectedParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection cn)
  {
    cn.applyEffect((short)cn.currentPacket.readInt());
  }
}


