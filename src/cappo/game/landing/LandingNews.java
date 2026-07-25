package cappo.game.landing;

import cappo.engine.database.DBResult;
import cappo.engine.database.Database;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LandingNews
{
  public int id;
  public String newTitle;
  public String newText;
  public String newImage;
  public boolean isClientAction;
  public String link;
  public String action;
  public String extra;
  public String button;
  public static List<LandingNews> news = new ArrayList();
  
  public LandingNews(int ID, String title, String text, String btnTxt, String image, boolean clientAction, String lnk, String act, String extraData)
  {
    this.id = ID;
    this.newTitle = title;
    this.newText = text;
    this.button = btnTxt;
    this.newImage = image;
    this.isClientAction = clientAction;
    this.link = lnk;
    this.action = act;
    this.extra = extraData;
  }
  
  public String getLink()
  {
    if (!this.isClientAction) {
      return this.link;
    }
    return this.link + "/" + this.action + "/" + this.extra;
  }
  
  public static void Init(DBResult result)
    throws Exception
  {
    news.clear();
    Database.query(result, "SELECT * FROM landing_news WHERE enabled='1';", new Object[0]);
    while (result.data.next()) {
      news.add(new LandingNews(result.data.getInt("id"), result.data.getString("title"), result.data.getString("text"), result.data.getString("button"), result.data.getString("image"), result.data.getInt("is_link") == 0, result.data.getString("link"), result.data.getString("action"), result.data.getString("extra")));
    }
  }
}


