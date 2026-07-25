package cappo.game.roomengine.settings;

import cappo.game.player.PlayerData;

public class PlayerBan
{
  public PlayerData player;
  public long timeout;
  
  public PlayerBan(PlayerData plr, long time)
  {
    this.player = plr;
    this.timeout = time;
  }
}


