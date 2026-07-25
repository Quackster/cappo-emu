package cappo.engine.network;

public class MessageReader
{
  public int headerId;
  public byte[] bytes;
  public int reader;
  
  public MessageReader(byte[] arr)
  {
    this.bytes = arr;
  }
  
  public MessageReader setHeaderId()
  {
    this.headerId = readShort();
    return this;
  }
  
  public int readInt()
  {
    return ((this.bytes[(this.reader++)] & 0xFF) << 24) + ((this.bytes[(this.reader++)] & 0xFF) << 16) + ((this.bytes[(this.reader++)] & 0xFF) << 8) + (this.bytes[(this.reader++)] & 0xFF);
  }
  
  public int readShort()
  {
    return ((this.bytes[(this.reader++)] & 0xFF) << 8) + (this.bytes[(this.reader++)] & 0xFF);
  }
  
  public boolean readBoolean()
  {
    return (this.bytes[(this.reader++)] & 0xFF) == 1;
  }
  
  public String readString()
  {
    int len = readShort();
    String result = new String(this.bytes, this.reader, len);
    this.reader += len;
    return result;
  }
}


