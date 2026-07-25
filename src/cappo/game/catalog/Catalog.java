package cappo.game.catalog;

import cappo.engine.database.DBResult;
import cappo.engine.database.Database;
import cappo.engine.logging.Log;
import cappo.engine.network.MessageWriter;
import cappo.game.collections.BaseItem;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Catalog
{
  public static class CatalogSubItem
  {
    public Integer amount;
    public Integer Expire;
    public String extraData;
    public BaseItem baseItem;
    public int extraParam;
  }
  
  public static class CatalogProduct
  {
    public boolean AllowGift;
    public int clubLevel;
    public int creditCost;
    public int activityPointsType;
    public int productId;
    public List<Catalog.CatalogSubItem> items = new ArrayList();
    public String itemName;
    public int activityPointCost;
    public boolean allowBundleDiscounts;
    public int pageId;
    public Integer uniqueLimitedItemsLaunched;
    public Integer uniqueLimitedItemsLeft;
  }
  
  public static class CatalogPage
  {
    public String caption;
    public int iconColor;
    public int IconImage;
    public int pageId;
    public List<Catalog.CatalogProduct> items = new ArrayList();
    public String Layout;
    public String[][] PageData;
    public int parentId;
    public int minRank;
    public boolean acceptSeasonCurrencyAsCredits;
    public String pageName;
    public boolean clubOnly;
    public boolean isEnabled;
    public boolean isVisible;
    public boolean isCacheDisabled;
  }
  
  public static Map<Integer, List<CatalogPage>> catalogMap = new HashMap();
  public static Map<Integer, CatalogPage> pages = new HashMap();
  public static Map<Integer, CatalogProduct> Items = new HashMap();
  public static Map<Integer, CatalogProduct> limitedItems = new HashMap();
  public static Map<Integer, MessageWriter> indexMap = new ConcurrentHashMap(1000);
  public static boolean isBlocked;
  public static Map<Integer, MessageWriter> pageMap = new ConcurrentHashMap(1000);
  
  public static void block()
  {
    isBlocked = true;
    indexMap.clear();
    pageMap.clear();
    catalogMap.clear();
    pages.clear();
    for (CatalogProduct item : Items.values()) {
      if (item.uniqueLimitedItemsLaunched.intValue() > 0)
      {
        int sells = item.uniqueLimitedItemsLaunched.intValue() - item.uniqueLimitedItemsLeft.intValue();
        try
        {
          Database.exec("UPDATE catalog_items_copy SET limited_sells=" + sells + " WHERE id=" + item.productId + ";", new Object[0]);
        }
        catch (Exception ex)
        {
          Log.printException("Catalog", ex);
        }
      }
    }
    Items.clear();
  }
  
  public static void unblock()
  {
    isBlocked = false;
  }
  
  private static void GeneratePage(CatalogPage Page)
  {
    pages.put(Integer.valueOf(Page.pageId), Page);
    
    List<CatalogPage> a = (List)catalogMap.get(Integer.valueOf(Page.parentId));
    if (a == null)
    {
      a = new ArrayList();
      catalogMap.put(Integer.valueOf(Page.parentId), a);
    }
    a.add(Page);
  }
  
  public static void Init(DBResult result)
    throws Exception
  {
    CatalogPage page = new CatalogPage();
    
    page.pageId = -1;
    page.parentId = 0;
    page.caption = "";
    page.minRank = 0;
    page.iconColor = 0;
    page.IconImage = 0;
    page.pageName = "root";
    page.isVisible = true;
    GeneratePage(page);
    
    Database.query(result, "SELECT * FROM catalog_pages ORDER BY order_num;", new Object[0]);
    while (result.data.next())
    {
      page = new CatalogPage();
      page.isEnabled = result.data.getString("enabled").equals("1");
      page.isVisible = result.data.getString("visible").equals("1");
      page.pageId = result.data.getInt("id");
      page.parentId = result.data.getInt("parent_id");
      page.caption = result.data.getString("caption");
      page.minRank = result.data.getInt("min_rank");
      page.clubOnly = result.data.getString("club_only").equals("1");
      page.iconColor = result.data.getInt("icon_color");
      page.IconImage = result.data.getInt("icon_image");
      page.Layout = result.data.getString("page_layout");
      String[] images = null;String[] texts = null;
      if (page.Layout.equals("frontpage"))
      {
        images = new String[] { result.data.getString("page_headline"), result.data.getString("page_teaser"), result.data.getString("page_special") };
        texts = new String[] { "", "", "", "�Conseguir Cr�ditos f�cilmente?", result.data.getString("page_text2"), result.data.getString("page_text_details"), "", "#FEFEFE", "#FEFEFE", "Lograr Cr�ditos>>", "credits" };
        page.Layout = "frontpage3";
      }
      else if (page.Layout.equals("camera1"))
      {
        texts = new String[] { "ctlg_header_text", "ctlg_text_1" };
      }
      else if (page.Layout.equals("club1"))
      {
        texts = new String[] { "ctlg_text_1", "ctlg_text_2", "ctlg_text_3", "ctlg_text_4", "ctlg_text_5" };
      }
      else if (page.Layout.equals("club2"))
      {
        texts = new String[] { "ctlg_text_1", "ctlg_text_2", "ctlg_text_3", "ctlg_text_4" };
      }
      else if (page.Layout.equals("presents"))
      {
        texts = new String[] { "ctlg_header_text", "ctlg_text1" };
      }
      else if (page.Layout.equals("collectibles"))
      {
        texts = new String[] { "ctlg_header_text", "ctlg_collectibles_link" };
      }
      else if (page.Layout.equals("purse"))
      {
        texts = new String[] { "ctlg_header_text", "ctlg_special_txt" };
      }
      else if (page.Layout.startsWith("pets"))
      {
        texts = new String[] { result.data.getString("page_text1"), "Give a name:", "Pick a color:", "Pick a race:" };
      }
      else if (page.Layout.startsWith("pets2"))
      {
        texts = new String[] { result.data.getString("page_text1"), "Give a name:", "Pick a color:", "Pick a race:" };
      }
      else if (page.Layout.startsWith("pets3"))
      {
        texts = new String[] { result.data.getString("page_text1"), "Give a name:", "Pick a color:", "Pick a race:" };
      }
      else if (page.Layout.equals("info_credits"))
      {
        texts = new String[] { "ctlg_text_1", "ctlg_text_2", "ctlg_text_3", "ctlg_text_4", "ctlg_text_5", "ctlg_text_6", "ctlg_text_7", "ctlg_text_8" };
      }
      else if (page.Layout.equals("info_pixels"))
      {
        texts = new String[] { "ctlg_text_1", "ctlg_text_2", "ctlg_text_3", "ctlg_text_4", "ctlg_text_5", "ctlg_text_6", "ctlg_text_7", "ctlg_text_8" };
      }
      else if (page.Layout.equals("bots"))
      {
        images = new String[] { result.data.getString("page_headline") };
        
        texts = new String[] { "", "", "", "" };
      }
      else if (page.Layout.equals("badge_display"))
      {
        texts = new String[] { result.data.getString("page_text1"), result.data.getString("page_text_details"), result.data.getString("page_teaser") };
        images = new String[] { result.data.getString("page_headline"), result.data.getString("page_teaser"), result.data.getString("page_special") };
      }
      if (images == null) {
        images = new String[] { result.data.getString("page_headline"), result.data.getString("page_teaser"), result.data.getString("page_special"), "", "" };
      }
      if (texts == null) {
        texts = new String[] { result.data.getString("page_text1"), result.data.getString("page_text_details"), result.data.getString("page_teaser"), "", "" };
      }
      page.PageData = new String[][] { images, texts };
      

      page.pageName = (page.Layout + page.pageId);
      GeneratePage(page);
    }
    Database.query(result, "SELECT * FROM catalog_items_copy WHERE ltd_id = 0;", new Object[0]);
    while (result.data.next())
    {
      page = (CatalogPage)pages.get(Integer.valueOf(result.data.getInt("page_id")));
      if (page != null)
      {
        CatalogProduct product = new CatalogProduct();
        product.productId = result.data.getInt("id");
        product.pageId = page.pageId;
        product.itemName = result.data.getString("catalog_name");
        product.creditCost = result.data.getInt("cost_credits");
        product.uniqueLimitedItemsLaunched = Integer.valueOf(0);
        product.uniqueLimitedItemsLeft = Integer.valueOf(0);
        product.allowBundleDiscounts = true;
        int pixels = result.data.getInt("cost_pixels");
        int cristals = result.data.getInt("cost_crystal");
        if (cristals > pixels)
        {
          product.activityPointCost = cristals;
          product.activityPointsType = 105;
        }
        else
        {
          product.activityPointCost = pixels;
          product.activityPointsType = 0;
        }
        String items = result.data.getString("item_ids");
        for (String itemid : items.split(";"))
        {
          CatalogSubItem subitem = new CatalogSubItem();
          subitem.baseItem = ((BaseItem)BaseItem.baseItems.get(Integer.valueOf(Integer.parseInt(itemid))));
          if (subitem.baseItem == null)
          {
            Log.printLog("BASE NULL:" + itemid);
          }
          else
          {
            subitem.amount = Integer.valueOf(result.data.getInt("amount"));
            if ((product.allowBundleDiscounts) && (subitem.amount.intValue() > 1)) {
              product.allowBundleDiscounts = false;
            }
            subitem.extraParam = result.data.getInt("extra_param");
            subitem.extraData = result.data.getString("extra_data");
            
            subitem.Expire = Integer.valueOf(-1);
            product.items.add(subitem);
          }
        }
        if ((product.allowBundleDiscounts) && (product.items.size() > 1)) {
          product.allowBundleDiscounts = false;
        }
        product.AllowGift = product.allowBundleDiscounts;
        product.clubLevel = (page.clubOnly ? 2 : 0);
        page.items.add(product);
        Items.put(Integer.valueOf(product.productId), product);
      }
    }
    Database.query(result, "SELECT * FROM catalog_items_copy LEFT JOIN ltd_items ON (catalog_items_copy.ltd_id=ltd_items.id) WHERE ltd_id != 0;", new Object[0]);
    while (result.data.next())
    {
      page = (CatalogPage)pages.get(Integer.valueOf(result.data.getInt("page_id")));
      if (page != null)
      {
        CatalogProduct product = new CatalogProduct();
        product.productId = result.data.getInt("id");
        product.pageId = page.pageId;
        product.itemName = result.data.getString("catalog_name");
        product.creditCost = result.data.getInt("cost_credits");
        product.uniqueLimitedItemsLaunched = Integer.valueOf(result.data.getInt("limited_stack"));
        product.uniqueLimitedItemsLeft = Integer.valueOf(product.uniqueLimitedItemsLaunched.intValue() - result.data.getInt("limited_sells"));
        product.allowBundleDiscounts = false;
        int pixels = result.data.getInt("cost_pixels");
        int cristals = result.data.getInt("cost_crystal");
        if (cristals > pixels)
        {
          product.activityPointCost = cristals;
          product.activityPointsType = 105;
        }
        else
        {
          product.activityPointCost = pixels;
          product.activityPointsType = 0;
        }
        String items = result.data.getString("item_ids");
        for (String itemid : items.split(";"))
        {
          CatalogSubItem subitem = new CatalogSubItem();
          subitem.baseItem = ((BaseItem)BaseItem.baseItems.get(Integer.valueOf(Integer.parseInt(itemid))));
          if (subitem.baseItem == null)
          {
            Log.printLog("BASE NULL:" + itemid);
          }
          else
          {
            subitem.amount = Integer.valueOf(result.data.getInt("amount"));
            subitem.extraParam = result.data.getInt("extra_param");
            subitem.extraData = result.data.getString("extra_data");
            subitem.Expire = Integer.valueOf(-1);
            product.items.add(subitem);
          }
        }
        product.clubLevel = (page.clubOnly ? 2 : 0);
        page.items.add(product);
        Items.put(Integer.valueOf(product.productId), product);
        CatalogProduct prev = (CatalogProduct)limitedItems.put(Integer.valueOf(product.productId), product);
        if (prev != null) {
          product.uniqueLimitedItemsLeft = prev.uniqueLimitedItemsLeft;
        }
        LimitedItems.add(product.productId, new LimitedItems(product, result.data.getLong("ltd_start"), result.data.getLong("ltd_end")));
      }
    }
  }
}


