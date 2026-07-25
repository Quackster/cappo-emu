package cappo.protocol.messages.composers.room.engine;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class RoomVisualizationSettingsComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(Boolean HideWall, int WallAnchor, int FloorAnchor)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(HideWall, ClientMessage);
    Composer.writeInt32(WallAnchor, ClientMessage);
    Composer.writeInt32(FloorAnchor, ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


