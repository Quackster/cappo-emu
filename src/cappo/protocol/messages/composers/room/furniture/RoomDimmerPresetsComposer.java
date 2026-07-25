package cappo.protocol.messages.composers.room.furniture;

import cappo.engine.network.MessageWriter;
import cappo.game.collections.MoodlightData;
import cappo.game.collections.MoodlightData.MoodlightPreset;
import cappo.protocol.messages.Composer;
import java.util.List;

public class RoomDimmerPresetsComposer
{
  public static int HEADER;
  
  public static final MessageWriter compose(MoodlightData Moodlightdata)
  {
    MessageWriter ClientMessage = new MessageWriter();
    Composer.initPacket(HEADER, ClientMessage);
    Composer.add(Integer.valueOf(Moodlightdata.Presets.size()), ClientMessage);
    Composer.add(Integer.valueOf(Moodlightdata.CurrentPreset), ClientMessage);
    int i = 0;
    for (MoodlightData.MoodlightPreset Preset : Moodlightdata.Presets)
    {
      i++;Composer.add(Integer.valueOf(i), ClientMessage);
      Composer.add(Integer.valueOf(Preset.BackgroundOnly ? 2 : 1), ClientMessage);
      Composer.add(Preset.ColorCode, ClientMessage);
      Composer.add(Integer.valueOf(Preset.ColorIntensity), ClientMessage);
    }
    Composer.endPacket(ClientMessage);
    return ClientMessage;
  }
}


