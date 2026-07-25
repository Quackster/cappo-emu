package cappo.game.player;

import cappo.game.collections.Utils;

public class AvatarLook
{
  public static final int SETS_HD = 0;
  public static final int SETS_HR = 1;
  public static final int SETS_HA = 2;
  public static final int SETS_HE = 3;
  public static final int SETS_EA = 4;
  public static final int SETS_FA = 5;
  public static final int SETS_CC = 6;
  public static final int SETS_CH = 7;
  public static final int SETS_CA = 8;
  public static final int SETS_CP = 9;
  public static final int SETS_LG = 10;
  public static final int SETS_SH = 11;
  public static final int SETS_WA = 12;
  public static final int SETS_PARTS = 13;
  public static final String[] sets = new String[13];
  public long[] parts = new long[13];
  private static final int PACK_NUMBITS = 12;
  private static final int PACK_BITS = 4095;
  
  static
  {
    sets[0] = "hd";
    sets[1] = "hr";
    sets[2] = "ha";
    sets[3] = "he";
    sets[4] = "ea";
    sets[5] = "fa";
    sets[6] = "cc";
    sets[7] = "ch";
    sets[8] = "ca";
    sets[9] = "cp";
    sets[10] = "lg";
    sets[11] = "sh";
    sets[12] = "wa";
  }
  
  public String toString()
  {
    return filterLook(new int[] {
      0, 
      1, 
      2, 
      3, 
      4, 
      5, 
      6, 
      7, 
      8, 
      9, 
      10, 
      11, 
      12 });
  }
  
  public AvatarLook(String lookStr)
  {
    String[] tmp = lookStr.split("\\.");
    for (String part : tmp)
    {
      String[] Set = part.split("-");
      if ((Set.length >= 2) && (Set.length <= 5) && (!part.endsWith("-")))
      {
        int type = 0;
        
        int a = 0;
        for (String s : sets)
        {
          if (s.equals(Set[0]))
          {
            type = a;
            a = -1;
            break;
          }
          a++;
        }
        if (a < 0)
        {
          int set = 0;
          
          a = 0;
          do
          {
            try
            {
              int val = Integer.parseInt(Set[a]) & 0xFFF;
              if (a == 1) {
                set |= val;
              } else if (a == 2) {
                set |= val << 12;
              } else if (a == 3) {
                set |= val << 24;
              } else if (a == 4) {
                set |= val << 36;
              }
            }
            catch (NumberFormatException ex)
            {
              set = 0;
              break;
            }
            a++;
          } while (a < Set.length);
          if (set != 0) {
            this.parts[type] = set;
          }
        }
      }
    }
  }
  
  public AvatarLook()
  {
    this.parts[1] = 172147L;
    this.parts[0] = 4286L;
    this.parts[7] = 254167L;
    this.parts[10] = 373021L;
    this.parts[11] = 254242L;
  }
  
  private int getIntBits(long num)
  {
    return (int)(num & 0xFFF);
  }
  
  private int getIntShiftBits(int type, int shift)
  {
    if (shift > 0) {
      return getIntBits(this.parts[type] >>> shift);
    }
    return getIntBits(this.parts[type]);
  }
  
  public void setPart(int type, AvatarLook from)
  {
    this.parts[type] = from.parts[type];
  }
  
  public String filterLook(int... types)
  {
    StringBuilder look = new StringBuilder(32);
    for (int type : types)
    {
      int partSet = getIntShiftBits(type, 0);
      if (partSet >= 1)
      {
        if (look.length() > 0) {
          look.append(".");
        }
        look.append(sets[type]);
        look.append("-");
        look.append(Integer.toString(partSet));
        
        int colors = getIntShiftBits(type, 12);
        if (colors > 0)
        {
          look.append("-");
          look.append(Integer.toString(colors));
          
          colors = getIntShiftBits(type, 24);
          if (colors > 0)
          {
            look.append("-");
            look.append(Integer.toString(colors));
            
            colors = getIntShiftBits(type, 36);
            if (colors > 0)
            {
              look.append("-");
              look.append(Integer.toString(colors));
            }
          }
        }
      }
    }
    return look.toString();
  }
  
  private static boolean isBadType(String string)
  {
    for (String set : sets) {
      if (set.equals(string)) {
        return false;
      }
    }
    return true;
  }
  
  public static boolean validateLook(String SelectedLook)
  {
    for (String part : SelectedLook.split("\\."))
    {
      String[] set = part.split("-");
      if ((set.length < 2) || (set.length > 5) || (part.endsWith("-"))) {
        return false;
      }
      if (isBadType(set[0])) {
        return false;
      }
      int a = 1;
      while (a < set.length) {
        if (Utils.isBadInteger(set[(a++)])) {
          return false;
        }
      }
    }
    return true;
  }
}


