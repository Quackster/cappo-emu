package cappo.game.collections;

public class Badge
{
  public int badgeId;
  public String badgeCode;
  public int badgeSlot;
  public boolean needInsert;
  
  public Badge(int id, String code, int slot)
  {
    this.badgeId = id;
    this.badgeCode = code;
    this.badgeSlot = slot;
  }
}


