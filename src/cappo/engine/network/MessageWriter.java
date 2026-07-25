package cappo.engine.network;

import java.util.ArrayList;
import java.util.List;

public class MessageWriter
{
  public List<Integer> savedPositions = new ArrayList();
  public boolean isReady;
  private boolean bytesReady;
  public int writer;
  private byte[] bytes;
  public int debugId;
  
  public MessageWriter(int Size)
  {
    this.bytes = new byte[Size];
  }
  
  public MessageWriter()
  {
    this(1000);
  }
  
  public byte[] getMessage()
    throws Exception
  {
    if (!this.isReady) {
      throw new Exception("Not ended MessageWriter!");
    }
    if (this.bytesReady) {
      return this.bytes;
    }
    byte[] rtn = new byte[this.writer];
    for (int i = 0; i < this.writer; i++) {
      rtn[i] = this.bytes[i];
    }
    this.bytes = rtn;
    this.bytesReady = true;
    
    return rtn;
  }
  
  public void writetInt32(int in)
  {
    this.bytes[(this.writer++)] = ((byte)(in >>> 24 & 0xFF));
    this.bytes[(this.writer++)] = ((byte)(in >>> 16 & 0xFF));
    this.bytes[(this.writer++)] = ((byte)(in >>> 8 & 0xFF));
    this.bytes[(this.writer++)] = ((byte)(in >>> 0 & 0xFF));
  }
  
  public void writeInt16(int in)
  {
    this.bytes[(this.writer++)] = ((byte)(in >>> 8 & 0xFF));
    this.bytes[(this.writer++)] = ((byte)(in >>> 0 & 0xFF));
  }
  
  public void writeBoolean(boolean in)
  {
    this.bytes[(this.writer++)] = ((byte)(in ? 1 : 0));
  }
  
  public void writeChar(char in)
  {
    this.bytes[(this.writer++)] = ((byte)(in & 0xFF));
  }
  
  public void writeByte(byte in)
  {
    this.bytes[(this.writer++)] = in;
  }
  
  public void writeString(String in)
  {
    int len = in.length();
    writeInt16(len);
    for (int i = 0; i < len; i++) {
      this.bytes[(this.writer++)] = ((byte)(in.charAt(i) & 0xFF));
    }
  }
  
  public void writeBytes(byte[] in)
  {
    int len = in.length;
    writeInt16(len);
    for (int i = 0; i < len; i++) {
      this.bytes[(this.writer++)] = in[i];
    }
  }
  
  public Object setSaved(Object add)
  {
    this.savedPositions.add(Integer.valueOf(this.writer));
    return add;
  }
  
  public void writeSaved(Object add)
  {
    if ((add instanceof Integer))
    {
      int tmp = this.writer;
      this.writer = ((Integer)this.savedPositions.remove(this.savedPositions.size() - 1)).intValue();
      writetInt32(((Integer)add).intValue());
      this.writer = tmp;
    }
    else if ((add instanceof Boolean))
    {
      int tmp = this.writer;
      this.writer = ((Integer)this.savedPositions.remove(this.savedPositions.size() - 1)).intValue();
      writeBoolean(((Boolean)add).booleanValue());
      this.writer = tmp;
    }
    else
    {
      throw new UnsupportedOperationException("Bad Param in SetWriter " + add.getClass());
    }
  }
}


