package cappo.protocol.messages.composers.serializers;

import cappo.engine.network.MessageWriter;
import cappo.game.roomengine.settings.ModerationPermissions;
import cappo.protocol.messages.Composer;

public class SerializeRoomModerationPermissions
{
  public static void parse(ModerationPermissions perms, MessageWriter writer)
  {
    Composer.writeInt32(perms.permissionsMute, writer);
    Composer.writeInt32(perms.permissionsKick, writer);
    Composer.writeInt32(perms.permissionsBan, writer);
  }
}


