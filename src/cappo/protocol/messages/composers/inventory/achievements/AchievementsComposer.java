package cappo.protocol.messages.composers.inventory.achievements;

import cappo.engine.network.MessageWriter;
import cappo.game.achievements.AchievementBase;
import cappo.game.achievements.UserAchievement;
import cappo.protocol.messages.Composer;
import java.util.Collection;
import java.util.List;

public class AchievementsComposer
{
  public static int HEADER;
  
  public static MessageWriter compose(Collection<UserAchievement> values, String openCategory)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.writeInt32(values.size(), ClientMessage);
    for (UserAchievement ach : values)
    {
      int nextLevel = ach.getNextNivel();
      Composer.writeInt32(ach.achievement.achId, ClientMessage);
      Composer.writeInt32(nextLevel, ClientMessage);
      Composer.add(ach.achievement.badgeId + nextLevel, ClientMessage);
      Composer.writeInt32(ach.getPrevGoal(), ClientMessage);
      Composer.writeInt32(ach.getNextGoal(), ClientMessage);
      Composer.writeInt32(0, ClientMessage);
      Composer.writeInt32(0, ClientMessage);
      Composer.writeInt32(ach.progress, ClientMessage);
      Composer.add(Boolean.valueOf(ach.achieved), ClientMessage);
      Composer.add(ach.achievement.categoryName, ClientMessage);
      Composer.add("", ClientMessage);
      Composer.writeInt32(ach.achievement.levels.size(), ClientMessage);
      Composer.writeInt32(0, ClientMessage);
    }
    Composer.add(openCategory, ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


