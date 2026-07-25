package cappo.protocol.messages.events.avatar;

import cappo.engine.database.DBResult;
import cappo.engine.database.Database;
import cappo.engine.logging.Log;
import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.collections.Utils;
import cappo.game.player.PlayerData;
import cappo.game.player.data.AvatarData;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.avatar.ResultCheckUserNameComposer;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CheckUserNameParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    if (!Main.haveFlag(4)) {
      return;
    }
    long now = Utils.getTimestamp();
    if (Main.avatarData.lastCheckNameTry >= now)
    {
      QueueWriter.write(Main.socket, ResultCheckUserNameComposer.compose(6, "", new ArrayList()));
      return;
    }
    Main.avatarData.lastCheckNameTry = (now + 1L);
    
    String name = Main.currentPacket.readString();
    if (name.length() < 5)
    {
      QueueWriter.write(Main.socket, ResultCheckUserNameComposer.compose(2, name, new ArrayList()));
      return;
    }
    if (name.length() > 15)
    {
      QueueWriter.write(Main.socket, ResultCheckUserNameComposer.compose(3, name, new ArrayList()));
      return;
    }
    if ((name == Main.playerData.userName) || (name.toLowerCase().startsWith("mod-")))
    {
      QueueWriter.write(Main.socket, ResultCheckUserNameComposer.compose(4, name, new ArrayList()));
      return;
    }
    DBResult result = new DBResult();
    try
    {
      Database.query(result, "SELECT null FROM users WHERE username = ? LIMIT 1;", new Object[] { name });
      if (result.data.next())
      {
        QueueWriter.write(Main.socket, ResultCheckUserNameComposer.compose(5, name, new ArrayList()));
        result.close();
        return;
      }
    }
    catch (Exception ex)
    {
      Log.printException("CheckUserNameParser-1", ex);
      
      result.close();
      
      QueueWriter.write(Main.socket, ResultCheckUserNameComposer.compose(0, name, new ArrayList()));
    }
  }
}


