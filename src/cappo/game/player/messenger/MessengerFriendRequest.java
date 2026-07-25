package cappo.game.player.messenger;

public class MessengerFriendRequest
{
  public int userid;
  public String username;
  public boolean needInsert;
  
  public MessengerFriendRequest(int id, String name, boolean insert)
  {
    this.userid = id;
    this.username = name;
    this.needInsert = insert;
  }
}


