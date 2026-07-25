package cappo.game.player.messenger;

public class MessengerFriendUpdate
{
  public static final int UPDATE = 0;
  public static final int ADD = 1;
  public static final int REMOVE = -1;
  public int userId;
  public int type;
  
  public MessengerFriendUpdate(int id, int updateType)
  {
    this.userId = id;
    this.type = updateType;
  }
}


