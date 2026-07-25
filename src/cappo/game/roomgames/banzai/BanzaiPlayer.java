package cappo.game.roomgames.banzai;

import cappo.game.roomeffects.special.UserSpecialEffect;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomgames.RoomGamePlayer;

public class BanzaiPlayer
  extends RoomGamePlayer
{
  public BanzaiPlayer(short playerTeam, Avatar player)
  {
    super(playerTeam, player);
  }
  
  public void removePlayer()
  {
    super.removePlayer();
    this.avatarEntity.userSpecialEffect.stopEffect();
  }
}


