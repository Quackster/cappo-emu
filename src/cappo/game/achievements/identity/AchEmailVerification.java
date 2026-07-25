package cappo.game.achievements.identity;

import cappo.game.achievements.AchievementLevel;

public class AchEmailVerification
  extends AchievementBaseIdentity
{
  public AchEmailVerification(int id)
  {
    super(id, "ACH_EmailVerification");
    

    addLevel(new AchievementLevel(1));
  }
}


