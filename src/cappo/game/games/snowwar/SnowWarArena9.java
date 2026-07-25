package cappo.game.games.snowwar;

import cappo.game.collections.BaseItem;
import cappo.game.games.snowwar.gameobjects.GameItemObject;
import cappo.game.games.snowwar.gameobjects.PileGameObject;
import cappo.game.roomengine.entity.item.extradata.ExtraDataBase;
import cappo.game.roomengine.entity.item.extradata.MapStuffData;
import java.util.List;
import java.util.Map;

public class SnowWarArena9
  extends SnowWarArenaBase
{
  public SnowWarArena9()
  {
    this.ArenaType = 9;
    this.ArenaHeight = 50;
    this.ArenaWidth = 50;
    this.HeightMap = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\rxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\rxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\rxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\rxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\rxxxxxxxxxxxxxx000000000000000xxxxxxxxxxxxxxxxxxxxx\rxxxxxxxxxxxxx00000000000000000xxxxxxxxxxxxxxxxxxxx\rxxxxxxxxxxxx0000000000000000000xxxxxxxxxxxxxxxxxxx\rxxxxxxxxxxx000000000000000000000xxxxxxxxxxxxxxxxxx\rxxxxxxxxxx00000000000000000000000xxxxxxxxxxxxxxxxx\rxxxxxxxxx0000000000000000000000000xxxxxxxxxxxxxxxx\rxxxxxxxx000000000000000000000000000xxxxxxxxxxxxxxx\rxxxxxxx00000000000000000000000000000xxxxxxxxxxxxxx\rxxxxxx0000000000000000000000000000000xxxxxxxxxxxxx\rxxxxx000000000000000000000000000000000xxxxxxxxxxxx\rxxxxx0000000000000000000000000000000000xxxxxxxxxxx\rxxxxx00000000000000000000000000000000000xxxxxxxxxx\rxxxxx000000000000000000000000000000000000xxxxxxxxx\rxxxxx0000000000000000000000000000000000000xxxxxxxx\rxxxxx00000000000000000000000000000000000000xxxxxxx\rxxxxx000000000000000000000000000000000000000xxxxxx\rxxxxx0000000000000000000000000000000000000000xxxxx\r0xxxx00000000000000000000000000000000000000000xxxx\rxxxxx00000000000000000000000000000000000000000xxxx\rxxxxx00000000000000000000000000000000000000000xxxx\rxxxxx000000000000000000000000000000000000000000xxx\rxxxxx000000000000000000000000000000000000000000xxx\rxxxxx000000000000000000000000000000000000000000xxx\rxxxxxx00000000000000000000000000000000000000000xxx\rxxxxxxx0000000000000000000000000000000000000000xxx\rxxxxxxxx0000000000000000000000000000000000000xxxxx\rxxxxxxxxx00000000000000000000000000000000000xxxxxx\rxxxxxxxxxx000000000000000000000000000000000xxxxxxx\rxxxxxxxxxxx00000000000000000000000000000000xxxx0xx\rxxxxxxxxxxxx0000000000000000000000000000000xxxxxxx\rxxxxxxxxxxxxx00000000000000000000000000000xxxxxxxx\rxxxxxxxxxxxxxx0000000000000000000000000000xxxxxxxx\rxxxxxxxxxxxxxxx00000000000000000000000000xxxxxxxxx\rxxxxxxxxxxxxxxxx0000000000000000000000000xxxxxxxxx\rxxxxxxxxxxxxxxxxx00000000000000000000000xxxxxxxxxx\rxxxxxxxxxxxxxxxxxx0000000000000000000000xxxxxxxxxx\rxxxxxxxxxxxxxxxxxxx00000000000000000000xxxxxxxxxxx\rxxxxxxxxxxxxxxxxxxxxxxx000000000000000xxxxxxxxxxxx\rxxxxxxxxxxxxxxxxxxxxxxxx0000000000000xxxxxxxxxxxxx\rxxxxxxxxxxxxxxxxxxxxxxxxx00000000000xxxxxxxxxxxxxx\rxxxxxxxxxxxxxxxxxxxxxxxxxx0000000xxxxxxxxxxxxxxxxx\rxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\rxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\rxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\rxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\r";
    


















































    GamefuseObject Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_iceblock;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 0;
    Item.X = 9;
    Item.Y = 14;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_ballpile;
    Item.baseItem.allowWalk = true;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 1;
    Item.X = 22;
    Item.Y = 27;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_ballpile;
    Item.baseItem.allowWalk = true;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 2;
    Item.X = 8;
    Item.Y = 20;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_ballpile;
    Item.baseItem.allowWalk = true;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 3;
    Item.X = 8;
    Item.Y = 26;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_iceblock;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 4;
    Item.X = 35;
    Item.Y = 30;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_iceblock;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 5;
    Item.X = 22;
    Item.Y = 17;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_iceblock;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 6;
    Item.X = 9;
    Item.Y = 17;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_iceblock;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 7;
    Item.X = 35;
    Item.Y = 24;
    Item.Rot = 4;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_ballpile;
    Item.baseItem.allowWalk = true;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 8;
    Item.X = 36;
    Item.Y = 18;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_ballpile;
    Item.baseItem.allowWalk = true;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 9;
    Item.X = 24;
    Item.Y = 21;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_iceblock;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 10;
    Item.X = 22;
    Item.Y = 25;
    Item.Rot = 4;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_iceblock;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 11;
    Item.X = 18;
    Item.Y = 21;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_ballpile;
    Item.baseItem.allowWalk = true;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 12;
    Item.X = 8;
    Item.Y = 23;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_ballpile;
    Item.baseItem.allowWalk = true;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 13;
    Item.X = 36;
    Item.Y = 27;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_iceblock;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 14;
    Item.X = 9;
    Item.Y = 20;
    Item.Rot = 6;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_ballpile;
    Item.baseItem.allowWalk = true;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 15;
    Item.X = 36;
    Item.Y = 21;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_ballpile;
    Item.baseItem.allowWalk = true;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 16;
    Item.X = 28;
    Item.Y = 21;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_iceblock;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 17;
    Item.X = 35;
    Item.Y = 15;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_iceblock;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 18;
    Item.X = 9;
    Item.Y = 29;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_iceblock;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 19;
    Item.X = 35;
    Item.Y = 18;
    Item.Rot = 6;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_iceblock;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 20;
    Item.X = 22;
    Item.Y = 21;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_iceblock;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 21;
    Item.X = 9;
    Item.Y = 23;
    Item.Rot = 4;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_ballpile;
    Item.baseItem.allowWalk = true;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 22;
    Item.X = 36;
    Item.Y = 30;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_ballpile;
    Item.baseItem.allowWalk = true;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 23;
    Item.X = 22;
    Item.Y = 23;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_ballpile;
    Item.baseItem.allowWalk = true;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 24;
    Item.X = 22;
    Item.Y = 19;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_ballpile;
    Item.baseItem.allowWalk = true;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 25;
    Item.X = 8;
    Item.Y = 14;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_ballpile;
    Item.baseItem.allowWalk = true;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 26;
    Item.X = 36;
    Item.Y = 15;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_iceblock;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 27;
    Item.X = 35;
    Item.Y = 27;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_iceblock;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 28;
    Item.X = 35;
    Item.Y = 21;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_iceblock;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 29;
    Item.X = 26;
    Item.Y = 21;
    Item.Rot = 6;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_ballpile;
    Item.baseItem.allowWalk = true;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 30;
    Item.X = 8;
    Item.Y = 17;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_ballpile;
    Item.baseItem.allowWalk = true;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 31;
    Item.X = 22;
    Item.Y = 15;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_iceblock;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 32;
    Item.X = 9;
    Item.Y = 26;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_ballpile;
    Item.baseItem.allowWalk = true;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 33;
    Item.X = 16;
    Item.Y = 21;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_ballpile;
    Item.baseItem.allowWalk = true;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 34;
    Item.X = 20;
    Item.Y = 21;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_ballpile;
    Item.baseItem.allowWalk = true;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 35;
    Item.X = 36;
    Item.Y = 24;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.ads_background;
    Item.baseItem.allowWalk = true;
    Item.baseItem.Height = 0.0F;
    Item.baseItem.itemExtraType = 1;
    Item.itemId = 36;
    Item.X = 0;
    Item.Y = 22;
    Item.Rot = 1;
    Item.Z = 0;
    Item.extraData = new MapStuffData("state=0\toffsetX=-1070\toffsetZ=9920\toffsetY=1520\timageUrl=http://dcr.lavvos.pl/lavvos/c_images/DEV_tests/snst_bg_2_big.png");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_ballpile;
    Item.baseItem.allowWalk = true;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 37;
    Item.X = 8;
    Item.Y = 29;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    this.spawnsBLUE.add(new SpawnPoint(10, 10));
    this.spawnsRED.add(new SpawnPoint(11, 11));
  }
  
  public void gameObjects(Map<Integer, GameItemObject> gameObjects, SnowWarRoom room)
  {
    gameObjects.put(Integer.valueOf(0), new PileGameObject(22, 27, 12, 12, 1, room.map, room));
    gameObjects.put(Integer.valueOf(1), new PileGameObject(8, 20, 12, 12, 2, room.map, room));
    gameObjects.put(Integer.valueOf(2), new PileGameObject(8, 26, 12, 12, 3, room.map, room));
    gameObjects.put(Integer.valueOf(3), new PileGameObject(36, 18, 12, 12, 8, room.map, room));
    gameObjects.put(Integer.valueOf(4), new PileGameObject(24, 21, 12, 12, 9, room.map, room));
    gameObjects.put(Integer.valueOf(5), new PileGameObject(8, 23, 12, 12, 12, room.map, room));
    gameObjects.put(Integer.valueOf(6), new PileGameObject(36, 27, 12, 12, 13, room.map, room));
    gameObjects.put(Integer.valueOf(7), new PileGameObject(36, 21, 12, 12, 15, room.map, room));
    gameObjects.put(Integer.valueOf(8), new PileGameObject(28, 21, 12, 12, 16, room.map, room));
    gameObjects.put(Integer.valueOf(9), new PileGameObject(36, 30, 12, 12, 22, room.map, room));
    gameObjects.put(Integer.valueOf(10), new PileGameObject(22, 23, 12, 12, 23, room.map, room));
    gameObjects.put(Integer.valueOf(11), new PileGameObject(22, 19, 12, 12, 24, room.map, room));
    gameObjects.put(Integer.valueOf(12), new PileGameObject(8, 14, 12, 12, 25, room.map, room));
    gameObjects.put(Integer.valueOf(13), new PileGameObject(36, 15, 12, 12, 26, room.map, room));
    gameObjects.put(Integer.valueOf(14), new PileGameObject(22, 15, 12, 12, 31, room.map, room));
    gameObjects.put(Integer.valueOf(15), new PileGameObject(16, 21, 12, 12, 33, room.map, room));
    gameObjects.put(Integer.valueOf(16), new PileGameObject(20, 21, 12, 12, 34, room.map, room));
    gameObjects.put(Integer.valueOf(17), new PileGameObject(36, 24, 12, 12, 35, room.map, room));
    gameObjects.put(Integer.valueOf(18), new PileGameObject(8, 29, 12, 12, 37, room.map, room));
  }
}


