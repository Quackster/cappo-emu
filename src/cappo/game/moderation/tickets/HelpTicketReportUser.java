package cappo.game.moderation.tickets;

public class HelpTicketReportUser
  extends HelpTicket
{
  public HelpTicketReportUser(boolean inRoom)
  {
    if (inRoom) {
      this.type = 2;
/* ::  */     } else {
/* ;:8 */       this.type = 1;
/* <:  */     }
/* =:  */   }
/* >:  */ }


