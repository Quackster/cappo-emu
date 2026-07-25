package cappo.protocol.messages.events.help;

import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.moderation.UserMuted;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.help.CallForHelpMutedComposer;
import cappo.protocol.messages.composers.help.CallForHelpOpenComposer;

public class CallForHelpOpenParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection cn)
  {
    if (cn.userMuted != null)
    {
      if (cn.userMuted.isMuted())
      {
        QueueWriter.write(cn.socket, CallForHelpMutedComposer.compose(cn.userMuted.reason));
        return;
      }
      cn.userMuted = null;
    }
    QueueWriter.write(cn.socket, CallForHelpOpenComposer.compose());
  }
}


