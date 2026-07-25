package cappo.game.achievements;

public class AchievementLevel
{
  public static final int REWARD_PIXELS = 0;
  public int levelGoal;
  public int rewardPoints;
  public int rewardType;
  
  public AchievementLevel(int goal)
  {
    this.levelGoal = goal;
  }
  
  public AchievementLevel(int goal, int points, int type)
  {
    this.levelGoal = goal;
    this.rewardPoints = points;
    this.rewardType = type;
  }
}


