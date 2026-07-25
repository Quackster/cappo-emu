package cappo.game.bots;

import cappo.game.player.AvatarLook;

public class BotBase
{
  public AvatarLook defaultLook;
  public String defaultGender;
  
  public BotBase(String look, String gender)
  {
    this.defaultLook = new AvatarLook(look);
    this.defaultGender = gender;
  }
}


