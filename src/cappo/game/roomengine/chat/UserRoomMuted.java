package cappo.game.roomengine.chat;

import cappo.game.collections.Utils;

public class UserRoomMuted
{
  public long unMuteTimeStamp;
  
  public boolean isMuted()
/* ::  */   {
/* ;:9 */     return this.unMuteTimeStamp > Utils.getTimestamp();
/* <:  */   }
/* =:  */ }


