package cappo.protocol.messages.composers.catalog;

import cappo.engine.network.MessageWriter;
import cappo.game.catalog.giftwrapping.GiftWrappingConfiguration;
import cappo.protocol.messages.Composer;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class GiftWrappingConfigurationComposer
{
  private static MessageWriter ClientMessage;
  public static int HEADER;
  
  public static final MessageWriter compose()
  {
    if ((ClientMessage == null) || (GiftWrappingConfiguration.needUpdate))
    {
      GiftWrappingConfiguration.needUpdate = false;
      
      ClientMessage = new MessageWriter();
      Composer.initPacket(HEADER, ClientMessage);
      Composer.add(Boolean.valueOf(true), ClientMessage);
      Composer.add(Integer.valueOf(1), ClientMessage);
      

      Composer.add(Integer.valueOf(GiftWrappingConfiguration.baseGiftItems.size()), ClientMessage);
      for (Iterator localIterator = GiftWrappingConfiguration.baseGiftItems.keySet().iterator(); localIterator.hasNext();)
      {
        int colorSprite = ((Integer)localIterator.next()).intValue();
        Composer.add(Integer.valueOf(colorSprite), ClientMessage);
      }
      Composer.add(Integer.valueOf(7), ClientMessage);
      for (int i = 0; i < 7; i++) {
        Composer.add(Integer.valueOf(i), ClientMessage);
      }
      Composer.add(Integer.valueOf(11), ClientMessage);
      for (int i = 0; i < 11; i++) {
        Composer.add(Integer.valueOf(i), ClientMessage);
      }
      Composer.add(Integer.valueOf(GiftWrappingConfiguration.baseGiftFreeItems.size()), ClientMessage);
      for (Iterator localIterator2 = GiftWrappingConfiguration.baseGiftFreeItems.keySet().iterator(); localIterator2.hasNext();)
      {
        int colorSprite = ((Integer)localIterator2.next()).intValue();
        Composer.add(Integer.valueOf(colorSprite), ClientMessage);
      }
      Composer.endPacket(ClientMessage);
    }
    return ClientMessage;
  }
}


