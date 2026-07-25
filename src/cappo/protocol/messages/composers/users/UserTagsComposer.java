package cappo.protocol.messages.composers.users;

import cappo.engine.network.MessageWriter;
import cappo.game.player.PlayerData;
import cappo.protocol.messages.Composer;

public class UserTagsComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(PlayerData Client)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(Client.userId), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    



    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


