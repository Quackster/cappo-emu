package cappo.protocol.messages.composers.room.furniture;

import cappo.engine.network.MessageWriter;
import cappo.game.roomengine.entity.item.extradata.ExtraDataBase;
import cappo.game.roomengine.entity.item.wall.GenericWallItem;
import cappo.protocol.messages.Composer;

public class RequestSpamWallPostItComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(GenericWallItem item)
  {
    String data = item.extraData.getWallLegacyString();
    MessageWriter ClientMessage = new MessageWriter(25 + data.length());
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.toString(item.itemId), ClientMessage);
    Composer.add(data, ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


