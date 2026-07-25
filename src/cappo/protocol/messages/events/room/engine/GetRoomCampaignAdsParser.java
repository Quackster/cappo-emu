package cappo.protocol.messages.events.room.engine;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.room.engine.RoomCampaignAdsComposer;

public class GetRoomCampaignAdsParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    QueueWriter.write(Main.socket, RoomCampaignAdsComposer.compose());
  }
}


