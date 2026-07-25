package cappo.engine.settings;

public class PerkAllowance
{
  public String codeName;
  public boolean active;
  public String errorText;
  
  public PerkAllowance(String code, boolean enabled, String error)
  {
    this.codeName = code;
    this.active = enabled;
    this.errorText = error;
  }
}


