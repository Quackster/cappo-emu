package cappo.game.collections;

public class Wardrobe
{
  public short slotId;
  public String look;
  public short gender;
  public boolean needInsert;
  
  public Wardrobe(int slot, String sLook, int sex)
  {
    this.slotId = ((short)slot);
    this.look = sLook;
    this.gender = ((short)sex);
  }
}


