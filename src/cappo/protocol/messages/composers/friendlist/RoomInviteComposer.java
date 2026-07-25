package cappo.protocol.messages.composers.friendlist;

import cappo.engine.network.MessageWriter;
import cappo.protocol.messages.Composer;

public class RoomInviteComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(int FriendId, String InvitationText)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(FriendId), ClientMessage);
    Composer.add(InvitationText, ClientMessage);
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


