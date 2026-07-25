package cappo.engine.database;

import cappo.engine.Server;
import cappo.engine.logging.Log;
import cappo.engine.threadpools.DatabaseExecTask;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class Database
{
  private static DataSource datasource;
  
  public static Connection getNew()
  {
    try
    {
      return datasource.getConnection();
    }
    catch (Exception ex)
    {
      Log.printException("Database-1", ex);
    }
    return null;
  }
  
  public static void parseValues(PreparedStatement pst, Object... Values)
    throws Exception
  {
    int i = 0;
    for (Object val : Values) {
      if ((val instanceof String)) {
        pst.setString(++i, (String)val);
      } else if ((val instanceof byte[])) {
        pst.setBytes(++i, (byte[])val);
      } else {
        throw new Exception("Unsoported value!" + val.getClass().getSimpleName());
      }
    }
  }
  
  public static void query(DBResult Result, String query, Object... Values)
    throws Exception
  {
    if (Result.data != null)
    {
      Result.data.close();
      Result.pst.close();
    }
    Result.pst = Result.cn.prepareStatement(query);
    parseValues(Result.pst, Values);
    Result.data = Result.pst.executeQuery();
  }
  
  public static void exec(String query, Object... params)
  {
    if (Server.blockMysql) {
      return;
    }
    DatabaseExecTask.addTask(new DatabaseExecTask(query, params), 0, 0);
  }
  
  public static void Init(String host, String port, String db, String user, String pass)
    throws Exception
  {
    PoolProperties p = new PoolProperties();
    
    p.setUrl("jdbc:mysql://" + host + ":" + port + "/" + db);
    p.setDriverClassName("com.mysql.jdbc.Driver");
    p.setUsername(user);
    p.setPassword(pass);
    
    p.setInitSQL("SET SESSION interactive_timeout=360,wait_timeout=360,join_buffer_size=120000000,sort_buffer_size=20000000,read_rnd_buffer_size=20000000;");
    
    p.setJmxEnabled(false);
    
    p.setValidationQuery("SELECT 1");
    p.setValidationInterval(10000L);
    p.setTestWhileIdle(true);
    p.setTestOnBorrow(false);
    p.setTestOnReturn(false);
    
    p.setTimeBetweenEvictionRunsMillis(5000);
    p.setMinEvictableIdleTimeMillis(5000);
    
    p.setRemoveAbandoned(true);
    p.setRemoveAbandonedTimeout(5);
    p.setLogAbandoned(true);
    
    p.setInitialSize(5);
    p.setMinIdle(3);
    p.setMaxIdle(6);
    p.setMaxActive(25);
    p.setMaxWait(2000);
    
    datasource = new DataSource();
    datasource.setPoolProperties(p);
/* :0:   */   }
/* :1:   */   
/* :2:   */   public static void close()
/* :3:   */   {
/* :4:98 */     datasource.close();
/* :5:   */   }
/* :6:   */ }


