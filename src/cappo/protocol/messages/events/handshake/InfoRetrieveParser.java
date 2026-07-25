package cappo.protocol.messages.events.handshake;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.player.PlayerData;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.availability.AvailabilityStatusComposer;
import cappo.protocol.messages.composers.handshake.PerkAllowancesComposer;
import cappo.protocol.messages.composers.handshake.UserInfoComposer;
import cappo.protocol.messages.composers.handshake.UserLevelsComposer;
import cappo.protocol.messages.composers.inventory.avatareffect.EffectsComposer;
import cappo.protocol.messages.composers.notifications.ActivityPointsComposer;

public class InfoRetrieveParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection cn)
  {
    QueueWriter.write(cn.socket, EffectsComposer.compose(cn.avatarEffects));
    QueueWriter.write(cn.socket, UserLevelsComposer.compose(2, cn.playerData.staffLevel));
    QueueWriter.write(cn.socket, AvailabilityStatusComposer.compose());
    QueueWriter.write(cn.socket, ActivityPointsComposer.compose(cn.pixelAmmount, cn.diamondAmmount));
    QueueWriter.write(cn.socket, UserInfoComposer.compose(cn, Boolean.valueOf(false)));
    QueueWriter.write(cn.socket, PerkAllowancesComposer.compose(cn.avatarData));
  }
}


