package cappo.engine.threadpools;

import cappo.engine.database.Database;
import cappo.engine.logging.Log;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DatabaseQueryTask
  extends GameTask
{
  private List<Query> queries;
  
  public static void addTask(DatabaseQueryTask task, int initDelay, int repeatRate)
  {
    WorkerTasks.addTask(task, initDelay, repeatRate, WorkerTasks.DatabaseQueryTasks);
  }
  
  public DatabaseQueryTask(int initialQuerySize)
  {
    this.queries = new ArrayList(initialQuerySize);
  }
  
  public DatabaseQueryTask(String query, Method callback, Object extra, Object... params)
  {
    this(1);
    
    addQuery(query, callback, extra, params);
  }
  
  public void addQuery(String query, Method callback, Object extra, Object... params)
  {
    this.queries.add(new Query(query, callback, extra, params));
  }
  
  public void run()
  {
    Connection cn = Database.getNew();
    if (cn == null) {
      return;
    }
    for (Query q : this.queries)
    {
      PreparedStatement pst = null;
      boolean callBackOk = false;
      try
      {
        pst = cn.prepareStatement(q.query);
        Database.parseValues(pst, q.values);
        ResultSet result = pst.executeQuery();
        callBackOk = ((Boolean)q.callBack.invoke(null, new Object[] { result, q.extra })).booleanValue();
      }
      catch (Exception ex)
      {
        Log.printException("QueryExec (" + q.query + ")", ex);
      }
      if (pst != null) {
        try
        {
          pst.close();
        }
        catch (Exception localException1) {}
      }
      if (!callBackOk) {
        break;
      }
    }
    if (cn != null) {
      try
      {
        cn.close();
      }
      catch (Exception localException2) {}
    }
  }
  
  private class Query
  {
    public final Method callBack;
    public final String query;
    public final Object[] values;
    public final Object extra;
    
    public Query(String q, Method call, Object e, Object... v)
    {
      this.query = q;
      this.values = v;
      this.callBack = call;
      this.extra = e;
    }
  }
}


