package cappo.protocol.messages.events.navigator;

import cappo.engine.logging.Log;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.navigator.OfficialRoomsComposer;

public class GetOfficialRoomsParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    try
    {
      QueueWriter.write(Main.socket, OfficialRoomsComposer.compose());
    }
    catch (Exception ex)
    {
      Log.printException("GetOfficialRoomsParser", ex);
    }
  }
}


