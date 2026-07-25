package cappo.protocol.messages.composers.games.snowwar;

import cappo.engine.network.MessageWriter;
import cappo.game.player.PlayerData;
import cappo.protocol.messages.Composer;

public class GameDirectoryStatusComposer
{
  public static final int ENABLED = 0;
  public static final int UNKNOW1 = 1;
  public static final int UNKNOW2 = 2;
  public static final int UNKNOW3 = 3;
  public static int HEADER;
  
  public static final MessageWriter compose(PlayerData player, int state)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(state), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(Integer.valueOf(-1), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


