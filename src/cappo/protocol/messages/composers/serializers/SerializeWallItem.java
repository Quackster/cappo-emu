package cappo.protocol.messages.composers.serializers;
import cappo.game.roomengine.itemInteractor.Interactor;

import cappo.engine.network.MessageWriter;
import cappo.game.collections.BaseItem;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.entity.item.extradata.ExtraDataBase;
import cappo.game.roomengine.entity.item.wall.GenericWallItem;
import cappo.game.roomengine.itemInteractor.Interactor.InteractorType;
import cappo.protocol.messages.Composer;

public class SerializeWallItem
{
  public static void parse(MessageWriter ClientMessage, GenericWallItem item)
  {
    Composer.add(Integer.toString(item.itemId), ClientMessage);
    Composer.add(Integer.valueOf(item.baseItem.SpriteId), ClientMessage);
    Composer.add(item.roomDataString(), ClientMessage);
    if (item.baseItem.interactorType == Interactor.InteractorType.postit) {
      Composer.add(item.extraData.getWallLegacyString().split(" ")[0], ClientMessage);
    } else {
      Composer.add(item.extraData.getWallLegacyString(), ClientMessage);
    }
    Composer.add(Integer.valueOf(-1), ClientMessage);
    Composer.add(Integer.valueOf(1), ClientMessage);
    Composer.add(Integer.valueOf(item.owner.userId), ClientMessage);
  }
}
