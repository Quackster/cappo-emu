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
    MessageWriter writer = new MessageWriter();
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