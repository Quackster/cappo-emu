package cappo.game.roomengine.entity.item.extradata;

import java.util.ArrayList;
import java.util.List;

public class StuffDataWriter
{
  public List<Integer> savedPositions = new ArrayList();
  private byte[] bytes;
  public int writer;
  
  public StuffDataWriter(int type, int Size)
  {
    this.bytes = new byte[Size];
    writeInt8(type);
  }
  
  public StuffDataWriter(int type)
  {
    this(type, 1000);
  }
  
  public byte[] getData()
  {
    if (this.writer == this.bytes.length) {
      return this.bytes;
    }
    byte[] rtn = new byte[this.writer];
    for (int i = 0; i < this.writer; i++) {
      rtn[i] = this.bytes[i];
    }
    this.bytes = rtn;
    
    return this.bytes;
  }
  
  public void writeInt32(int in)
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
  
  public void writeInt8(int in)
  {
    this.bytes[(this.writer++)] = ((byte)(in >>> 0 & 0xFF));
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
      writeInt32(((Integer)add).intValue());
      this.writer = tmp;
    }
    else
    {
      throw new UnsupportedOperationException("Bad Param in SetWriter " + add.getClass());
    }
  }
  
  public void writeSavedInt8(int add)
  {
    int tmp = this.writer;
    this.writer = ((Integer)this.savedPositions.remove(this.savedPositions.size() - 1)).intValue();
    writeInt8(add);
/* :0:94 */     this.writer = tmp;
/* :1:   */   }
/* :2:   */ }


