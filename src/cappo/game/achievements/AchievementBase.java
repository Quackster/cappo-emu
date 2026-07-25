package cappo.game.achievements;

import java.util.ArrayList;
import java.util.List;

public class AchievementBase
{
  public final int achId;
  public final String badgeId;
  public final String categoryName;
  public final List<AchievementLevel> levels;
  
  public AchievementBase(int id, String badge, String category)
  {
    this.achId = id;
    this.badgeId = badge;
    this.categoryName = category;
    this.levels = new ArrayList();
  }
  
  public void addLevel(AchievementLevel level)
  {
    this.levels.add(level);
  }
}


