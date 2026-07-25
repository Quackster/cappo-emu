package cappo.protocol.messages.composers.inventory.achievements;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class AchievementsScoreComposer
{
  public static int HEADER;
  
  public static MessageWriter compose(int score)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(score), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


