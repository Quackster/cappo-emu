package cappo.game.roomeffects.special;

import cappo.game.roomeffects.UserEffect;
import cappo.game.roomeffects.UserEffectBase;
import cappo.game.roomengine.entity.live.Avatar;

public class UserSpecialEffect
  extends UserEffectBase
{
  public UserSpecialEffect(Avatar avatar, short effect)
  {
    super(avatar, effect);
  }
  
  public void stopEffect()
  {
    this.user.userSpecialEffect = null;
    if (this.user.userEffect != null) {
      this.user.userEffect.startEffect();
    } else {
      super.stopEffect();
    }
  }
}


