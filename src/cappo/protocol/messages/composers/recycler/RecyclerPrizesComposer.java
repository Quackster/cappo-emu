package cappo.protocol.messages.composers.recycler;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class RecyclerPrizesComposer
{
  public static int HEADER;
  private static MessageWriter ClientMessage;
  
  public static final MessageWriter compose()
  {
    if (ClientMessage == null)
    {
      ClientMessage = new MessageWriter();
      
      int[][] Category = { { 13, 13, 13, 13, 13 }, { 13, 13, 13, 13 }, { 13, 13, 13 }, { 13, 13 }, { 13 } };
      Composer.initPacket(HEADER, ClientMessage);
      Composer.add(Integer.valueOf(1), ClientMessage);
      
      Composer.add(Integer.valueOf(0), ClientMessage);
      Composer.add(Integer.valueOf(0), ClientMessage);
      Composer.add(Integer.valueOf(Category.length), ClientMessage);
      int[][] arrayOfInt1 = Category;int j = Category.length;
      for (int i = 0; i < j; i++)
      {
        int[] element = arrayOfInt1[i];
        

        Composer.add("s", ClientMessage);
        Composer.add(Integer.valueOf(element.length), ClientMessage);
        for (int o = 0; o < element.length; o++)
        {
          Composer.add("s", ClientMessage);
          Composer.add(Integer.valueOf(element[o]), ClientMessage);
        }
      }
      Composer.endPacket(ClientMessage);
    }
    return ClientMessage;
  }
}


