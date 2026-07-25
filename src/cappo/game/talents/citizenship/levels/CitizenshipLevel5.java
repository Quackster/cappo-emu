package cappo.game.talents.citizenship.levels;

import cappo.game.talents.TalentTrackLevel;
import cappo.game.talents.rewards.badges.TalentTrackRewardBadge;
import cappo.game.talents.rewards.products.TalentTrackRewardGiveVip;
import cappo.game.talents.rewards.products.TalentTrackRewardProduct;
import java.util.List;

public class CitizenshipLevel5
  extends TalentTrackLevel
{
  public CitizenshipLevel5()
  {
    this.badgeRewards.add(new TalentTrackRewardBadge("CITIZEN"));
    this.productRewards.add(new TalentTrackRewardProduct("A1 KUMIANKKA"));
    this.productRewards.add(new TalentTrackRewardGiveVip(7));
  }
}


