package cappo.game.collections;

import cappo.engine.database.DBResult;
import cappo.engine.database.Database;
import cappo.engine.logging.Log;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MoodlightData
{
  public int CurrentPreset;
  public boolean Enabled;
  public int ItemId;
  public List<MoodlightPreset> Presets;
  
  public class MoodlightPreset
  {
    public boolean BackgroundOnly;
    public String ColorCode;
    public int ColorIntensity;
    
    public MoodlightPreset(String ColorCode, int ColorIntensity, boolean BackgroundOnly)
    {
      this.ColorCode = ColorCode;
      this.ColorIntensity = ColorIntensity;
      this.BackgroundOnly = BackgroundOnly;
    }
  }
  
  public MoodlightData()
  {
    this.Presets = new ArrayList();
  }
  
  public MoodlightData(int id)
  {
    this();
    
    this.ItemId = id;
    

    DBResult result = new DBResult();
    try
    {
      Database.query(result, "SELECT * FROM items_moodlight WHERE item_id = " + id + " LIMIT 1;", new Object[0]);
      if (result.data.next())
      {
        this.Enabled = (result.data.getInt("enabled") == 1);
        this.CurrentPreset = result.data.getInt("current_preset");
        AddPresent(result.data.getString("preset_one"));
        AddPresent(result.data.getString("preset_two"));
        AddPresent(result.data.getString("preset_three"));
      }
      else
      {
        this.Enabled = false;
        this.CurrentPreset = 1;
        AddPresent("#000000,255,0");
        AddPresent("#000000,255,0");
        AddPresent("#000000,255,0");
      }
    }
    catch (Exception ex)
    {
      Log.printException("MoodlightData", ex);
    }
    result.close();
  }
  
  public void mysqlSave()
    throws Exception
  {
    MoodlightPreset present = GetPreset(1);
    String one = present.ColorCode + "," + present.ColorIntensity + (present.BackgroundOnly ? "1" : "0");
    present = GetPreset(2);
    String two = present.ColorCode + "," + present.ColorIntensity + (present.BackgroundOnly ? "1" : "0");
    present = GetPreset(3);
    String three = present.ColorCode + "," + present.ColorIntensity + (present.BackgroundOnly ? "1" : "0");
    
    Database.exec("INSERT INTO items_moodlight (item_id,enabled,current_preset,preset_one,preset_two,preset_three)VALUES(" + this.ItemId + "," + (this.Enabled ? 1 : 0) + "," + this.CurrentPreset + ",?,?,?) on DUPLICATE KEY UPDATE `preset_one`=?,`preset_two`=?,`preset_three`=?;", new Object[] { one, two, three, one, two, three });
  }
  
  public void AddPresent(String Present)
  {
    this.Presets.add(GeneratePreset(Present));
  }
  
  public String GenerateExtraData()
  {
    MoodlightPreset Preset = GetPreset(this.CurrentPreset);
    return (this.Enabled ? "2" : "1") + "," + this.CurrentPreset + "," + (Preset.BackgroundOnly ? "2" : "1") + "," + Preset.ColorCode + "," + Preset.ColorIntensity;
  }
  
  private MoodlightPreset GeneratePreset(String Data)
  {
    String[] Bits = Data.split(",");
    if (!IsValidColor(Bits[0])) {
      Bits[0] = "#000000";
    }
    return new MoodlightPreset(Bits[0], Integer.parseInt(Bits[1]), Bits[2].equals("1"));
  }
  
  private MoodlightPreset GetPreset(int i)
  {
    return (MoodlightPreset)this.Presets.get(--i);
  }
  
  private boolean IsValidColor(String ColorCode)
  {
    if (ColorCode.equals("#000000")) {
      return true;
    }
    if (ColorCode.equals("#0053F7")) {
      return true;
    }
    if (ColorCode.equals("#EA4532")) {
      return true;
    }
    if (ColorCode.equals("#82F349")) {
      return true;
    }
    if (ColorCode.equals("#74F5F5")) {
      return true;
    }
    if (ColorCode.equals("#E759DE")) {
      return true;
    }
    if (ColorCode.equals("#F2F851")) {
      return true;
    }
    return false;
  }
  
  private boolean IsValidIntensity(int Intensity)
  {
    if ((Intensity < 0) || (Intensity > 255)) {
      return false;
    }
    return true;
  }
  
  public void UpdatePreset(String Color, int Intensity, boolean BgOnly)
  {
    if ((!IsValidColor(Color)) || (!IsValidIntensity(Intensity))) {
      return;
    }
    MoodlightPreset Preset = GetPreset(this.CurrentPreset);
    Preset.ColorCode = Color;
    Preset.ColorIntensity = Intensity;
    Preset.BackgroundOnly = BgOnly;
  }
}


