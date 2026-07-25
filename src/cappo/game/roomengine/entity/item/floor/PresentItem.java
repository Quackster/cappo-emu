package cappo.game.roomengine.entity.item.floor;

import cappo.game.catalog.Catalog;
import cappo.game.catalog.Catalog.CatalogProduct;
import cappo.game.roomengine.entity.item.extradata.MapStuffData;
import java.util.Map;

public class PresentItem
  extends FloorItem
{
  public static final String MESSAGE = "MESSAGE";
  public static final String PRODUCT_CODE = "PRODUCT_CODE";
  public static final String EXTRA_PARAM = "EXTRA_PARAM";
  public static final String PURCHASER_NAME = "PURCHASER_NAME";
  public static final String PURCHASER_FIGURE = "PURCHASER_FIGURE";
  
  public Catalog.CatalogProduct getProduct()
  {
    MapStuffData data = (MapStuffData)this.extraData;
    return (Catalog.CatalogProduct)Catalog.Items.get(Integer.valueOf(Integer.parseInt((String)data.extraData.get("PRODUCT_CODE"))));
  }
  
  public String getProductParam()
  {
    MapStuffData data = (MapStuffData)this.extraData;
    return (String)data.extraData.get("EXTRA_PARAM");
  }
}


