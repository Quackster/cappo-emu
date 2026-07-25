package cappo.protocol.messages.events.users;

import cappo.engine.network.MessageReader;
import cappo.engine.network.QueueWriter;
import cappo.engine.player.Connection;
import cappo.protocol.messages.IncomingMessageEvent;
import cappo.protocol.messages.composers.users.ApproveNameComposer;

public class ApproveNameParser
  extends IncomingMessageEvent
{
  public void messageReceived(Connection Main)
  {
    String PetName = Main.currentPacket.readString();
    int len = PetName.length();
    if (len < 3) {
      QueueWriter.write(Main.socket, ApproveNameComposer.compose(2, "3"));
    } else if (len > 15) {
      QueueWriter.write(Main.socket, ApproveNameComposer.compose(1, "15"));
    } else if (!ValidPetNameChars(PetName, len)) {
      QueueWriter.write(Main.socket, ApproveNameComposer.compose(3, ""));
    } else {
      QueueWriter.write(Main.socket, ApproveNameComposer.compose(0, ""));
    }
  }
  
  private boolean ValidPetNameChars(String inputStr, int HardCodedLen)
  {
    for (int i = 0; i < HardCodedLen; i++)
    {
      if ((i == 0) && 
        (inputStr.charAt(0) == ' ')) {
        return false;
      }
      if (((inputStr.charAt(i) < 'a') || (inputStr.charAt(i) > 'z')) && ((inputStr.charAt(i) < '0') || (inputStr.charAt(i) > '9'))) {
        return false;
      }
    }
    return true;
  }
}


