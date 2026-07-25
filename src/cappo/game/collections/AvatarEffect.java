package cappo.game.collections;

public class AvatarEffect
{
  public static final int EFFECTS = 0;
  public static final int COSTUMES = 1;
  public boolean Activated;
  public int effectType;
  public int noNamed;
  public long ActivateTimestamp;
  public int TotalDuration;
  
  public AvatarEffect(int effectId, int totalDuration, boolean activated, long activateTimestamp)
  {
    this.effectType = effectId;
    this.noNamed = 0;
    this.TotalDuration = totalDuration;
    this.Activated = activated;
    this.ActivateTimestamp = activateTimestamp;
  }
}


