package cappo.protocol.messages.composers.room.engine;

import cappo.engine.network.MessageWriter;
import cappo.game.roomengine.Square;
import cappo.game.roomengine.gamemap.GameMapBase;
import cappo.protocol.messages.Composer;

public class FloorHeightMapComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(GameMapBase model)
  {
    boolean scale = true;
    
    int len = model.widthX * model.heightY;
    int packetlen = len + (model.heightY - 1);
    MessageWriter writer = new MessageWriter(9 + packetlen);
    Composer.initPacket(HEADER, writer);
    Composer.writeBoolean(scale, writer);
    Composer.writeInt16(packetlen, writer);
    for (int xy = 0; xy < len; xy++)
    {
      if ((xy > 0) && (xy % model.widthX == 0)) {
        Composer.writeChar('\r', writer);
      }
      Square square = model.getSquare(xy);
      char c;
      if (square == null)
      {
        c = 'x';
      }
      else
      {
        int z = (int)square.height;
        c = Integer.toString(z, 36).charAt(0);
      }
      Composer.writeChar(c, writer);
    }
    Composer.endPacket(writer);
    
    return writer;
  }
}


