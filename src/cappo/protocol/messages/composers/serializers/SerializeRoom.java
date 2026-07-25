package cappo.protocol.messages.composers.serializers;

import cappo.engine.network.MessageWriter;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.RoomIcon;
import cappo.game.roomengine.settings.TradingSettings;
import cappo.protocol.messages.Composer;

public class SerializeRoom
{
  public static void parse(MessageWriter ClientMessage, RoomData room)
  {
    Composer.add(Integer.valueOf(room.roomId), ClientMessage);
    Composer.add(room.name, ClientMessage);
    Composer.add(Boolean.valueOf(true), ClientMessage);
    Composer.add(Integer.valueOf(room.roomOwnerId), ClientMessage);
    Composer.add(room.roomOwnerName, ClientMessage);
    Composer.add(Integer.valueOf(room.state), ClientMessage);
    Composer.add(Integer.valueOf(room.room != null ? room.room.userCount : 0), ClientMessage);
    Composer.add(Integer.valueOf(room.usersMax), ClientMessage);
    Composer.add(room.description, ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(Integer.valueOf(room.tradingSettings.permissions), ClientMessage);
    Composer.add(Integer.valueOf(room.rating), ClientMessage);
    Composer.add(Integer.valueOf(room.ranking), ClientMessage);
    Composer.add(Integer.valueOf(room.category), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add("", ClientMessage);
    Composer.add("", ClientMessage);
    Composer.add("", ClientMessage);
    Composer.add(Integer.valueOf(room.tags.length), ClientMessage);
    for (String Tag : room.tags) {
      Composer.add(Tag, ClientMessage);
    }
    Composer.add(Integer.valueOf(room.icon.backgroundImage), ClientMessage);
    Composer.add(Integer.valueOf(room.icon.foregroundImage), ClientMessage);
    Composer.add(Integer.valueOf(room.icon.items.length), ClientMessage);
    for (String item : room.icon.items) {
      if ((!item.isEmpty()) && (!item.equals(",")))
      {
        String[] values = item.split(",");
        Composer.add(Integer.valueOf(Integer.parseInt(values[0])), ClientMessage);
        if ((values.length > 1) && (!values[1].isEmpty())) {
          Composer.add(Integer.valueOf(Integer.parseInt(values[1])), ClientMessage);
        } else {
          Composer.add(Integer.valueOf(0), ClientMessage);
        }
      }
      else
      {
        Composer.add(Integer.valueOf(0), ClientMessage);
        Composer.add(Integer.valueOf(0), ClientMessage);
      }
    }
    Composer.add(Boolean.valueOf(room.haveFlag(2)), ClientMessage);
    Composer.add(Boolean.valueOf(true), ClientMessage);
    Composer.add("Event name here..", ClientMessage);
    Composer.add("Event description here..", ClientMessage);
    Composer.add(Integer.valueOf(30), ClientMessage);
  }
}


