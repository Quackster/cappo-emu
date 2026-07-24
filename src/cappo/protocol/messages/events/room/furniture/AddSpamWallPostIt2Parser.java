/*  1:   */ package cappo.protocol.messages.events.room.furniture;
import cappo.game.roomengine.itemInteractor.Interactor;
/*  2:   */ 
/*  3:   */ import cappo.engine.network.MessageReader;
/*  4:   */ import cappo.engine.player.Connection;
/*  5:   */ import cappo.engine.threadpools.RoomTask;
/*  6:   */ import cappo.game.collections.BaseItem;
/*  7:   */ import cappo.game.roomengine.entity.item.extradata.ExtraDataBase;
/*  8:   */ import cappo.game.roomengine.entity.item.wall.GenericWallItem;
/*  9:   */ import cappo.game.roomengine.entity.live.Avatar;
/* 10:   */ import cappo.game.roomengine.itemInteractor.Interactor.InteractorType;
/* 11:   */ import cappo.protocol.messages.IncomingMessageEvent;
/* 12:   */ 
/* 13:   */ public class AddSpamWallPostIt2Parser
/* 14:   */   extends IncomingMessageEvent
/* 15:   */ {
/* 16:   */   public void messageReceived(Connection Main)
/* 17:   */   {
/* 18:20 */     Avatar avatar = Main.avatar;
/* 19:21 */     if ((avatar == null) || (avatar.controllerLevel < 4)) {
/* 20:22 */       return;
/* 21:   */     }
/* 22:25 */     RoomTask room = avatar.room;
/* 23:   */     
/* 24:27 */     GenericWallItem item = (GenericWallItem)room.getWallItem(Main.currentPacket.readInt());
/* 25:28 */     if ((item == null) || (item.baseItem.interactorType != Interactor.InteractorType.postit)) {
/* 26:29 */       return;
/* 27:   */     }
/* 28:32 */     Main.currentPacket.readString();
/* 29:   */     
/* 30:34 */     String color = Main.currentPacket.readString();
/* 31:   */     String str1 = color;
/* 32:35 */     boolean matched;
/* 33:   */     switch (str1.hashCode())
/* 34:   */     {
/* 34b:  */     case 1695802060:
/* 35:35 */       matched = str1.equals("9CCEFF");
/* 37:   */       break;
/* 39:   */     case 1695891988:
/* 40:35 */       matched = str1.equals("9CFF9C");
/* 42:   */       break;
/* 44:   */     case 2070451754:
/* 45:   */     case 2070841312:
/* 46:35 */       matched = str1.equals("FF9CFF") || str1.equals("FFFF33");
/* 48:   */       break;
/* 48b:  */     default:
/* 48c:  */       matched = false;
/* 49:   */     }
/* 50:43 */     if (!matched) {
/* 50d:  */       return;
/* 50e:  */     }
/* 52:46 */     item.extraData.setExtraData(color.concat(" ").concat(Main.currentPacket.readString()));
/* 53:47 */     room.wallItemUpdateNeeded(item);
/* 54:   */   }
/* 55:   */ }

/* Location:           C:\Users\Manel\Downloads\cappo.zip
 * Qualified Name:     cappo.protocol.messages.events.room.furniture.AddSpamWallPostIt2Parser
 * JD-Core Version:    0.7.0.1
 */