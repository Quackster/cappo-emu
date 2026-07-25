package cappo.protocol.messages.composers.friendlist;

import cappo.engine.network.MessageWriter;
import cappo.engine.player.Connection;
import cappo.game.player.AvatarLook;
import cappo.game.player.PlayerData;
import cappo.protocol.messages.Composer;
import java.util.List;

public class HabboSearchResultsComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(List<PlayerData> PlayersFriends, List<PlayerData> Players)
  {
    MessageWriter ClientMessage = new MessageWriter(1000 + (Players.size() + PlayersFriends.size()) * 150);
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(PlayersFriends.size()), ClientMessage);
    for (PlayerData client : PlayersFriends)
    {
      Composer.add(Integer.valueOf(client.userId), ClientMessage);
      Composer.add(client.userName, ClientMessage);
      Composer.add(client.motto, ClientMessage);
      if (client.connection != null)
      {
        Composer.add(Boolean.valueOf(true), ClientMessage);
        Composer.add(Boolean.valueOf(client.connection.avatar != null), ClientMessage);
      }
      else
      {
        Composer.add(Boolean.valueOf(false), ClientMessage);
        Composer.add(Boolean.valueOf(false), ClientMessage);
      }
      Composer.add("", ClientMessage);
      Composer.add(Integer.valueOf(0), ClientMessage);
      Composer.add(client.avatarLook.toString(), ClientMessage);
      Composer.add(client.getRealName(), ClientMessage);
    }
    Composer.add(Integer.valueOf(Players.size()), ClientMessage);
    for (PlayerData client : Players)
    {
      Composer.add(Integer.valueOf(client.userId), ClientMessage);
      Composer.add(client.userName, ClientMessage);
      Composer.add(client.motto, ClientMessage);
      if (client.connection != null)
      {
        Composer.add(Boolean.valueOf(true), ClientMessage);
        Composer.add(Boolean.valueOf(client.connection.avatar != null), ClientMessage);
      }
      else
      {
        Composer.add(Boolean.valueOf(false), ClientMessage);
        Composer.add(Boolean.valueOf(false), ClientMessage);
      }
      Composer.add("", ClientMessage);
      Composer.add(Integer.valueOf(0), ClientMessage);
      Composer.add(client.avatarLook.toString(), ClientMessage);
      Composer.add(client.getRealName(), ClientMessage);
    }
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


