package cappo.protocol.messages.events.help;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.game.moderation.UserMuted;
import cappo.game.moderation.tickets.HelpTicketReportRoom;
import cappo.game.moderation.tickets.HelpTicketsManager;
import cappo.game.player.PlayerData;
import cappo.game.roomengine.RoomData;
import cappo.game.roomengine.RoomManager;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.help.CallForHelpMutedComposer;
import cappo.protocol.messages.composers.help.CallForHelpResultComposer;

public class CallForHelpRoomParser
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
    HelpTicketReportRoom ticket = new HelpTicketReportRoom(false);
    ticket.text = cn.currentPacket.readString();
    ticket.category = ((short)cn.currentPacket.readInt());
    ticket.reporterId = cn.playerData.userId;
    ticket.reporterName = cn.playerData.userName;
    ticket.roomId = cn.currentPacket.readInt();
    ticket.reportedName = "";
    
    RoomData room = RoomManager.getRoom(ticket.roomId);
    if (room == null) {
      return;
    }
    ticket.roomId = room.roomId;
    ticket.roomName = room.name;
    
    HelpTicketsManager.addTicket(ticket);
    
    QueueWriter.write(cn.socket, CallForHelpResultComposer.compose(0));
  }
}


