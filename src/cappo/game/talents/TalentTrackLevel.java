package cappo.game.talents;

import cappo.game.talents.rewards.badges.TalentTrackRewardBadge;
import cappo.game.talents.rewards.products.TalentTrackRewardProduct;
import java.util.ArrayList;
import java.util.List;

public class TalentTrackLevel
{
  public static final int LOCKED = 0;
  public static final int UNLOKED = 1;
  public static final int ACHIEVED = 2;
  public List<TalentTrackRewardBadge> badgeRewards = new ArrayList();
  public List<TalentTrackRewardProduct> productRewards = new ArrayList();
  
  public void addBadge(TalentTrackRewardBadge badge)
  {
    this.badgeRewards.add(badge);
  }
  
  public void addProduct(TalentTrackRewardProduct product)
  {
    this.productRewards.add(product);
  }
}


