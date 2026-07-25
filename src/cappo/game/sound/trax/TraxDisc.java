package cappo.game.sound.trax;

public class TraxDisc
{
  public int Id;
  public int Length;
  public String Author;
  public String Name;
  public String SongData;
  
  public TraxDisc(int id, String name, String songdata, int length, String author)
  {
    this.Id = id;
    this.Name = name;
    this.SongData = songdata;
    this.Length = (length * 1000);
    this.Author = author;
  }
}


