package cappo.game.roomeffects;

import cappo.engine.network.QueueWriter;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomeffects.special.UserSpecialEffect;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.protocol.messages.composers.room.action.UserEffectComposer;
import io.netty.channel.Channel;

public abstract class UserEffectBase
{
  public Avatar user;
  public short effectId;
  
  public UserEffectBase(Avatar avatar, short effect)
  {
    this.user = avatar;
    this.effectId = effect;
    startEffect();
  }
  
  public void startEffect()
  {
    if (((this instanceof UserSpecialEffect)) || (this.user.userSpecialEffect == null)) {
      this.user.room.sendMessage(UserEffectComposer.compose(this.user.virtualId, this.effectId));
    }
  }
  
  public void startEffect(Channel socket)
  {
    if (((this instanceof UserSpecialEffect)) || (this.user.userSpecialEffect == null)) {
      QueueWriter.writeAndFlush(socket, UserEffectComposer.compose(this.user.virtualId, this.effectId));
    }
  }
  
  public void stopEffect()
  {
    this.user.userEffect = null;
    if (this.user.userSpecialEffect == null) {
      this.user.room.sendMessage(UserEffectComposer.compose(this.user.virtualId, -1));
    }
  }
}


