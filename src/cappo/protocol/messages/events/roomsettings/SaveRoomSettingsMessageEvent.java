package cappo.protocol.messages.events.roomsettings;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.RoomData;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.navigator.RoomUpdatedComposer;
import cappo.protocol.messages.composers.room.chat.ChatSettingsComposer;
import cappo.protocol.messages.composers.room.engine.RoomVisualizationSettingsComposer;
import cappo.protocol.messages.composers.roomsettings.RoomSettingsSavedComposer;
import cappo.protocol.messages.parsers.roomsettings.SaveRoomSettingsMessageParser;

public class SaveRoomSettingsMessageEvent
  extends IncomingMessageEvent
{
  public void messageReceived(Connection cn)
    throws Exception
  {
    SaveRoomSettingsMessageParser parser = new SaveRoomSettingsMessageParser(cn.currentPacket, cn);
    if (!parser.isValid) {
      return;
    }
    parser.setRoomName(3);
    parser.setRoomDesc();
    parser.setRoomState(-1, 3);
    parser.setRoomPassword();
    parser.setRoomMaxUsers(10, 100, 5);
    parser.setRoomCategory();
    parser.setRoomTags(2, 30);
    parser.setRoomTrading(0, 2);
    parser.setRoomOthersSettings();
    parser.setRoomVisualizationSettings();
    parser.setRoomModPermissionsSettings();
    parser.setRoomChatSettings();
    
    RoomData roomData = parser.getRoomData();
    
    QueueWriter.write(cn.socket, RoomSettingsSavedComposer.compose(roomData.roomId));
    
    RoomTask room = roomData.room;
    if (room != null)
    {
      room.sendMessage(RoomUpdatedComposer.compose(roomData.roomId));
      if (parser.roomVisualizationChanged) {
        room.sendMessage(RoomVisualizationSettingsComposer.compose(Boolean.valueOf(roomData.haveFlag(16)), roomData.wallAnchor, roomData.floorAnchor));
      }
      if (parser.roomChatChanged) {
        room.sendMessage(ChatSettingsComposer.compose(roomData.chatSettings));
      }
    }
  }
}


