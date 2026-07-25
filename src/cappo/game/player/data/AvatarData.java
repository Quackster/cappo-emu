package cappo.game.player.data;

import cappo.engine.settings.PerkAllowance;
import cappo.game.achievements.UserAchievementManager;
import cappo.game.collections.UnseenItems;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AvatarData
{
  public List<PerkAllowance> perksAllowances;
  public UserAchievementManager achievementManager;
  public UnseenItems UnseenItems;
  public Map<Integer, Integer> Achievements;
  public List<String> friendCategories;
  public List<Integer> ratedRooms;
  public long EcotronNextTime;
  public long lastChangeNameTry;
  public long lastCheckNameTry;
  public int AccessCount;
  public int LoadingRoom;
  public int TotalLengthHC;
  public int TotalLengthVIP;
  public int volume1 = 80;
  public int volume2 = 80;
  public int volume3 = 80;
  public boolean oldChatStyle;
  
  public AvatarData()
  {
    this.perksAllowances = new ArrayList();
    this.perksAllowances.add(new PerkAllowance("USE_GUIDE_TOOL", false, ""));
    this.perksAllowances.add(new PerkAllowance("GIVE_GUIDE_TOURS", false, ""));
    this.perksAllowances.add(new PerkAllowance("JUDGE_CHAT_REVIEWS", false, ""));
    this.perksAllowances.add(new PerkAllowance("VOTE_IN_COMPETITIONS", false, ""));
    this.perksAllowances.add(new PerkAllowance("CALL_ON_HELPERS", false, ""));
    
    this.perksAllowances.add(new PerkAllowance("CITIZEN", false, ""));
    
    this.perksAllowances.add(new PerkAllowance("TRADE", true, ""));
    this.perksAllowances.add(new PerkAllowance("HEIGHTMAP_EDITOR_BETA", true, ""));
    this.perksAllowances.add(new PerkAllowance("EXPERIMENTAL_CHAT_BETA", true, ""));
    this.perksAllowances.add(new PerkAllowance("EXPERIMENTAL_TOOLBAR", true, ""));
    this.perksAllowances.add(new PerkAllowance("NEW_UI", true, ""));
    
    this.achievementManager = new UserAchievementManager();
    this.UnseenItems = new UnseenItems();
    this.Achievements = new ConcurrentHashMap();
    this.friendCategories = new ArrayList();
    this.ratedRooms = new ArrayList();
  }
}


