package cappo.protocol.messages.events.sound;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.sound.TraxSongInfoComposer;
import java.util.ArrayList;
import java.util.List;

public class GetSongInfoParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    int Count = Main.currentPacket.readInt();
    if (Count > 20) {
      return;
    }
    List<Integer> Discs = new ArrayList(Count);
    for (int i = 0; i < Count; i++) {
      Discs.add(Integer.valueOf(Main.currentPacket.readInt()));
    }
    QueueWriter.write(Main.socket, TraxSongInfoComposer.compose(Discs));
  }
}


