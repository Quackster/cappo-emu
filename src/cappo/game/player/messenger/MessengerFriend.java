package cappo.game.player.messenger;

public class MessengerFriend
{
  public int userId;
  public int friendType;
  public boolean needUpdate;
  
  public MessengerFriend(int userid, int type)
  {
    this.userId = userid;
    this.friendType = type;
  }
}


