package cappo.protocol.messages.events.recycler;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.collections.Utils;
import cappo.game.player.data.AvatarData;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.recycler.RecyclerStatusComposer;

public class GetRecyclerStatusParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    int Status = 1;
    int TimeToWait = 0;
    if (Utils.getTimestamp() < Main.avatarData.EcotronNextTime)
    {
      TimeToWait = (int)(Main.avatarData.EcotronNextTime - Utils.getTimestamp());
      Status = 3;
    }
    QueueWriter.write(Main.socket, RecyclerStatusComposer.compose(Status, TimeToWait));
  }
}


