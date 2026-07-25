package cappo.game.achievements;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserAchievementManager
{
  public Map<Integer, UserAchievement> achievements = new ConcurrentHashMap();
  
  public void fillAchievements()
  {
    for (AchievementBase ach : AchievementManager.achievements.values()) {
      if (!this.achievements.containsKey(Integer.valueOf(ach.achId))) {
        this.achievements.put(Integer.valueOf(ach.achId), new UserAchievement(ach, 0));
      }
    }
  }
}


