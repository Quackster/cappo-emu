package cappo.protocol.messages.composers.catalog;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;
import java.util.Calendar;

public class HabboClubExtendOfferComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose()
  {
    Calendar calendar = Calendar.getInstance();
    
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(4), ClientMessage);
    
    calendar.setTimeInMillis(System.currentTimeMillis());
    calendar.add(5, 31);
    Composer.add(Integer.valueOf(4896), ClientMessage);
    Composer.add("HABBO_CLUB_BASIC_1_MONTH", ClientMessage);
    Composer.add(Integer.valueOf(15), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(Boolean.valueOf(false), ClientMessage);
    Composer.add(Integer.valueOf(3), ClientMessage);
    Composer.add(Integer.valueOf(93), ClientMessage);
    Composer.add(Integer.valueOf(93), ClientMessage);
    Composer.add(Integer.valueOf(calendar.get(1)), ClientMessage);
    Composer.add(Integer.valueOf(calendar.get(2) + 1), ClientMessage);
    Composer.add(Integer.valueOf(calendar.get(5)), ClientMessage);
    
    calendar.setTimeInMillis(System.currentTimeMillis());
    calendar.add(5, 93);
    Composer.add(Integer.valueOf(4897), ClientMessage);
    Composer.add("HABBO_CLUB_BASIC_3_MONTHS", ClientMessage);
    Composer.add(Integer.valueOf(25), ClientMessage);
    Composer.add(Boolean.valueOf(false), ClientMessage);
    Composer.add(Integer.valueOf(3), ClientMessage);
    Composer.add(Integer.valueOf(93), ClientMessage);
    Composer.add(Integer.valueOf(93), ClientMessage);
    Composer.add(Integer.valueOf(calendar.get(1)), ClientMessage);
    Composer.add(Integer.valueOf(calendar.get(2) + 1), ClientMessage);
    Composer.add(Integer.valueOf(calendar.get(5)), ClientMessage);
    
    calendar.setTimeInMillis(System.currentTimeMillis());
    calendar.add(5, 31);
    Composer.add(Integer.valueOf(4898), ClientMessage);
    Composer.add("HABBO_CLUB_VIP_1_MONTH", ClientMessage);
    Composer.add(Integer.valueOf(25), ClientMessage);
    Composer.add(Boolean.valueOf(true), ClientMessage);
    Composer.add(Integer.valueOf(1), ClientMessage);
    Composer.add(Integer.valueOf(31), ClientMessage);
    Composer.add(Integer.valueOf(31), ClientMessage);
    Composer.add(Integer.valueOf(calendar.get(1)), ClientMessage);
    Composer.add(Integer.valueOf(calendar.get(2) + 1), ClientMessage);
    Composer.add(Integer.valueOf(calendar.get(5)), ClientMessage);
    
    calendar.setTimeInMillis(System.currentTimeMillis());
    calendar.add(5, 93);
    Composer.add(Integer.valueOf(4899), ClientMessage);
    Composer.add("HABBO_CLUB_VIP_3_MONTHS", ClientMessage);
    Composer.add(Integer.valueOf(60), ClientMessage);
    Composer.add(Boolean.valueOf(true), ClientMessage);
    Composer.add(Integer.valueOf(3), ClientMessage);
    Composer.add(Integer.valueOf(93), ClientMessage);
    Composer.add(Integer.valueOf(93), ClientMessage);
    Composer.add(Integer.valueOf(calendar.get(1)), ClientMessage);
    Composer.add(Integer.valueOf(calendar.get(2) + 1), ClientMessage);
    Composer.add(Integer.valueOf(calendar.get(5)), ClientMessage);
    
    Composer.add(Integer.valueOf(1), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


