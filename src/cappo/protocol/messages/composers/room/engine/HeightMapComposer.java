package cappo.protocol.messages.composers.room.engine;

import cappo.engine.network.MessageWriter;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.Square;
import cappo.game.roomengine.gamemap.GameMapBase;
import cappo.protocol.messages.Composer;

public class HeightMapComposer
{
  public static int HEADER;

  public static final MessageWriter compose(RoomTask room)
  {
    GameMapBase model = room.model;

    int len = model.widthX * model.heightY;
    StringBuilder sb = new StringBuilder(len + model.heightY);
    for (int xy = 0; xy < len; xy++)
    {
      if ((xy > 0) && (xy % model.widthX == 0)) {
        sb.append('\r');
      }
      Square square = model.getSquare(xy);
      if (square == null)
      {
        sb.append('x');
      }
      else
      {
        sb.append(heightChar(square.height));
      }
    }
    // The heightmap is a single length-prefixed string, so the buffer must hold
    // the whole string plus the 4-byte length prefix and 2-byte header. The
    // default 1000-byte MessageWriter overflows for any room larger than ~31x31
    // (e.g. model_2=2039, model_5=1189, model_8=1187 bytes), which throws an
    // ArrayIndexOutOfBoundsException and aborts the whole room-entry sequence.
    MessageWriter writer = new MessageWriter(sb.length() + 16);
    Composer.initPacket(HEADER, writer);
    Composer.add(sb.toString(), writer);
    Composer.endPacket(writer);
    return writer;
  }

  private static char heightChar(float height)
  {
    int h = (int)height;
    if (h < 0) { h = 0; }
    if (h > 15) { h = 15; }
    return Integer.toString(h, 16).charAt(0);
  }
}