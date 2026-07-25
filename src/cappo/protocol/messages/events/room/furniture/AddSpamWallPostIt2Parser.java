package cappo.protocol.messages.events.room.furniture;
import cappo.game.roomengine.itemInteractor.Interactor;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.BaseItem;
import cappo.game.roomengine.entity.item.extradata.ExtraDataBase;
import cappo.game.roomengine.entity.item.wall.GenericWallItem;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.itemInteractor.Interactor.InteractorType;
import cappo.protocol.messages.IncomingMessageEvent;

public class AddSpamWallPostIt2Parser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    Avatar avatar = Main.avatar;
    if ((avatar == null) || (avatar.controllerLevel < 4)) {
      return;
    }
    RoomTask room = avatar.room;
    
    GenericWallItem item = (GenericWallItem)room.getWallItem(Main.currentPacket.readInt());
    if ((item == null) || (item.baseItem.interactorType != Interactor.InteractorType.postit)) {
      return;
    }
    Main.currentPacket.readString();
    
    String color = Main.currentPacket.readString();
    String str1 = color;
    boolean matched;
    switch (str1.hashCode())
    {
/* 34b:  */     case 1695802060:
      matched = str1.equals("9CCEFF");
      break;
    case 1695891988:
      matched = str1.equals("9CFF9C");
      break;
    case 2070451754:
    case 2070841312:
      matched = str1.equals("FF9CFF") || str1.equals("FFFF33");
      break;
/* 48b:  */     default:
/* 48c:  */       matched = false;
    }
    if (!matched) {
/* 50d:  */       return;
/* 50e:  */     }
    item.extraData.setExtraData(color.concat(" ").concat(Main.currentPacket.readString()));
    room.wallItemUpdateNeeded(item);
  }
}
