package cappo.engine.threadpools;

import cappo.engine.database.Database;
import cappo.engine.logging.Log;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class DatabaseExecTask
  extends GameTask
{
  public final Object[] values;
  public final String query;
  
  public static void addTask(GameTask task, int initDelay, int repeatRate)
  {
    WorkerTasks.addTask(task, initDelay, repeatRate, WorkerTasks.DatabaseExecTasks);
  }
  
  public DatabaseExecTask(String q, Object... v)
  {
    this.query = q;
    this.values = v;
  }
  
  public void run()
  {
    PreparedStatement pst = null;
    Connection cn = null;
    try
    {
      cn = Database.getNew();
      pst = cn.prepareStatement(this.query);
      Database.parseValues(pst, this.values);
      pst.execute();
    }
    catch (Exception ex)
    {
      Log.printException("QueryExec (" + this.query + ")", ex);
    }
    if (pst != null) {
      try
      {
        pst.close();
      }
      catch (Exception localException1) {}
    }
    if (cn != null) {
      try
      {
        cn.close();
      }
      catch (Exception localException2) {}
    }
  }
}


