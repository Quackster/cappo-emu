package cappo.engine.tasks;

import cappo.engine.database.Database;
import cappo.engine.logging.Log;
import cappo.engine.player.Clients;

public class OnlineCounter
  implements Runnable
{
  public void run()
  {
    try
    {
      Database.exec("UPDATE `server_status` SET `users_online`='" + Clients.GetOnlineCount() + "';", new Object[0]);
    }
    catch (Exception ex)
    {
      Log.printException("ServerTasks", ex);
    }
  }
}


