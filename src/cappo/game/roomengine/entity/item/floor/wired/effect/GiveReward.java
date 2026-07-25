package cappo.game.roomengine.entity.item.floor.wired.effect;

import cappo.engine.database.DBResult;
import cappo.engine.database.Database;
import cappo.engine.logging.Log;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.catalog.Catalog;
import cappo.game.catalog.Catalog.CatalogProduct;
import cappo.game.collections.Utils;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.roomevents.wired.GiveRewardEvent;
import cappo.protocol.messages.composers.userdefinedroomevents.WiredRewardNotificationComposer;
import cappo.protocol.messages.events.catalog.PurchaseFromCatalogParser;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GiveReward
  extends WiredEffectBase
{
  private int option1;
  private int option2;
  private int option3;
  
  public int getCode()
  {
    return 17;
  }
  
  public boolean needUser()
  {
    return false;
  }
  
  public void setWiredOption(int index, int option)
  {
    if (index == 0) {
      this.option1 = option;
    } else if (index == 1) {
      this.option2 = option;
    } else if (index == 2) {
      this.option3 = option;
    }
  }
  
  public int[] getWiredOptions()
  {
    return new int[] { this.option1, this.option2, this.option3 };
  }
  
  public void setWiredData(String data)
  {
    this.rewards.clear();
    
    int probability = 0;
    if (!data.isEmpty())
    {
      String[] rewardItems = data.split(";");
      for (String rwd : rewardItems) {
        if (!rwd.isEmpty())
        {
          String[] parts = rwd.split(",");
          if ((parts.length == 3) && (!parts[1].isEmpty()))
          {
            int prob = Integer.parseInt(parts[2]);
            if ((prob >= 0) && (prob <= 100))
            {
              Reward reward = new Reward();
              reward.isBadge = (!parts[0].equals("1"));
              if (!reward.isBadge)
              {
                Catalog.CatalogProduct product = (Catalog.CatalogProduct)Catalog.Items.get(Integer.valueOf(Integer.parseInt(parts[1])));
                if (product == null) {}
              }
              else
              {
                reward.product = parts[1];
                
                reward.probability = prob;
                reward.probabilityStartAt = probability;
                
                probability += prob;
                if (probability > 100)
                {
                  this.rewards.clear();
                  return;
                }
                this.rewards.add(reward);
              }
            }
          }
        }
      }
    }
  }
  
  public void saveData()
  {
    try
    {
      Database.exec("INSERT INTO trigger_item (trigger_id,trigger_data)VALUES(" + this.itemId + ",?) on DUPLICATE KEY UPDATE `trigger_data`=?;", new Object[] { getWiredData(), getWiredData() });
      super.saveData();
    }
    catch (Exception ex)
    {
      Log.printException("ShowMessage-saveData", ex);
    }
  }
  
  public void loadData(DBResult result)
  {
    try
    {
      Database.query(result, "SELECT trigger_data FROM trigger_item WHERE trigger_id = " + this.itemId + ";", new Object[0]);
      if (result.data.next()) {
        setWiredData(result.data.getString("trigger_data"));
      }
      super.loadData(result);
    }
    catch (Exception ex)
    {
      Log.printException("ShowMessage-loadData", ex);
    }
  }
  
  public String getWiredData()
  {
    String data = "";
    for (Reward reward : this.rewards)
    {
      if (!data.isEmpty()) {
        data = data.concat(";");
      }
      data = data.concat(reward.isBadge ? "0" : "1").concat(",").concat(reward.product).concat(",").concat(Integer.toString(reward.probability));
    }
    return data;
  }
  
  public void invoke(Connection invoker)
  {
    if (this.delayEffect > 0) {
      getRoom().addItemEvent(new GiveRewardEvent(this, invoker), this.delayEffect);
    } else {
      doEffect(this, invoker);
    }
  }
  
  public static void doEffect(GiveReward wired, Connection invoker)
  {
    int rnd = Utils.GetRandomNumber(1, 100);
    for (Reward reward : wired.rewards) {
      if ((rnd >= reward.probabilityStartAt) && (reward.probability >= rnd))
      {
        if (invoker == null) {
          reward.giveReward(wired.getRoom());
        } else {
          reward.giveReward(invoker);
        }
        return;
      }
    }
    QueueWriter.writeAndFlush(invoker.socket, WiredRewardNotificationComposer.compose(4));
  }
  
  private final List<Reward> rewards = new ArrayList();
  
  private class Reward
  {
    public int probabilityStartAt;
    public int probability;
    public boolean isBadge;
    public String product;
    
    private Reward() {}
    
    public void giveReward(RoomTask room)
    {
      for (Avatar User : room.userList.values()) {
        give(User.cn);
      }
    }
    
    public void giveReward(Connection cn)
    {
      give(cn);
    }
    
    private void give(Connection cn)
    {
      if (!this.isBadge)
      {
        Catalog.CatalogProduct catalogProduct = (Catalog.CatalogProduct)Catalog.Items.get(Integer.valueOf(Integer.parseInt(this.product)));
        if (catalogProduct == null) {
          return;
        }
        PurchaseFromCatalogParser.buyProduct(catalogProduct, "", 1, cn);
        QueueWriter.writeAndFlush(cn.socket, WiredRewardNotificationComposer.compose(6));
      }
      else
      {
        cn.giveBadge(this.product);
        QueueWriter.writeAndFlush(cn.socket, WiredRewardNotificationComposer.compose(7));
      }
    }
  }
}


