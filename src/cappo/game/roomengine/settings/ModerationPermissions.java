package cappo.game.roomengine.settings;

public class ModerationPermissions
{
  public static final int ONLY_OWNER = 0;
  public static final int WITH_RIGHTS = 1;
  public static final int ALL_USERS = 2;
  public int permissionsMute;
  public int permissionsKick;
  public int permissionsBan;
  private static final int PACK_NUMBITS = 3;
  private static final int PACK_BITS = 7;
  
  public ModerationPermissions(int data)
  {
    this.permissionsMute = getIntShiftBits(data, 0);
    this.permissionsKick = getIntShiftBits(data, 3);
    this.permissionsBan = getIntShiftBits(data, 6);
  }
  
  public int getIntValue()
  {
    return this.permissionsMute | this.permissionsKick << 3 | this.permissionsBan << 6;
  }
  
  private int getIntBits(long num)
  {
    return (int)(num & 0x7);
  }
  
  private int getIntShiftBits(int data, int shift)
  {
    if (shift > 0) {
      return getIntBits(data >>> shift);
    }
    return getIntBits(data);
  }
}


