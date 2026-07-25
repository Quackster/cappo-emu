package cappo.protocol.messages.composers.room.engine;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class UserChangeComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int VirtualId, String Look, int Sex, String Motto, int AchievementsScore)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(VirtualId), ClientMessage);
    Composer.add(Look, ClientMessage);
    Composer.add(Sex == 1 ? "M" : "F", ClientMessage);
    Composer.add(Motto, ClientMessage);
    Composer.add(Integer.valueOf(AchievementsScore), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


