package cappo.game.roomengine.settings;

import cappo.game.player.PlayerData;

public class PlayerRight
{
  public PlayerData player;
  public boolean needInsert;
  
  public PlayerRight(PlayerData plr)
  {
    this.player = plr;
  }
  
  public PlayerRight needInsert()
  {
    this.needInsert = true;
    return this;
  }
}


