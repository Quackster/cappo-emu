package cappo.game.roomengine.settings;

public class ChatSettings
{
  public static final int MODE_1 = 0;
  public static final int MODE_2 = 1;
  public static final int WIDTH_LARGE = 0;
  public static final int WIDTH_MEDIUM = 1;
  public static final int WIDTH_SMALL = 2;
  public static final int SPEED_FAST = 0;
  public static final int SPEED_NORMAL = 1;
  public static final int SPEED_SLOW = 2;
  public int chatMode;
  public int chatBubbleWidth;
  public int chatScrollSpeed;
  public int chatHearingDistance;
  public int chatFloodSensitivity;
  
  public ChatSettings(int data)
  {
    this.chatMode = getIntShiftBits(data, 0, 7);
    this.chatBubbleWidth = getIntShiftBits(data, 3, 7);
    this.chatScrollSpeed = getIntShiftBits(data, 6, 7);
    this.chatHearingDistance = getIntShiftBits(data, 9, 31);
    this.chatFloodSensitivity = getIntShiftBits(data, 14, 7);
  }
  
  public int getIntValue()
  {
    return this.chatMode | 
      this.chatBubbleWidth << 3 | 
      this.chatScrollSpeed << 6 | 
      this.chatHearingDistance << 9 | 
      this.chatFloodSensitivity << 14;
  }
  
  private int getIntBits(long num, int bits)
  {
    return (int)(num & bits);
  }
  
  private int getIntShiftBits(int data, int shift, int bits)
  {
    if (shift > 0) {
      return getIntBits(data >>> shift, bits);
    }
    return getIntBits(data, bits);
  }
}


