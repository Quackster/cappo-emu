package cappo.protocol.messages.composers.games.snowwar;

import cappo.engine.network.MessageWriter;
import cappo.game.games.snowwar.gameobjects.HumanGameObject;
import cappo.protocol.messages.Composer;
import java.util.Collection;

public class StageStillLoadingComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(Collection<HumanGameObject> playersLoaded)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(Integer.valueOf(playersLoaded.size()), ClientMessage);
    for (HumanGameObject player : playersLoaded) {
      Composer.add(Integer.valueOf(player.userId), ClientMessage);
    }
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


