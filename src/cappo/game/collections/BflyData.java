package cappo.game.collections;

public class BflyData
{
  public static int getB(float k)
  {
    return Math.round(k % 1.0F * 100.0F);
  }
  
  public static int getA(float k, int b)
  {
    return (Math.round(k * 100.0F) - b) / 100;
  }
  
  public static float Combine(int a, int b)
  {
    return a + b / 100.0F;
  }
  
  public static int Parse(String a)
  {
    int w = 0;int i = 0;
    int length = a.length();
    if (length == 0) {
      return 0;
    }
    do
    {
      int k = a.charAt(i++);
      if ((k < 48) || (k > 59)) {
        return 0;
      }
      w = 10 * w + k - 48;
    } while (
    






      i < length);
    return w;
  }
}


