package cappo.protocol.messages;

import cappo.engine.network.MessageWriter;

public class Composer
{
  public static void writeInt32(int add, MessageWriter message)
  {
    message.writetInt32(add);
  }
  
  public static void writeInt16(int add, MessageWriter message)
  {
    message.writeInt16(add);
  }
  
  public static void writeBoolean(boolean add, MessageWriter message)
  {
    message.writeBoolean(add);
  }
  
  public static void writeByte(byte add, MessageWriter message)
  {
    message.writeByte(add);
  }
  
  public static void writeChar(char add, MessageWriter message)
  {
    message.writeChar(add);
  }
  
  public static void add(Object add, MessageWriter Message)
  {
    if (add == null) {
      throw new UnsupportedOperationException("NULL Param in Append!");
    }
    if ((add instanceof Integer))
    {
      Message.writetInt32(((Integer)add).intValue());
      return;
    }
    if ((add instanceof Short))
    {
      Message.writetInt32(((Short)add).shortValue());
      return;
    }
    if ((add instanceof String))
    {
      Message.writeString((String)add);
      return;
    }
    if ((add instanceof Boolean))
    {
      Message.writeBoolean(((Boolean)add).booleanValue());
      return;
    }
    if ((add instanceof byte[]))
    {
      Message.writeBytes((byte[])add);
      return;
    }
    if ((add instanceof Long))
    {
      Message.writetInt32(((Long)add).intValue());
      return;
    }
    throw new UnsupportedOperationException("Bad Param in Append " + add.getClass());
  }
  
  public static void endPacket(MessageWriter Message)
  {
    int tmp = Message.writer;
    int len = tmp - 4;
    if ((len < 2) || (len > 131072)) {
      throw new UnsupportedOperationException("Bad Message! Len=" + len);
    }
    Message.writer = 0;
    Message.writetInt32(len);
    Message.writer = tmp;
    Message.isReady = true;
  }
  
  public static void initPacket(int headerId, MessageWriter Message)
  {
    if (headerId == 0) {
      throw new UnsupportedOperationException("Header = 0!!");
    }
    Message.debugId = headerId;
    Message.writer = 4;
    Message.writeInt16((short)headerId);
  }
}


