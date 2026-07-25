package cappo.protocol.messages.events.avatar;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.game.collections.Wardrobe;
import cappo.game.player.AvatarLook;
import cappo.protocol.messages.IncomingMessageEvent;
import java.util.Map;

public class SaveWardrobeOutfitParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    int slotId = Main.currentPacket.readInt();
    String SelectedLook = Main.currentPacket.readString();
    if (!AvatarLook.validateLook(SelectedLook)) {
      SelectedLook = "hr-115-42.hd-190-1.ch-215-62.lg-285-91.sh-290-62";
    }
    Wardrobe wrb = new Wardrobe(slotId, SelectedLook, Main.currentPacket.readString().equals("M") ? 1 : 0);
    wrb.needInsert = true;
    Main.Wardrobes.put(Short.valueOf(wrb.slotId), wrb);
  }
}


