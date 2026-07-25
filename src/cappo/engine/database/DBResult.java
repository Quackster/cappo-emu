package cappo.engine.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBResult
{
  public Connection cn;
  public ResultSet data;
  public PreparedStatement pst;
  
  public DBResult()
  {
    this.cn = Database.getNew();
  }
  
  public void close()
  {
    if (this.data != null) {
      try
      {
        this.data.close();
      }
      catch (Exception localException) {}
    }
    if (this.pst != null) {
      try
      {
        this.pst.close();
      }
      catch (Exception localException1) {}
    }
    if (this.cn != null) {
      try
      {
        this.cn.close();
      }
      catch (Exception localException2) {}
    }
  }
}


