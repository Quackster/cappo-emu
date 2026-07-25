package cappo.protocol.messages.composers.games.snowwar;

import cappo.engine.network.MessageWriter;
import cappo.game.games.snowwar.gameobjects.HumanGameObject;
import cappo.protocol.messages.Composer;

public class PlayerExitedGameArenaComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(HumanGameObject Player)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(Player.userId), ClientMessage);
    Composer.add(Integer.valueOf(Player.objectId), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


