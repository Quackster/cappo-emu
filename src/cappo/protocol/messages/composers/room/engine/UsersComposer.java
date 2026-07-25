package cappo.protocol.messages.composers.room.engine;

import cappo.engine.network.MessageWriter;
import cappo.engine.player.Connection;
import cappo.game.bots.RentalBot;
import cappo.game.games.snowwar.Direction8;
import cappo.game.pets.Pet;
import cappo.game.pets.PetBase;
import cappo.game.player.AvatarLook;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.entity.live.PetEntity;
import cappo.game.roomengine.entity.live.RentalBotEntity;
import cappo.protocol.messages.Composer;
import java.util.Collection;

public class UsersComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(Collection<Avatar> UserList, Collection<PetEntity> petList, Collection<RentalBotEntity> rentalBotList)
  {
    int i = 0;
    
    MessageWriter ClientMessage = new MessageWriter(100 + (UserList.size() + petList.size() + rentalBotList.size()) * 400);
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(ClientMessage.setSaved(Integer.valueOf(0)), ClientMessage);
    for (Avatar User : UserList)
    {
      PlayerData playerData = User.cn.getPlayerData();
      Composer.add(Integer.valueOf(User.id), ClientMessage);
      Composer.add(playerData.userName, ClientMessage);
      Composer.add(playerData.motto, ClientMessage);
      Composer.add(playerData.avatarLook.toString(), ClientMessage);
      Composer.add(Short.valueOf(User.virtualId), ClientMessage);
      Composer.add(Integer.valueOf(User.x), ClientMessage);
      Composer.add(Integer.valueOf(User.y), ClientMessage);
      Composer.add(Float.toString(User.z).replace(',', '.'), ClientMessage);
      Composer.add(Integer.valueOf(User.RotBody.getRot()), ClientMessage);
      Composer.add(Integer.valueOf(User.entityType), ClientMessage);
      Composer.add(playerData.sex == 1 ? "M" : "F", ClientMessage);
      Composer.add(Integer.valueOf(-1), ClientMessage);
      Composer.add(Integer.valueOf(-1), ClientMessage);
      Composer.add("GroupName", ClientMessage);
      Composer.add("", ClientMessage);
      Composer.add(Integer.valueOf(playerData.AchievementsScore), ClientMessage);
      i++;
    }
    for (PetEntity User : petList)
    {
      Composer.add(Integer.valueOf(User.petData.id), ClientMessage);
      Composer.add(User.petData.name, ClientMessage);
      Composer.add(User.motto, ClientMessage);
      Composer.add(User.look, ClientMessage);
      Composer.add(Short.valueOf(User.virtualId), ClientMessage);
      Composer.add(Integer.valueOf(User.x), ClientMessage);
      Composer.add(Integer.valueOf(User.y), ClientMessage);
      Composer.add(Float.toString(User.z).replace(',', '.'), ClientMessage);
      Composer.add(Integer.valueOf(User.RotBody.getRot()), ClientMessage);
      Composer.add(Integer.valueOf(User.entityType), ClientMessage);
      Composer.add(Short.valueOf(User.petData.base.raceId), ClientMessage);
      Composer.add(Integer.valueOf(User.petData.ownerId), ClientMessage);
      Composer.add(User.petData.ownerName, ClientMessage);
      Composer.add(Integer.valueOf(0), ClientMessage);
      Composer.add(Boolean.valueOf(User.petData.haveSaddle), ClientMessage);
      Composer.add(Boolean.valueOf(User.ridingEntity != null), ClientMessage);
      Composer.add(Boolean.valueOf(false), ClientMessage);
      Composer.add(Boolean.valueOf(false), ClientMessage);
      Composer.add(Boolean.valueOf(false), ClientMessage);
      Composer.add(Boolean.valueOf(false), ClientMessage);
      Composer.add(Integer.valueOf(0), ClientMessage);
      Composer.add("", ClientMessage);
      i++;
    }
    for (RentalBotEntity bot : rentalBotList)
    {
      Composer.add(Integer.valueOf(bot.botData.id), ClientMessage);
      Composer.add(bot.botData.name, ClientMessage);
      Composer.add(bot.botData.motto, ClientMessage);
      Composer.add(bot.botData.botLook.toString(), ClientMessage);
      Composer.add(Short.valueOf(bot.virtualId), ClientMessage);
      Composer.add(Integer.valueOf(bot.x), ClientMessage);
      Composer.add(Integer.valueOf(bot.y), ClientMessage);
      Composer.add(Float.toString(bot.z).replace(',', '.'), ClientMessage);
      Composer.add(Integer.valueOf(bot.RotBody.getRot()), ClientMessage);
      Composer.add(Integer.valueOf(bot.entityType), ClientMessage);
      Composer.add(bot.botData.gender, ClientMessage);
      Composer.add(Integer.valueOf(bot.botData.ownerId), ClientMessage);
      Composer.add(bot.botData.ownerName, ClientMessage);
      
      Composer.add(Integer.valueOf(7), ClientMessage);
      
      Composer.writeInt16(0, ClientMessage);
      Composer.writeInt16(1, ClientMessage);
      Composer.writeInt16(2, ClientMessage);
      Composer.writeInt16(3, ClientMessage);
      Composer.writeInt16(4, ClientMessage);
      Composer.writeInt16(5, ClientMessage);
      Composer.writeInt16(6, ClientMessage);
      
      i++;
    }
    ClientMessage.writeSaved(Integer.valueOf(i));
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
  
  public static final MessageWriter compose(Avatar User)
  {
    PlayerData playerData = User.cn.getPlayerData();
    
    MessageWriter ClientMessage = new MessageWriter(400);
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(1), ClientMessage);
    Composer.add(Integer.valueOf(User.id), ClientMessage);
    Composer.add(playerData.userName, ClientMessage);
    Composer.add(playerData.motto, ClientMessage);
    Composer.add(playerData.avatarLook.toString(), ClientMessage);
    Composer.add(Short.valueOf(User.virtualId), ClientMessage);
    Composer.add(Integer.valueOf(User.x), ClientMessage);
    Composer.add(Integer.valueOf(User.y), ClientMessage);
    Composer.add(Float.toString(User.z).replace(',', '.'), ClientMessage);
    Composer.add(Integer.valueOf(User.RotBody.getRot()), ClientMessage);
    Composer.add(Integer.valueOf(User.entityType), ClientMessage);
    Composer.add(playerData.sex == 1 ? "M" : "F", ClientMessage);
    Composer.add(Integer.valueOf(-1), ClientMessage);
    Composer.add(Integer.valueOf(-1), ClientMessage);
    Composer.add("GroupName", ClientMessage);
    Composer.add("", ClientMessage);
    Composer.add(Integer.valueOf(playerData.AchievementsScore), ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }

  public static final MessageWriter compose(PetEntity User)
  {
    MessageWriter ClientMessage = new MessageWriter(400);
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(1), ClientMessage);
    Composer.add(Integer.valueOf(User.petData.id), ClientMessage);
    Composer.add(User.petData.name, ClientMessage);
    Composer.add(User.motto, ClientMessage);
    Composer.add(User.look, ClientMessage);
    Composer.add(Short.valueOf(User.virtualId), ClientMessage);
    Composer.add(Integer.valueOf(User.x), ClientMessage);
    Composer.add(Integer.valueOf(User.y), ClientMessage);
    Composer.add(Float.toString(User.z).replace(',', '.'), ClientMessage);
    Composer.add(Integer.valueOf(User.RotBody.getRot()), ClientMessage);
    Composer.add(Integer.valueOf(User.entityType), ClientMessage);
    Composer.add(Short.valueOf(User.petData.base.raceId), ClientMessage);
    Composer.add(Integer.valueOf(User.petData.ownerId), ClientMessage);
    Composer.add(User.petData.ownerName, ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(Boolean.valueOf(User.petData.haveSaddle), ClientMessage);
    Composer.add(Boolean.valueOf(User.ridingEntity != null), ClientMessage);
    Composer.add(Boolean.valueOf(false), ClientMessage);
    Composer.add(Boolean.valueOf(false), ClientMessage);
    Composer.add(Boolean.valueOf(false), ClientMessage);
    Composer.add(Boolean.valueOf(false), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add("", ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
  
  public static final MessageWriter compose(RentalBotEntity bot)
  {
    MessageWriter ClientMessage = new MessageWriter(400);
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(1), ClientMessage);
    
    Composer.add(Integer.valueOf(bot.botData.id), ClientMessage);
    Composer.add(bot.botData.name, ClientMessage);
    Composer.add(bot.botData.motto, ClientMessage);
    Composer.add(bot.botData.botLook.toString(), ClientMessage);
    Composer.add(Short.valueOf(bot.virtualId), ClientMessage);
    Composer.add(Integer.valueOf(bot.x), ClientMessage);
    Composer.add(Integer.valueOf(bot.y), ClientMessage);
    Composer.add(Float.toString(bot.z).replace(',', '.'), ClientMessage);
    Composer.add(Integer.valueOf(bot.RotBody.getRot()), ClientMessage);
    Composer.add(Integer.valueOf(bot.entityType), ClientMessage);
    Composer.add(bot.botData.gender, ClientMessage);
    Composer.add(Integer.valueOf(bot.botData.ownerId), ClientMessage);
    Composer.add(bot.botData.ownerName, ClientMessage);
    
    Composer.add(Integer.valueOf(7), ClientMessage);
    
    Composer.writeInt16(0, ClientMessage);
    Composer.writeInt16(1, ClientMessage);
    Composer.writeInt16(2, ClientMessage);
    Composer.writeInt16(3, ClientMessage);
    Composer.writeInt16(4, ClientMessage);
    Composer.writeInt16(5, ClientMessage);
    Composer.writeInt16(6, ClientMessage);
    

    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


