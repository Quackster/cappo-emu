package cappo.game.roomengine.entity.item.extradata;

public class StuffDataReader
{
  public int type;
  public byte[] bytes;
  public int reader;
  
  public StuffDataReader(byte[] arr)
  {
    if (arr == null)
    {
      this.bytes = new byte[2];
      return;
    }
    this.bytes = arr;
    this.type = readInt8();
  }
  
  public int readInt32()
  {
    return ((this.bytes[(this.reader++)] & 0xFF) << 24) + ((this.bytes[(this.reader++)] & 0xFF) << 16) + ((this.bytes[(this.reader++)] & 0xFF) << 8) + (this.bytes[(this.reader++)] & 0xFF);
  }
  
  public int readInt16()
  {
    return ((this.bytes[(this.reader++)] & 0xFF) << 8) + (this.bytes[(this.reader++)] & 0xFF);
  }
  
  public int readInt8()
  {
    return this.bytes[(this.reader++)] & 0xFF;
  }
  
  public String readString()
  {
    int len = readInt16();
    byte[] text = new byte[len];
    System.arraycopy(this.bytes, this.reader, text, 0, len);
    this.reader += len;
    return new String(text);
  }
  
  public boolean canRead(int len)
  {
    return this.bytes.length - this.reader >= len;
  }
}


