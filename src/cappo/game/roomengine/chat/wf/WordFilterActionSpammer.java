package cappo.game.roomengine.chat.wf;

import cappo.engine.Server;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.collections.Utils;
import cappo.game.player.PlayerData;
import cappo.protocol.messages.composers.games.gamecenter.JoinedPlayerQueueComposer;
import cappo.protocol.messages.composers.games.gamecenter.LoadGameComposer;

public class WordFilterActionSpammer
  extends WordFilterAction
{
  public boolean run(Connection cn)
  {
    Utils.AlertFromHotel(cn.socket, cappo.game.utils.lang.LangTexts.texts[8]);
    if (Server.blockFF) {
      return true;
    }
    int GameId = 3;
    QueueWriter.writeAndFlush(cn.socket, JoinedPlayerQueueComposer.compose(3));
    
    String sendData = Server.mysqlDB + "-" + Integer.toString(cn.getPlayerData().userId);
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
    QueueWriter.writeAndFlush(cn.socket, LoadGameComposer.compose(3, new String(buf)));
    
    return true;
  }
}


