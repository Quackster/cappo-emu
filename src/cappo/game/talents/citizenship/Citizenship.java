package cappo.game.talents.citizenship;

import cappo.game.talents.TalentTrack;
import cappo.game.talents.citizenship.levels.CitizenshipLevel1;
import cappo.game.talents.citizenship.levels.CitizenshipLevel2;
import cappo.game.talents.citizenship.levels.CitizenshipLevel3;
import cappo.game.talents.citizenship.levels.CitizenshipLevel4;
import cappo.game.talents.citizenship.levels.CitizenshipLevel5;
import java.util.List;

public class Citizenship
  extends TalentTrack
{
  public Citizenship()
  {
    this.levels.add(new CitizenshipLevel1());
    this.levels.add(new CitizenshipLevel2());
    this.levels.add(new CitizenshipLevel3());
    this.levels.add(new CitizenshipLevel4());
    this.levels.add(new CitizenshipLevel5());
  }
}


