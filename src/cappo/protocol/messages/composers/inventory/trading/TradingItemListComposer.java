package cappo.protocol.messages.composers.inventory.trading;

import cappo.engine.network.MessageWriter;
import cappo.game.collections.BaseItem;
import cappo.game.inventory.trading.TradeUser;
import cappo.game.roomengine.entity.item.Item;
import cappo.protocol.messages.Composer;
import cappo.protocol.messages.composers.serializers.SerializeItemData;
import java.util.Map;

public class TradingItemListComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(TradeUser owner, TradeUser guest)
  {
    MessageWriter ClientMessage = new MessageWriter(500 + owner.furnis.size() * 200 + guest.furnis.size() * 200);
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(owner.userId), ClientMessage);
    Composer.add(Integer.valueOf(owner.furnis.size()), ClientMessage);
    for (Item furni : owner.furnis.values())
    {
      Composer.add(Integer.valueOf(furni.itemId), ClientMessage);
      Composer.add(furni.baseItem.Type, ClientMessage);
      Composer.add(Integer.valueOf(furni.refId), ClientMessage);
      Composer.add(Integer.valueOf(furni.baseItem.SpriteId), ClientMessage);
      Composer.add(Integer.valueOf(furni.baseItem.itemCategory), ClientMessage);
      Composer.add(Boolean.valueOf(true), ClientMessage);
      
      SerializeItemData.parse(ClientMessage, furni.baseItem, furni);
      

      Composer.add(Integer.valueOf(0), ClientMessage);
      Composer.add(Integer.valueOf(0), ClientMessage);
      Composer.add(Integer.valueOf(0), ClientMessage);
      if (furni.baseItem.Type.equals("s")) {
        Composer.add(Integer.valueOf(0), ClientMessage);
      }
    }
    Composer.add(Integer.valueOf(guest.userId), ClientMessage);
    Composer.add(Integer.valueOf(guest.furnis.size()), ClientMessage);
    for (Item furni : guest.furnis.values())
    {
      Composer.add(Integer.valueOf(furni.itemId), ClientMessage);
      Composer.add(furni.baseItem.Type, ClientMessage);
      Composer.add(Integer.valueOf(furni.refId), ClientMessage);
      Composer.add(Integer.valueOf(furni.baseItem.SpriteId), ClientMessage);
      Composer.add(Integer.valueOf(furni.baseItem.itemCategory), ClientMessage);
      Composer.add(Boolean.valueOf(true), ClientMessage);
      
      SerializeItemData.parse(ClientMessage, furni.baseItem, furni);
      

      Composer.add(Integer.valueOf(0), ClientMessage);
      Composer.add(Integer.valueOf(0), ClientMessage);
      Composer.add(Integer.valueOf(0), ClientMessage);
      if (furni.baseItem.Type.equals("s")) {
        Composer.add(Integer.valueOf(0), ClientMessage);
      }
    }
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


