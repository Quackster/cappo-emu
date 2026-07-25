package cappo.engine.tasks;

import cappo.engine.database.Database;
import cappo.engine.logging.Log;
import cappo.engine.player.Clients;
import cappo.game.collections.Utils;

public class OnlineCounterGrapth
  implements Runnable
{
  private long unixMinutes;
  
  public void run()
  {
    if (this.unixMinutes++ == 0L) {
      this.unixMinutes = (Utils.getTimestamp() / 60L);
    }
    try
    {
      int online = Clients.GetOnlineCount();
      Database.exec("INSERT INTO stats_online (time,data)VALUES(" + this.unixMinutes + "," + online + ");", new Object[0]);
    }
    catch (Exception ex)
    {
      Log.printException("ServerTasks", ex);
    }
  }
}


