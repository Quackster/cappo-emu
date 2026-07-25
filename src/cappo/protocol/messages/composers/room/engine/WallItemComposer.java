package cappo.protocol.messages.composers.room.engine;
import cappo.game.roomengine.itemInteractor.Interactor;

import cappo.engine.network.MessageWriter;
import cappo.game.collections.BaseItem;
import cappo.game.roomengine.entity.item.extradata.ExtraDataBase;
import cappo.game.roomengine.entity.item.wall.GenericWallItem;
import cappo.game.roomengine.itemInteractor.Interactor.InteractorType;
import cappo.protocol.messages.Composer;

public class WallItemComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(GenericWallItem item)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.toString(item.itemId), ClientMessage);
    if (item.baseItem.interactorType == Interactor.InteractorType.postit) {
      Composer.add(item.extraData.getWallLegacyString().split(" ")[0], ClientMessage);
    } else {
      Composer.add(item.extraData.getWallLegacyString(), ClientMessage);
    }
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}
