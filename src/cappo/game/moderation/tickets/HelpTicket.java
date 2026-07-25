package cappo.game.moderation.tickets;

public class HelpTicket
{
  public static final short STATUS_OPEN = 1;
  public static final short STATUS_PICKED = 2;
  public static final short STATUS_CLOSED = 3;
  public int id;
  public short status;
  public short type;
  public short category;
  public short priority;
  public int reporterId;
  public String reporterName;
  public int reportedId;
  public String reportedName;
  public int handlerId;
  public String handlerName;
  public long timeStamp;
  public int chatLogId;
  public String roomName;
  public int roomId;
  public int roomType = 1;
  public String text;
}


