package cappo.game.games.snowwar;

import cappo.game.collections.BaseItem;
import cappo.game.games.snowwar.gameobjects.GameItemObject;
import cappo.game.games.snowwar.gameobjects.MachineGameObject;
import cappo.game.games.snowwar.gameobjects.TreeGameObject;
import cappo.game.roomengine.entity.item.extradata.ExtraDataBase;
import cappo.game.roomengine.entity.item.extradata.MapStuffData;
import java.util.List;
import java.util.Map;

public class SnowWarArena8
  extends SnowWarArenaBase
{
  public SnowWarArena8()
  {
    this.ArenaType = 8;
    this.ArenaHeight = 50;
    this.ArenaWidth = 50;
    this.HeightMap = "xxxxxxxxxxxxxxxxxxx00000xxxxxxxxxxxxxxxxxxxxxxxxxx\rxxxxxxxxxxxxxxxxxx0000000xxxxxxxxxxxxxxxxxxxxxxxxx\rxxxxxxxxxxxxxxxxx000000000xxxxxxxxxxxxxxxxxxxxxxxx\rxxxxxxxxxxxxxxxx00000000000xxxxxxxxxxxxxxxxxxxxxxx\rxxxxxxxxxxxxxxx0000000000000xxxxxxxxxxxxxxxxxxxxxx\rxxxxxxxxxxxxxx000000000000000xxxxxxxxxxxxxxxxxxxxx\rxxxxxxxxxxxxx00000000000000000xxxxxxxxxxxxxxxxxxxx\rxxxxxxxxxxxx0000000000000000000xxxxxxxxxxxxxxxxxxx\rxxxxxxxxxxx000000000000000000000xxxxxxxxxxxxxxxxxx\rxxxxxxxxxx00000000000000000000000xxxxxxxxxxxxxxxxx\rxxxxxxxxx0000000000000000000000000xxxxxxxxxxxxxxxx\rxxxxxxxx000000000000000000000000000xxxxxxxxxxxxxxx\rxxxxxxx00000000000000000000000000000xxxxxxxxxxxxxx\rxxxxxx0000000000000000000000000000000xxxxxxxxxxxxx\rxxxxx000000000000000000000000000000000xxxxxxxxxxxx\rxxxx00000000000000000000000000000000000xxxxxxxxxxx\rxxx0000000000000000000000000000000000000xxxxxxxxxx\rxx000000000000000000000000000000000000000xxxxxxxxx\rx00000000000000000000000000000000000000000xxxxxxxx\r00000000000000000000xxxxx0xxxxxxx0000000000xxxxxxx\r00000000000000000000xxxxx0xxxxxxx00000000000xxxxxx\r00000000000000000000xxxxx0xxxxxxx000000000000xxxxx\r00000000000000000000xxx0000000xxx0000000000000xxxx\rx0000000000000000000xxx0000000xxx00000000000000xxx\rxx000000000000000000xxx0000000000000000000000000xx\rxxx00000000000000000xxx0000000xxx0000000000000000x\rxxxx00000000000000000000000000xxx00000000000000000\rxxxxx000000000000000xxx0000000xxx00000000000000000\rxxxxxx00000000000000xxxxxxx0xxxxx00000000000000000\rxxxxxxx0000000000000xxxxxxx0xxxxx00000000000000000\rxxxxxxxx000000000000xxxxxxx0xxxxx00000000000000000\rxxxxxxxxx00000000000000000000000000000000000000000\rxxxxxxxxxx000000000000000000000000000000000000000x\rxxxxxxxxxxx0000000000000000000000000000000000000xx\rxxxxxxxxxxxx00000000000000000000000000000000000xxx\rxxxxxxxxxxxxx000000000000000000000000000000000xxxx\rxxxxxxxxxxxxxx0000000000000000000000000000000xxxxx\rxxxxxxxxxxxxxxx00000000000000000000000000000xxxxxx\rxxxxxxxxxxxxxxxx000000000000000000000000000xxxxxxx\rxxxxxxxxxxxxxxxxx0000000000000000000000000xxxxxxxx\rxxxxxxxxxxxxxxxxxx00000000000000000000000xxxxxxxxx\rxxxxxxxxxxxxxxxxxxx000000000000000000000xxxxxxxxxx\rxxxxxxxxxxxxxxxxxxxx0000000000000000000xxxxxxxxxxx\rxxxxxxxxxxxxxxxxxxxxx00000000000000000xxxxxxxxxxxx\rxxxxxxxxxxxxxxxxxxxxxx000000000000000xxxxxxxxxxxxx\rxxxxxxxxxxxxxxxxxxxxxxx0000000000000xxxxxxxxxxxxxx\rxxxxxxxxxxxxxxxxxxxxxxxx00000000000xxxxxxxxxxxxxxx\rxxxxxxxxxxxxxxxxxxxxxxxxx000000000xxxxxxxxxxxxxxxx\rxxxxxxxxxxxxxxxxxxxxxxxxxx0000000xxxxxxxxxxxxxxxxx\rxxxxxxxxxxxxxxxxxxxxxxxxxxx00000xxxxxxxxxxxxxxxxxx\r";
    


















































    GamefuseObject Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 0;
    Item.X = 41;
    Item.Y = 37;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 1;
    Item.X = 2;
    Item.Y = 20;
    Item.Rot = 0;
    Item.Z = 1440;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 2;
    Item.X = 10;
    Item.Y = 18;
    Item.Rot = 6;
    Item.Z = 1440;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 3;
    Item.X = 19;
    Item.Y = 41;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 4;
    Item.X = 41;
    Item.Y = 20;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 5;
    Item.X = 9;
    Item.Y = 31;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 6;
    Item.X = 23;
    Item.Y = 44;
    Item.Rot = 0;
    Item.Z = 1440;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 7;
    Item.X = 4;
    Item.Y = 18;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 8;
    Item.X = 2;
    Item.Y = 20;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 9;
    Item.X = 25;
    Item.Y = 38;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 10;
    Item.X = 2;
    Item.Y = 19;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 11;
    Item.X = 9;
    Item.Y = 18;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 12;
    Item.X = 7;
    Item.Y = 18;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_fence;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 13;
    Item.X = 17;
    Item.Y = 14;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("2");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 14;
    Item.X = 2;
    Item.Y = 18;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 15;
    Item.X = 49;
    Item.Y = 28;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 16;
    Item.X = 2;
    Item.Y = 22;
    Item.Rot = 6;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 17;
    Item.X = 5;
    Item.Y = 18;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 18;
    Item.X = 4;
    Item.Y = 18;
    Item.Rot = 0;
    Item.Z = 1440;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 19;
    Item.X = 39;
    Item.Y = 22;
    Item.Rot = 2;
    Item.Z = 1440;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 20;
    Item.X = 39;
    Item.Y = 23;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_fence;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 21;
    Item.X = 24;
    Item.Y = 44;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 22;
    Item.X = 36;
    Item.Y = 15;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 23;
    Item.X = 23;
    Item.Y = 44;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 24;
    Item.X = 47;
    Item.Y = 32;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 25;
    Item.X = 39;
    Item.Y = 37;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 26;
    Item.X = 8;
    Item.Y = 18;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 27;
    Item.X = 2;
    Item.Y = 19;
    Item.Rot = 0;
    Item.Z = 1440;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 28;
    Item.X = 6;
    Item.Y = 20;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 29;
    Item.X = 2;
    Item.Y = 18;
    Item.Rot = 0;
    Item.Z = 1440;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_fence;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 30;
    Item.X = 15;
    Item.Y = 14;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 31;
    Item.X = 26;
    Item.Y = 6;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 32;
    Item.X = 39;
    Item.Y = 23;
    Item.Rot = 4;
    Item.Z = 1440;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 33;
    Item.X = 23;
    Item.Y = 38;
    Item.Rot = 2;
    Item.Z = 1440;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 34;
    Item.X = 10;
    Item.Y = 26;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 35;
    Item.X = 23;
    Item.Y = 45;
    Item.Rot = 4;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 36;
    Item.X = 39;
    Item.Y = 21;
    Item.Rot = 0;
    Item.Z = 1440;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 37;
    Item.X = 5;
    Item.Y = 18;
    Item.Rot = 0;
    Item.Z = 1440;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 38;
    Item.X = 39;
    Item.Y = 24;
    Item.Rot = 6;
    Item.Z = 1440;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 39;
    Item.X = 13;
    Item.Y = 15;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 40;
    Item.X = 3;
    Item.Y = 18;
    Item.Rot = 0;
    Item.Z = 1440;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_fence;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 41;
    Item.X = 13;
    Item.Y = 14;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("1");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 42;
    Item.X = 30;
    Item.Y = 7;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 43;
    Item.X = 40;
    Item.Y = 20;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_fence;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 44;
    Item.X = 29;
    Item.Y = 7;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("1");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_fence;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 45;
    Item.X = 21;
    Item.Y = 14;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("2");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_fence;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 46;
    Item.X = 24;
    Item.Y = 40;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 47;
    Item.X = 23;
    Item.Y = 40;
    Item.Rot = 0;
    Item.Z = 1440;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 48;
    Item.X = 15;
    Item.Y = 10;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 49;
    Item.X = 37;
    Item.Y = 37;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 50;
    Item.X = 20;
    Item.Y = 4;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 51;
    Item.X = 3;
    Item.Y = 18;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 52;
    Item.X = 23;
    Item.Y = 40;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 53;
    Item.X = 2;
    Item.Y = 22;
    Item.Rot = 2;
    Item.Z = 1440;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 54;
    Item.X = 43;
    Item.Y = 20;
    Item.Rot = 6;
    Item.Z = 1440;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 55;
    Item.X = 23;
    Item.Y = 39;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 56;
    Item.X = 45;
    Item.Y = 25;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 57;
    Item.X = 23;
    Item.Y = 42;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 58;
    Item.X = 42;
    Item.Y = 20;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 59;
    Item.X = 9;
    Item.Y = 18;
    Item.Rot = 0;
    Item.Z = 1440;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 60;
    Item.X = 10;
    Item.Y = 18;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_fence;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 61;
    Item.X = 24;
    Item.Y = 38;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("1");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 62;
    Item.X = 11;
    Item.Y = 18;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 63;
    Item.X = 39;
    Item.Y = 37;
    Item.Rot = 0;
    Item.Z = 1440;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 64;
    Item.X = 8;
    Item.Y = 18;
    Item.Rot = 0;
    Item.Z = 1440;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 65;
    Item.X = 39;
    Item.Y = 20;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 66;
    Item.X = 38;
    Item.Y = 37;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 67;
    Item.X = 40;
    Item.Y = 37;
    Item.Rot = 0;
    Item.Z = 1440;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 68;
    Item.X = 2;
    Item.Y = 21;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_fence;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 69;
    Item.X = 24;
    Item.Y = 42;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("2");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 70;
    Item.X = 39;
    Item.Y = 22;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 71;
    Item.X = 38;
    Item.Y = 37;
    Item.Rot = 0;
    Item.Z = 1440;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 72;
    Item.X = 40;
    Item.Y = 20;
    Item.Rot = 0;
    Item.Z = 1440;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 73;
    Item.X = 28;
    Item.Y = 47;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 74;
    Item.X = 6;
    Item.Y = 18;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.s_snowball_machine;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 75;
    Item.X = 26;
    Item.Y = 24;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 76;
    Item.X = 5;
    Item.Y = 24;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 77;
    Item.X = 41;
    Item.Y = 20;
    Item.Rot = 4;
    Item.Z = 1440;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_fence;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 78;
    Item.X = 19;
    Item.Y = 14;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.ads_background;
    Item.baseItem.allowWalk = true;
    Item.baseItem.Height = 0.0F;
    Item.baseItem.itemExtraType = 1;
    Item.itemId = 79;
    Item.X = 0;
    Item.Y = 19;
    Item.Rot = 1;
    Item.Z = 0;
    Item.extraData = new MapStuffData("state=0\toffsetX=-1166\toffsetZ=10000\toffsetY=1542\timageUrl=http://dcr.lavvos.pl/lavvos/c_images/DEV_tests/snst_bg_1_a_big.png");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 80;
    Item.X = 20;
    Item.Y = 8;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 81;
    Item.X = 2;
    Item.Y = 21;
    Item.Rot = 0;
    Item.Z = 1440;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 82;
    Item.X = 23;
    Item.Y = 38;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 83;
    Item.X = 23;
    Item.Y = 42;
    Item.Rot = 0;
    Item.Z = 1440;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 84;
    Item.X = 42;
    Item.Y = 20;
    Item.Rot = 0;
    Item.Z = 1440;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 85;
    Item.X = 39;
    Item.Y = 21;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 86;
    Item.X = 12;
    Item.Y = 18;
    Item.Rot = 4;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 87;
    Item.X = 11;
    Item.Y = 18;
    Item.Rot = 0;
    Item.Z = 1440;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 88;
    Item.X = 40;
    Item.Y = 37;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 89;
    Item.X = 23;
    Item.Y = 41;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 90;
    Item.X = 43;
    Item.Y = 20;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 91;
    Item.X = 39;
    Item.Y = 20;
    Item.Rot = 0;
    Item.Z = 1440;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 92;
    Item.X = 15;
    Item.Y = 34;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 93;
    Item.X = 6;
    Item.Y = 18;
    Item.Rot = 0;
    Item.Z = 1440;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 94;
    Item.X = 7;
    Item.Y = 18;
    Item.Rot = 0;
    Item.Z = 1440;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_fence;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 95;
    Item.X = 29;
    Item.Y = 9;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("2");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 96;
    Item.X = 39;
    Item.Y = 24;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 97;
    Item.X = 23;
    Item.Y = 43;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    

    Item = new GamefuseObject();
    Item.baseItem = BaseItem.ads_igorraygun;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 0.1F;
    Item.itemId = 98;
    Item.X = 28;
    Item.Y = 12;
    Item.Rot = 4;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.ads_igorraygun;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 0.1F;
    Item.itemId = 99;
    Item.X = 41;
    Item.Y = 33;
    Item.Rot = 6;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.ads_igorraygun;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 0.1F;
    Item.itemId = 100;
    Item.X = 31;
    Item.Y = 41;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.ads_igorraygun;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 0.1F;
    Item.itemId = 101;
    Item.X = 17;
    Item.Y = 37;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.ads_igorraygun;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 0.1F;
    Item.itemId = 102;
    Item.X = 11;
    Item.Y = 21;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    

    this.spawnsBLUE.add(new SpawnPoint(22, 9));
    this.spawnsBLUE.add(new SpawnPoint(25, 12));
    this.spawnsBLUE.add(new SpawnPoint(26, 8));
    this.spawnsBLUE.add(new SpawnPoint(31, 14));
    this.spawnsBLUE.add(new SpawnPoint(23, 13));
    
    this.spawnsRED.add(new SpawnPoint(30, 43));
    this.spawnsRED.add(new SpawnPoint(33, 42));
    this.spawnsRED.add(new SpawnPoint(38, 41));
    this.spawnsRED.add(new SpawnPoint(26, 42));
    this.spawnsRED.add(new SpawnPoint(33, 46));
  }
  
  public void gameObjects(Map<Integer, GameItemObject> gameObjects, SnowWarRoom room)
  {
    gameObjects.put(Integer.valueOf(0), new TreeGameObject(19, 41, 0, 1, 3, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(1), new TreeGameObject(9, 31, 0, 1, 5, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(2), new TreeGameObject(25, 38, 0, 1, 9, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(3), new TreeGameObject(49, 28, 0, 1, 15, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(4), new TreeGameObject(36, 15, 0, 1, 22, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(5), new TreeGameObject(47, 32, 0, 1, 24, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(6), new TreeGameObject(6, 20, 0, 1, 28, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(7), new TreeGameObject(26, 6, 0, 1, 31, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(8), new TreeGameObject(10, 26, 0, 1, 34, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(9), new TreeGameObject(13, 15, 0, 1, 39, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(10), new TreeGameObject(30, 7, 0, 1, 42, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(11), new TreeGameObject(15, 10, 0, 1, 48, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(12), new TreeGameObject(20, 4, 0, 1, 50, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(13), new TreeGameObject(45, 25, 0, 1, 56, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(14), new TreeGameObject(28, 47, 0, 1, 73, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(15), new MachineGameObject(26, 24, 0, 5, 0, 75, room.map, room));
    gameObjects.put(Integer.valueOf(16), new TreeGameObject(5, 24, 0, 1, 76, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(17), new TreeGameObject(20, 8, 0, 1, 80, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(18), new TreeGameObject(15, 34, 0, 1, 92, 3, 0, room.map, room));
  }
}


