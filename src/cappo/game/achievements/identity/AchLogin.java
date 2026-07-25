package cappo.game.achievements.identity;

import cappo.game.achievements.AchievementLevel;

public class AchLogin
  extends AchievementBaseIdentity
{
  public AchLogin(int id)
  {
    super(id, "ACH_Login");
    

    addLevel(new AchievementLevel(5));
    
    addLevel(new AchievementLevel(8));
  }
}


