package cappo.protocol.messages.events.inventory.achievements;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.achievements.UserAchievementManager;
import cappo.game.player.data.AvatarData;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.inventory.achievements.AchievementsComposer;
import cappo.protocol.messages.composers.inventory.achievements.AchievementsScoreComposer;
import java.util.Map;

public class GetAchievementsParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection cn)
  {
    QueueWriter.write(cn.socket, AchievementsComposer.compose(cn.avatarData.achievementManager.achievements.values(), ""));
    QueueWriter.write(cn.socket, AchievementsScoreComposer.compose(0));
  }
}


