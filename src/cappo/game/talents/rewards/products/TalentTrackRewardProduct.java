package cappo.game.talents.rewards.products;

public class TalentTrackRewardProduct
{
  public final String product;
  public final int extraValue;
  
  public TalentTrackRewardProduct(String reward)
  {
    this.product = reward;
    this.extraValue = 0;
  }
  
  public TalentTrackRewardProduct(String reward, int extra)
  {
    this.product = reward;
    this.extraValue = extra;
  }
}


