package cappo.protocol.messages.events.help;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Clients;
import cappo.engine.player.Connection;
import cappo.game.moderation.UserMuted;
import cappo.game.moderation.tickets.HelpTicketReportUser;
import cappo.game.moderation.tickets.HelpTicketsManager;
import cappo.game.player.PlayerData;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.help.CallForHelpMutedComposer;
import cappo.protocol.messages.composers.help.CallForHelpResultComposer;

public class CallForHelp2Parser
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
    HelpTicketReportUser ticket = new HelpTicketReportUser(false);
    ticket.text = cn.currentPacket.readString();
    ticket.category = ((short)cn.currentPacket.readInt());
    ticket.reporterId = cn.playerData.userId;
    ticket.reporterName = cn.playerData.userName;
    
    int userId = cn.currentPacket.readInt();
    if (userId == 0)
    {
      ticket.reportedId = 0;
      ticket.reportedName = "";
    }
    else
    {
      PlayerData client = Clients.getPlayerData(userId);
      if (client == null) {
        return;
      }
      ticket.reportedId = client.userId;
      ticket.reportedName = client.userName;
    }
    ticket.roomName = "";
    
    HelpTicketsManager.addTicket(ticket);
    
    QueueWriter.write(cn.socket, CallForHelpResultComposer.compose(0));
  }
}


