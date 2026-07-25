package cappo.game.achievements.identity;

import cappo.game.achievements.AchievementLevel;

public class AchAvatarLooks
  extends AchievementBaseIdentity
{
  public AchAvatarLooks(int id)
  {
    super(id, "ACH_AvatarLooks");
    

    addLevel(new AchievementLevel(1));
  }
}


