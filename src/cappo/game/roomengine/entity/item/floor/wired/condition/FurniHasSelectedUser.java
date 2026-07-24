/*  1:   */ package cappo.game.roomengine.entity.item.floor.wired.condition;
import cappo.game.roomengine.entity.item.floor.RoomFloorItemData;
/*  2:   */ 
/*  3:   */ import cappo.engine.player.Connection;
/*  4:   */ import cappo.game.roomengine.entity.item.floor.FloorItem;
/*  5:   */ import cappo.game.roomengine.entity.item.floor.RoomFloorItemData.AffectedTile;
/*  6:   */ import cappo.game.roomengine.entity.live.Avatar;
/*  7:   */ import java.util.Collection;
/*  8:   */ import java.util.Iterator;
/*  9:   */ import java.util.List;
/* 10:   */ import java.util.Map;
/* 11:   */ 
/* 12:   */ public class FurniHasSelectedUser
/* 13:   */   extends WiredConditionBase
/* 14:   */ {
/* 15:   */   public int getCode()
/* 16:   */   {
/* 17:18 */     return 8;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public boolean needUser()
/* 21:   */   {
/* 22:23 */     return true;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public boolean checkCondition(Connection invoker)
/* 26:   */   {
/* 28:28 */     for (FloorItem floorItem : this.items.values())
/* 29:   */     {
/* 30:28 */       List<RoomFloorItemData.AffectedTile> PointList = floorItem.getAffectedTiles();
/* 31:30 */       for (RoomFloorItemData.AffectedTile Tile : PointList)
/* 32:   */       {
/* 33:31 */         if (invoker.avatar.xy == Tile.xy) {
/* 34:32 */           return true;
/* 35:   */         }
/* 36:   */       }
/* 37:   */     }
/* 38:37 */     return false;
/* 38:   */   }
/* 39:   */ }



/* Location:           C:\Users\Manel\Downloads\cappo.zip

 * Qualified Name:     cappo.game.roomengine.entity.item.floor.wired.condition.FurniHasSelectedUser

 * JD-Core Version:    0.7.0.1

 */