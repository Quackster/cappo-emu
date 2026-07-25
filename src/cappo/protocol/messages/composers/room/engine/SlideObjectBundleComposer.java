package cappo.protocol.messages.composers.room.engine;

import cappo.engine.network.MessageWriter;
import cappo.game.rollers.RollerMoveDataEntity;
import cappo.game.rollers.RollerMoveDataObject;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.live.LiveEntity;
import cappo.protocol.messages.Composer;
import java.util.List;

public class SlideObjectBundleComposer
{
  public static final int MOVETYPE_NONE = 0;
  public static final int MOVETYPE_MV = 1;
  public static final int MOVETYPE_STD = 2;
  public static int HEADER;
  
  public static final MessageWriter compose(FloorItem roller, int nextX, int nextY, List<RollerMoveDataObject> stackedItems, RollerMoveDataEntity moveDataEntity)
  {
    MessageWriter ClientMessage = new MessageWriter(500 + stackedItems.size() * 40);
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(roller.getX()), ClientMessage);
    Composer.add(Integer.valueOf(roller.getY()), ClientMessage);
    Composer.add(Integer.valueOf(nextX), ClientMessage);
    Composer.add(Integer.valueOf(nextY), ClientMessage);
    Composer.add(Integer.valueOf(stackedItems.size()), ClientMessage);
    for (RollerMoveDataObject stacked : stackedItems)
    {
      Composer.add(Integer.valueOf(stacked.item.itemId), ClientMessage);
      Composer.add(Float.toString(stacked.fromZ).replace(',', '.'), ClientMessage);
      Composer.add(Float.toString(stacked.item.getZ()).replace(',', '.'), ClientMessage);
    }
    Composer.add(Integer.valueOf(roller.itemId), ClientMessage);
    if (moveDataEntity != null)
    {
      Composer.add(Integer.valueOf(moveDataEntity.entityMoveType), ClientMessage);
      if (moveDataEntity.entityMoveType != 0)
      {
        Composer.add(Short.valueOf(moveDataEntity.entity.virtualId), ClientMessage);
        Composer.add(Float.toString(moveDataEntity.fromZ).replace(',', '.'), ClientMessage);
        Composer.add(Float.toString(moveDataEntity.entity.z).replace(',', '.'), ClientMessage);
      }
    }
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
  
  public static final MessageWriter compose(int fromX, int fromY, RollerMoveDataObject stacked)
  {
    MessageWriter ClientMessage = new MessageWriter(500);
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(fromX), ClientMessage);
    Composer.add(Integer.valueOf(fromY), ClientMessage);
    Composer.add(Integer.valueOf(stacked.item.getX()), ClientMessage);
    Composer.add(Integer.valueOf(stacked.item.getY()), ClientMessage);
    Composer.add(Integer.valueOf(1), ClientMessage);
    
    Composer.add(Integer.valueOf(stacked.item.itemId), ClientMessage);
    Composer.add(Float.toString(stacked.fromZ).replace(',', '.'), ClientMessage);
    Composer.add(Float.toString(stacked.item.getZ()).replace(',', '.'), ClientMessage);
    
    Composer.add(Integer.valueOf(-1), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


