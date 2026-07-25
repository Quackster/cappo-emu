package cappo.game.roomgames.banzai;

import cappo.game.roomeffects.special.BanzaiEffect;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomgames.RoomGamePlayer;

public class BattleBanzai
{
  public static void togglePlayer(Avatar avatar, short team)
  {
    if (avatar.roomGamePlayer != null)
    {
      if ((avatar.roomGamePlayer instanceof BanzaiPlayer)) {
        avatar.roomGamePlayer.removePlayer();
      }
    }
    else
    {
      avatar.roomGamePlayer = new BanzaiPlayer(team, avatar);
      avatar.userSpecialEffect = new BanzaiEffect(avatar, (short)(team + 32));
    }
  }
}


