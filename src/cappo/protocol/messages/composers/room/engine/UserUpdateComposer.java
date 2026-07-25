package cappo.protocol.messages.composers.room.engine;

import cappo.engine.network.MessageWriter;
import cappo.game.games.snowwar.Direction8;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.entity.live.LiveEntity;
import cappo.game.roomengine.entity.live.PetEntity;
import cappo.protocol.messages.Composer;
import java.util.Collection;

public class UserUpdateComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(Collection<Avatar> UserList, Collection<PetEntity> BotList)
  {
    int i = 0;
    
    MessageWriter ClientMessage = new MessageWriter(100 + (UserList.size() + BotList.size()) * 100);
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(ClientMessage.setSaved(Integer.valueOf(0)), ClientMessage);
    for (Avatar User : UserList)
    {
      Composer.add(Short.valueOf(User.virtualId), ClientMessage);
      Composer.add(Integer.valueOf(User.x), ClientMessage);
      Composer.add(Integer.valueOf(User.y), ClientMessage);
      Composer.add(Float.toString(User.z).replace(',', '.'), ClientMessage);
      Composer.add(Integer.valueOf(User.RotHead.getRot()), ClientMessage);
      Composer.add(Integer.valueOf(User.RotBody.getRot()), ClientMessage);
      Composer.add(User.Status, ClientMessage);
      i++;
    }
    for (PetEntity User : BotList)
    {
      Composer.add(Short.valueOf(User.virtualId), ClientMessage);
      Composer.add(Integer.valueOf(User.x), ClientMessage);
      Composer.add(Integer.valueOf(User.y), ClientMessage);
      Composer.add(Float.toString(User.z).replace(',', '.'), ClientMessage);
      Composer.add(Integer.valueOf(User.RotHead.getRot()), ClientMessage);
      Composer.add(Integer.valueOf(User.RotBody.getRot()), ClientMessage);
      Composer.add(User.Status, ClientMessage);
      i++;
    }
    ClientMessage.writeSaved(Integer.valueOf(i));
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
  
  public static final MessageWriter compose(Collection<LiveEntity> UserList)
  {
    MessageWriter ClientMessage = new MessageWriter(100 + UserList.size() * 100);
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(UserList.size()), ClientMessage);
    for (LiveEntity User : UserList)
    {
      Composer.add(Short.valueOf(User.virtualId), ClientMessage);
      Composer.add(Integer.valueOf(User.x), ClientMessage);
      Composer.add(Integer.valueOf(User.y), ClientMessage);
      Composer.add(Float.toString(User.z).replace(',', '.'), ClientMessage);
      Composer.add(Integer.valueOf(User.RotHead.getRot()), ClientMessage);
      Composer.add(Integer.valueOf(User.RotBody.getRot()), ClientMessage);
      Composer.add(User.Status, ClientMessage);
    }
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


