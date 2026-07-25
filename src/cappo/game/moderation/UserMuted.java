package cappo.game.moderation;

import cappo.game.collections.Utils;

public class UserMuted
{
  public long unMuteTimeStamp;
  public String reason;
  
  public boolean isMuted()
  {
    return this.unMuteTimeStamp > Utils.getTimestamp();
  }
}


