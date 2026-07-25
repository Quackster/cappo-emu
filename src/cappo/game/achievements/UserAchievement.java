package cappo.game.achievements;

import java.util.List;

public class UserAchievement
{
  public AchievementBase achievement;
  public int progress;
  public int level;
  public boolean achieved;
  
  public UserAchievement(AchievementBase ach, int Progress)
  {
    this.achievement = ach;
    this.progress = Progress;
  }
  
  public int getNextNivel()
  {
    if (this.achievement.levels.size() > this.level) {
      return this.level + 1;
    }
    return this.level;
  }
  
  public int getPrevGoal()
  {
    if (this.level <= 1) {
      return 0;
    }
    return ((AchievementLevel)this.achievement.levels.get(this.level - 2)).levelGoal;
  }
  
  public int getNextGoal()
  {
    if (this.achievement.levels.isEmpty()) {
      return 0;
    }
    if (this.achievement.levels.size() > this.level) {
      return ((AchievementLevel)this.achievement.levels.get(this.level)).levelGoal;
    }
    return ((AchievementLevel)this.achievement.levels.get(this.level - 1)).levelGoal;
  }
}


