package cappo.game.collections;

import java.util.ArrayList;
import java.util.List;

public class UnseenItems
{
  public static final int FURNIS = 1;
  public static final int RENTALS = 2;
  public static final int PET = 3;
  public static final int BADGE = 4;
  public static final int BOT = 5;
  public static final int GAMES = 6;
  public int Size;
  public List<List<Integer>> unseenItems = new ArrayList();
  
  public UnseenItems()
  {
    this.unseenItems.add(0, null);
    this.unseenItems.add(1, new ArrayList());
    this.unseenItems.add(2, new ArrayList());
    this.unseenItems.add(3, new ArrayList());
    this.unseenItems.add(4, new ArrayList());
    this.unseenItems.add(5, new ArrayList());
  }
  
  public void AddItem(int Type, int Id)
  {
    List<Integer> items = (List)this.unseenItems.get(Type);
    if (items.isEmpty()) {
      this.Size += 1;
    }
    items.add(Integer.valueOf(Id));
  }
  
  public void ResetItems(int Type)
  {
    List<Integer> items = (List)this.unseenItems.get(Type);
    if (!items.isEmpty())
    {
      this.Size -= 1;
      items.clear();
    }
  }
}


