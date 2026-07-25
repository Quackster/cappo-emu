package cappo.engine.network;

public class Crypto
{
  private int i;
  private int j;
  private final int[] Table = new int[256];
  private final int[] _Table = new int[258];
  
  public void init(byte[] key)
  {
    int keyLen = key.length;
    this.i = 0;
    while (this.i < 256) {
      this.Table[this.i] = (this.i++);
    }
    this.j = (this.i = 0);
    while (this.i < 256)
    {
      this.j = ((this.j + this.Table[this.i] + (key[(this.i % keyLen)] & 0xFF)) % 256);
      Swamp(this.i++, this.j);
    }
    this.j = (this.i = 0);
  }
  
  public void parse(byte[] b)
  {
    for (int a = 0; a < b.length; a++)
    {
      this.i = (++this.i % 256);
      this.j = ((this.j + this.Table[this.i]) % 256);
      Swamp(this.i, this.j);
      b[a] = ((byte)(b[a] & 0xFF ^ this.Table[((this.Table[this.i] + this.Table[this.j]) % 256)]));
    }
  }
  
  private void Swamp(int a, int b)
  {
    int k = this.Table[a];
    this.Table[a] = this.Table[b];
    this.Table[b] = k;
  }
  
  public void backup()
  {
    int _i = 0;
    while (_i < 256)
    {
      this._Table[_i] = this.Table[_i];
      _i++;
    }
    this._Table[256] = this.i;
    this._Table[257] = this.j;
  }
  
  public void restore()
  {
    int _i = 0;
    while (_i < 256)
    {
      this.Table[_i] = this._Table[_i];
      _i++;
    }
    this.i = this._Table[256];
    this.j = this._Table[257];
  }
}


