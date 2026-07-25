package cappo.game.achievements;

import cappo.game.achievements.identity.AchAvatarLooks;
import cappo.game.achievements.identity.AchEmailVerification;
import cappo.game.achievements.identity.AchLogin;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AchievementManager
{
  public static final int ACH_EmailVerification = 3;
  public static final int ACH_Login = 4;
  public static final int ACH_AvatarLooks = 6;
  public static final Map<Integer, AchievementBase> achievements = new ConcurrentHashMap();
  
  static
  {
    achievements.put(Integer.valueOf(3), new AchEmailVerification(3));
    achievements.put(Integer.valueOf(4), new AchLogin(4));
    achievements.put(Integer.valueOf(6), new AchAvatarLooks(6));
  }
}


