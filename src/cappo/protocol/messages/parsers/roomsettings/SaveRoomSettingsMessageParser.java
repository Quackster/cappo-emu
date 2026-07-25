package cappo.protocol.messages.parsers.roomsettings;

import cappo.engine.network.MessageReader;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.RoomManager;
import cappo.game.roomengine.entity.live.Avatar;
import cappo.game.roomengine.settings.ChatSettings;
import cappo.game.roomengine.settings.ControllerLevels;

public class SaveRoomSettingsMessageParser
{
  public boolean isValid;
  private RoomData roomData;
  private String roomName;
  private String roomDesc;
  private int roomState;
  private String roomPassword;
  private int roomMaxUsers;
  private int roomCategory;
  private String[] roomTags;
  private int roomTrading;
  private boolean roomAllowPets;
  private boolean roomAllowPetsEat;
  private boolean roomAllowWalkthrough;
  private boolean roomHideWall;
  private int roomWallAnchor;
  private int roomFloorAnchor;
  private int roomMute;
  private int roomKick;
  private int roomBan;
  private int chatMode;
  private int chatBubbleWidth;
  private int chatScrollSpeed;
  public boolean roomVisualizationChanged;
  public boolean roomChatChanged;
  
  public SaveRoomSettingsMessageParser(MessageReader reader, Connection cn)
  {
    int roomId = reader.readInt();
    if (cn.avatar == null)
    {
      this.roomData = RoomManager.getRoom(roomId);
      if (this.roomData == null) {
        return;
      }
      int controllerLevel = ControllerLevels.getLevel(cn.playerData, this.roomData, this.roomData.room);
      if (controllerLevel >= 4) {}
    }
    else
    {
      this.roomData = cn.avatar.room.roomData;
      if (cn.avatar.controllerLevel < 4) {
        return;
      }
    }
    this.roomName = reader.readString();
    this.roomDesc = reader.readString();
    this.roomState = reader.readInt();
    this.roomPassword = reader.readString();
    this.roomMaxUsers = reader.readInt();
    this.roomCategory = reader.readInt();
    
    int tagSize = reader.readInt();
    this.roomTags = new String[tagSize];
    for (int i = 0; i < tagSize; i++) {
      this.roomTags[i] = reader.readString().toLowerCase();
    }
    this.roomTrading = reader.readInt();
    
    this.roomAllowPets = reader.readBoolean();
    this.roomAllowPetsEat = reader.readBoolean();
    this.roomAllowWalkthrough = reader.readBoolean();
    
    this.roomHideWall = reader.readBoolean();
    this.roomWallAnchor = reader.readInt();
    this.roomFloorAnchor = reader.readInt();
    
    this.roomMute = reader.readInt();
    this.roomKick = reader.readInt();
    this.roomBan = reader.readInt();
    
    this.chatMode = reader.readInt();
    this.chatBubbleWidth = reader.readInt();
    this.chatScrollSpeed = reader.readInt();
    
    this.isValid = true;
  }
  
  public RoomData getRoomData()
  {
    return this.roomData;
  }
  
  public void setRoomName(int minLen)
  {
    if (this.roomName.length() > minLen) {
      this.roomData.name = this.roomName;
    }
  }
  
  public void setRoomDesc()
  {
    this.roomData.description = this.roomDesc;
  }
  
  public void setRoomState(int moreThan, int lessThan)
  {
    if ((this.roomState > moreThan) && (this.roomState < lessThan)) {
      this.roomData.state = this.roomState;
    }
  }
  
  public void setRoomPassword()
  {
    this.roomData.password = this.roomPassword;
  }
  
  public void setRoomMaxUsers(int min, int max, int mod)
  {
    if ((this.roomMaxUsers > max) || (this.roomMaxUsers < min)) {
      return;
    }
    if (this.roomMaxUsers % mod != 0) {
      return;
    }
    this.roomData.updateMaxUsers(this.roomMaxUsers);
  }
  
  public void setRoomCategory()
  {
    this.roomData.category = this.roomCategory;
  }
  
  public void setRoomTags(int maxCount, int maxLen)
  {
    int tagCount = this.roomTags.length;
    if (tagCount > maxCount) {
      return;
    }
    for (String tag : this.roomTags)
    {
      int len = tag.length();
      if (len > maxLen) {
        return;
      }
    }
    for (String tag : this.roomTags) {
      RoomManager.AddTag(tag);
    }
    for (String tag : this.roomData.tags) {
      RoomManager.RemoveTag(tag);
    }
    this.roomData.tags = this.roomTags;
  }
  
  public void setRoomTrading(int minVal, int maxVal)
  {
    if ((this.roomTrading < minVal) || (this.roomTrading > maxVal)) {
      return;
    }
    this.roomData.tradingSettings.permissions = this.roomTrading;
  }
  
  public void setRoomOthersSettings()
  {
    this.roomData.setFlag(2, this.roomAllowPets);
    this.roomData.setFlag(4, this.roomAllowPetsEat);
    this.roomData.setFlag(8, this.roomAllowWalkthrough);
  }
  
  public void setRoomVisualizationSettings()
  {
    if (this.roomData.haveFlag(16) != this.roomHideWall)
    {
      this.roomData.setFlag(16, this.roomHideWall);
      this.roomVisualizationChanged = true;
    }
    if (this.roomData.wallAnchor != this.roomWallAnchor)
    {
      this.roomData.wallAnchor = this.roomWallAnchor;
      this.roomVisualizationChanged = true;
    }
    if (this.roomData.floorAnchor != this.roomFloorAnchor)
    {
      this.roomData.floorAnchor = this.roomFloorAnchor;
      this.roomVisualizationChanged = true;
    }
  }
  
  public void setRoomModPermissionsSettings()
  {
    if ((this.roomMute < 0) || 
      (this.roomMute > 1)) {
      return;
    }
    if ((this.roomKick < 0) || 
      (this.roomKick > 2)) {
      return;
    }
    if ((this.roomBan < 0) || 
      (this.roomBan > 1)) {
      return;
    }
    this.roomData.modPermissions.permissionsMute = this.roomMute;
    this.roomData.modPermissions.permissionsKick = this.roomKick;
    this.roomData.modPermissions.permissionsBan = this.roomBan;
  }
  
  public void setRoomChatSettings()
  {
    if ((this.chatMode < 0) || 
      (this.chatMode > 1)) {
      return;
    }
    if ((this.chatBubbleWidth < 0) || 
      (this.chatBubbleWidth > 2)) {
      return;
    }
    if ((this.chatScrollSpeed < 0) || 
      (this.chatScrollSpeed > 2)) {
      return;
    }
    if (this.roomData.chatSettings.chatMode != this.chatMode)
    {
      this.roomData.chatSettings.chatMode = this.chatMode;
      this.roomChatChanged = true;
    }
    if (this.roomData.chatSettings.chatBubbleWidth != this.chatBubbleWidth)
    {
      this.roomData.chatSettings.chatBubbleWidth = this.chatBubbleWidth;
      this.roomChatChanged = true;
    }
    if (this.roomData.chatSettings.chatScrollSpeed != this.chatScrollSpeed)
    {
      this.roomData.chatSettings.chatScrollSpeed = this.chatScrollSpeed;
      this.roomChatChanged = true;
    }
  }
}


