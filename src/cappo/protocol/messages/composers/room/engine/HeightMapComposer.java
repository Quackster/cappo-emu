package cappo.protocol.messages.composers.room.engine;

import cappo.engine.network.MessageWriter;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.Square;
import cappo.game.roomengine.gamemap.GameMapBase;
import cappo.protocol.messages.Composer;
import java.util.Map;

public class HeightMapComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(RoomTask room)
  {
    GameMapBase model = room.model;
    
    int len = model.widthX * model.heightY;
    MessageWriter writer = new MessageWriter(14 + len * 2);
    Composer.initPacket(HEADER, writer);
    Composer.writeInt32(model.widthX, writer);
    Composer.writeInt32(len, writer);
    for (int xy = 0; xy < len; xy++)
    {
      Square square = model.getSquare(xy);
      if (square == null)
      {
        Composer.writeInt16(16384, writer);
      }
      else
      {
        Float newZ = (Float)room.squareAbsoluteHeight.get(Integer.valueOf(xy));
        if (newZ == null) {
          Composer.writeInt16(16384, writer);
        } else {
          Composer.writeInt16(newZ.intValue() * 256, writer);
        }
      }
    }
    Composer.endPacket(writer);
    return writer;
  }
}


