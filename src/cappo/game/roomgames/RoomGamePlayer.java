package cappo.game.roomgames;

import cappo.game.roomengine.entity.live.Avatar;

public class RoomGamePlayer
{
  public static final short TEAM_1 = 1;
  public static final short TEAM_2 = 2;
  public static final short TEAM_3 = 3;
  public static final short TEAM_4 = 4;
  public short team;
  public short score;
  public Avatar avatarEntity;
  
  public RoomGamePlayer(short playerTeam, Avatar player)
  {
    this.team = playerTeam;
    this.avatarEntity = player;
  }
  
  public void removePlayer()
  {
    this.avatarEntity.roomGamePlayer = null;
  }
}


