package cappo.protocol.messages.events.friendlist;

import cappo.engine.database.DBResult;
import cappo.engine.logging.Log;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.player.PlayerData;
import cappo.game.player.messenger.PlayerMessenger;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.friendlist.MessengerInitComposer;

public class MessengerInitParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection cn)
  {
    if (cn.playerData == null) {
      return;
    }
    DBResult result = new DBResult();
    try
    {
      cn.playerData.messenger.initMessenger(result);
      QueueWriter.write(cn.socket, MessengerInitComposer.compose(cn.playerData.messenger));
    }
    catch (Exception ex)
    {
      Log.printException("MessengerInitParser-1", ex);
    }
    result.close();
  }
}


