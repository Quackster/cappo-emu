package cappo.protocol.messages.composers.games.gamecenter;

import cappo.engine.Server;
import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class LoadGameComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int GameId, String token)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(GameId), ClientMessage);
    Composer.add(Long.toString(System.currentTimeMillis()), ClientMessage);
    Composer.add("http://dcr.lavvos.pl/FastFood.swf", ClientMessage);
    Composer.add("best", ClientMessage);
    Composer.add("showAll", ClientMessage);
    Composer.add(Integer.valueOf(60), ClientMessage);
    Composer.add(Integer.valueOf(10), ClientMessage);
    Composer.add(Integer.valueOf(0), ClientMessage);
    Composer.add(Integer.valueOf(5), ClientMessage);
    

    Composer.add("accessToken", ClientMessage);
    Composer.add(token, ClientMessage);
    

    Composer.add("gameServerHost", ClientMessage);
    Composer.add(Server.fastfoodIP, ClientMessage);
    

    Composer.add("gameServerPort", ClientMessage);
    Composer.add(Server.fastfoodPORT, ClientMessage);
    

    Composer.add("socketPolicyPort", ClientMessage);
    Composer.add("30843", ClientMessage);
    

    Composer.add("assetUrl", ClientMessage);
    Composer.add("http://dcr.lavvos.pl/BasicAssets.swf", ClientMessage);
    


    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


