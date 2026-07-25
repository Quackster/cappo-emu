package cappo.protocol.messages.composers.avatar;

import cappo.engine.network.MessageWriter;
import cappo.game.collections.Wardrobe;
import cappo.game.player.AvatarLook;
import cappo.protocol.messages.Composer;
import java.util.Collection;

public class WardrobeComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int clubLevel, Collection<Wardrobe> Wardrobes)
  {
    MessageWriter ClientMessage = new MessageWriter(100 + Wardrobes.size() * 400);
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(clubLevel), ClientMessage);
    Composer.add(Integer.valueOf(clubLevel > 0 ? Wardrobes.size() : 0), ClientMessage);
    if (clubLevel > 0) {
      for (Wardrobe wrb : Wardrobes)
      {
        Composer.add(Short.valueOf(wrb.slotId), ClientMessage);
        if (!AvatarLook.validateLook(wrb.look))
        {
          wrb.look = "hr-115-42.hd-190-1.ch-215-62.lg-285-91.sh-290-62";
          wrb.gender = 1;
          wrb.needInsert = true;
        }
        Composer.add(wrb.look, ClientMessage);
        Composer.add(wrb.gender == 1 ? "M" : "F", ClientMessage);
      }
    }
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


