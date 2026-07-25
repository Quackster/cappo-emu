package cappo.protocol.messages.composers.room.bots;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class BotSkillComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int botId, int skillId, String data)
  {
    MessageWriter clientMessage = new MessageWriter();
    Composer.initPacket(HEADER, clientMessage);
    Composer.add(Integer.valueOf(botId), clientMessage);
    Composer.add(Integer.valueOf(skillId), clientMessage);
    Composer.add(data, clientMessage);
    Composer.endPacket(clientMessage);
    return clientMessage;
  }
}


