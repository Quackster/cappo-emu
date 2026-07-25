package cappo.protocol.messages.events.games.gamecenter;

import cappo.engine.Server;
import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.collections.Utils;
import cappo.game.player.PlayerData;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.games.gamecenter.JoinedPlayerQueueComposer;
import cappo.protocol.messages.composers.games.gamecenter.LoadGameComposer;

public class JoinPlayerQueueParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    if (Server.blockFF) {
      return;
    }
    int GameId = Main.currentPacket.readInt();
    QueueWriter.write(Main.socket, JoinedPlayerQueueComposer.compose(GameId));
    
    String sendData = Server.mysqlDB + "-" + Integer.toString(Main.playerData.userId);
    String privKey = "$g%h&j@k";
    String pubKey = Integer.toHexString(Utils.GetRandomNumber(50000, 2147483647));
    int p = 0;
    int len = "$g%h&j@k".length();
    int len2 = pubKey.length();
    String tokenizer = "";
    for (int i = 0; i < len; i++)
    {
      tokenizer = tokenizer + (char)("$g%h&j@k".charAt(i) & 0xFF ^ pubKey.charAt(p) & 0xFF);
      p++;
      if (p == len2) {
        p = 0;
      }
    }
    len = sendData.length();
    int len3 = tokenizer.length();
    byte[] buf = new byte[len2 + 1 + len];
    p = 0;
    for (int i = 0; i <= len2; i++) {
      if (i < len2) {
        buf[i] = ((byte)pubKey.charAt(i));
      } else {
        buf[i] = 45;
      }
    }
    len2++;
    for (int i = 0; i < len; i++)
    {
      buf[(len2 + i)] = ((byte)(sendData.charAt(i) & 0xFF ^ tokenizer.charAt(p) & 0xFF));
      p++;
      if (p == len3) {
        p = 0;
      }
    }
    QueueWriter.write(Main.socket, LoadGameComposer.compose(GameId, new String(buf)));
  }
}


