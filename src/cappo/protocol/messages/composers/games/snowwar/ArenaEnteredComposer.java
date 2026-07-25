package cappo.protocol.messages.composers.games.snowwar;

import cappo.engine.network.MessageWriter;
import cappo.game.games.snowwar.gameobjects.HumanGameObject;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializeGame2PlayerData;

public class ArenaEnteredComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(HumanGameObject Player)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    SerializeGame2PlayerData.parse(ClientMessage, Player);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


