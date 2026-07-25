package cappo.game.games.snowwar;

import cappo.game.collections.BaseItem;
import cappo.game.games.snowwar.gameobjects.GameItemObject;
import cappo.game.games.snowwar.gameobjects.PileGameObject;
import cappo.game.games.snowwar.gameobjects.TreeGameObject;
import cappo.game.roomengine.entity.item.extradata.ExtraDataBase;
import cappo.game.roomengine.entity.item.extradata.MapStuffData;
import java.util.List;
import java.util.Map;

public class SnowWarArena11
  extends SnowWarArenaBase
{
  public SnowWarArena11()
  {
    this.ArenaType = 11;
    this.ArenaWidth = 50;
    this.ArenaHeight = 50;
    this.HeightMap = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\rxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\rxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\rxxxxxxxxxxxxxxxxxx00000000xxxxxxxxxxxxxxxxxxxxxxxx\rxxxxxxxxxxxxxxxxx00000000000xxxxxxxxxxxxxxxxxxxxxx\rxxxxxxxxxxxxxx000000000000000xxxxxxxxxxxxxxxxxxxxx\rxxxxxxxxxxxxx00000000000000000xxxxxxxxxxxxxxxxxxxx\rxxxxxxxxxxxx0000000000000000000xxxxxxxxxxxxxxxxxxx\rxxxxxxxxxxx000000000000000000000xxxxxxxxxxxxxxxxxx\rxxxxxxxxxx00000000000000000000000xxxxxxxxxxxxxxxxx\rxxxxxxxxx0000000000000000000000000xxxxxxxxxxxxxxxx\rxxxxxxxx000000000000000000000000000xxxxxxxxxxxxxxx\rxxxxxxx00000000000000000000000000000xxxxxxxxxxxxxx\rxxxxxx0000000000000000000000000000000xxxxxxxxxxxxx\rxxxxx000000000000000000000000000000000xxxxxxxxxxxx\rxxxxx0000000000000000000000000000000000xxxxxxxxxxx\rxxxxx00000000000000000000000000000000000xxxxxxxxxx\rxxxxx000000000000000000000000000000000000xxxxxxxxx\rxxxx00000000000000000000000000000000000000xxxxxxxx\rxxxx000000000000000000000000000000000000000xxxxxxx\rxxxx0000000000000000000000000000000000000000xxxxxx\rxxxx00000000000000000000000000000000000000000xxxxx\r0xxx000000000000000000000000000000000000000000xxxx\rxxxx000000000000000000000000000000000000000000xxxx\rxxxx0000000000000000000000000000000000000000000xxx\rxxxx0000000000000000000000000000000000000000000xxx\rxxxx0000000000000000000000000000000000000000000xxx\rxxxxx000000000000000000000000000000000000000000xxx\rxxxxxx00000000000000000000000000000000000000000xxx\rxxxxxxx0000000000000000000000000000000000000000xxx\rxxxxxxxx000000000000000000000000000000000000000xxx\rxxxxxxxxx0000000000000000000000000000000000000xxxx\rxxxxxxxxxx000000000000000000000000000000000000xxxx\rxxxxxxxxxxx0000000000000000000000000000000000xxxxx\rxxxxxxxxxxxx00000000000000000000000000000000xxxxxx\rxxxxxxxxxxxxx000000000000000000000000000000xxxxxxx\rxxxxxxxxxxxxxx0000000000000000000000000000xxxxxxxx\rxxxxxxxxxxxxxxx00000000000000000000000000xxxxxxxxx\rxxxxxxxxxxxxxxxx0000000000000000000000000xxxxxxxxx\rxxxxxxxxxxxxxxxxx00000000000000000000000xxxxxxxxxx\rxxxxxxxxxxxxxxxxxx0000000000000000000000xxxxxxxxxx\rxxxxxxxxxxxxxxxxxxx00000000000000000000xxxxxxxxxxx\rxxxxxxxxxxxxxxxxxxxx000000000000000000xxxxxxxxxxxx\rxxxxxxxxxxxxxxxxxxxxx0000000000000000xxxxxxxxxxxxx\rxxxxxxxxxxxxxxxxxxxxxxx0000000000000xxxxxxxxxxxxxx\rxxxxxxxxxxxxxxxxxxxxxxxx000000000xxxxxxxxxxxxxxxxx\rxxxxxxxxxxxxxxxxxxxxxxxxxx000000xxxxxxxxxxxxxxxxxx\rxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\rxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\rxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\r";
    


















































    GamefuseObject Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 0;
    Item.X = 29;
    Item.Y = 23;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 1;
    Item.X = 11;
    Item.Y = 16;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 2;
    Item.X = 26;
    Item.Y = 28;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 3;
    Item.X = 31;
    Item.Y = 42;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_ballpile;
    Item.baseItem.allowWalk = true;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 4;
    Item.X = 27;
    Item.Y = 16;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 5;
    Item.X = 32;
    Item.Y = 10;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("1");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 6;
    Item.X = 13;
    Item.Y = 21;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 7;
    Item.X = 33;
    Item.Y = 14;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_ballpile;
    Item.baseItem.allowWalk = true;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 8;
    Item.X = 18;
    Item.Y = 14;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 9;
    Item.X = 15;
    Item.Y = 36;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("1");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 10;
    Item.X = 34;
    Item.Y = 17;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("2");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 11;
    Item.X = 17;
    Item.Y = 33;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("2");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 12;
    Item.X = 27;
    Item.Y = 17;
    Item.Rot = 4;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_ballpile;
    Item.baseItem.allowWalk = true;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 13;
    Item.X = 31;
    Item.Y = 12;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 14;
    Item.X = 34;
    Item.Y = 42;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("2");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 15;
    Item.X = 41;
    Item.Y = 31;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 16;
    Item.X = 9;
    Item.Y = 20;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 17;
    Item.X = 30;
    Item.Y = 39;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("1");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 18;
    Item.X = 21;
    Item.Y = 27;
    Item.Rot = 6;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_ballpile;
    Item.baseItem.allowWalk = true;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 19;
    Item.X = 27;
    Item.Y = 12;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 20;
    Item.X = 22;
    Item.Y = 28;
    Item.Rot = 4;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 21;
    Item.X = 13;
    Item.Y = 31;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 22;
    Item.X = 10;
    Item.Y = 30;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_ballpile;
    Item.baseItem.allowWalk = true;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 23;
    Item.X = 22;
    Item.Y = 35;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_ballpile;
    Item.baseItem.allowWalk = true;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 24;
    Item.X = 21;
    Item.Y = 28;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.xm09_man_a;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 25;
    Item.X = 14;
    Item.Y = 9;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 26;
    Item.X = 40;
    Item.Y = 34;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("1");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 27;
    Item.X = 42;
    Item.Y = 21;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_ballpile;
    Item.baseItem.allowWalk = true;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 28;
    Item.X = 27;
    Item.Y = 32;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 29;
    Item.X = 25;
    Item.Y = 20;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("2");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 30;
    Item.X = 36;
    Item.Y = 14;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 31;
    Item.X = 11;
    Item.Y = 26;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 32;
    Item.X = 39;
    Item.Y = 36;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("1");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 33;
    Item.X = 25;
    Item.Y = 24;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_ballpile;
    Item.baseItem.allowWalk = true;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 34;
    Item.X = 22;
    Item.Y = 13;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 35;
    Item.X = 13;
    Item.Y = 13;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_fence;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 36;
    Item.X = 19;
    Item.Y = 22;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 37;
    Item.X = 28;
    Item.Y = 9;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("1");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_fence;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 38;
    Item.X = 34;
    Item.Y = 26;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("1");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 39;
    Item.X = 19;
    Item.Y = 11;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("1");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_ballpile;
    Item.baseItem.allowWalk = true;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 40;
    Item.X = 32;
    Item.Y = 20;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 41;
    Item.X = 29;
    Item.Y = 44;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 42;
    Item.X = 43;
    Item.Y = 27;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 43;
    Item.X = 19;
    Item.Y = 36;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 44;
    Item.X = 29;
    Item.Y = 26;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("1");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.xm09_man_c;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 45;
    Item.X = 14;
    Item.Y = 9;
    Item.Rot = 4;
    Item.Z = 2480;
    Item.extraData.setExtraData("6");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.ads_background;
    Item.baseItem.allowWalk = true;
    Item.baseItem.Height = 0.0F;
    Item.baseItem.itemExtraType = 1;
    Item.itemId = 46;
    Item.X = 0;
    Item.Y = 22;
    Item.Rot = 1;
    Item.Z = 0;
    Item.extraData = new MapStuffData("state=0\toffsetX=-1119\toffsetZ=9950\toffsetY=390\timageUrl=http://dcr.lavvos.pl/lavvos/c_images/DEV_tests/snst_bg_3_noscale.png");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 47;
    Item.X = 14;
    Item.Y = 17;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 48;
    Item.X = 24;
    Item.Y = 9;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 49;
    Item.X = 22;
    Item.Y = 20;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_fence;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 50;
    Item.X = 17;
    Item.Y = 22;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("1");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 51;
    Item.X = 26;
    Item.Y = 16;
    Item.Rot = 6;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_ballpile;
    Item.baseItem.allowWalk = true;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 52;
    Item.X = 17;
    Item.Y = 29;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 53;
    Item.X = 22;
    Item.Y = 27;
    Item.Rot = 6;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 54;
    Item.X = 37;
    Item.Y = 40;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 55;
    Item.X = 24;
    Item.Y = 42;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 56;
    Item.X = 17;
    Item.Y = 10;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_ballpile;
    Item.baseItem.allowWalk = true;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 57;
    Item.X = 14;
    Item.Y = 24;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 58;
    Item.X = 20;
    Item.Y = 40;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 59;
    Item.X = 20;
    Item.Y = 8;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_ballpile;
    Item.baseItem.allowWalk = true;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 60;
    Item.X = 17;
    Item.Y = 35;
    Item.Rot = 0;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_block1;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 61;
    Item.X = 26;
    Item.Y = 17;
    Item.Rot = 4;
    Item.Z = 0;
    Item.extraData.setExtraData("");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_tree1_d;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 1.0F;
    Item.itemId = 62;
    Item.X = 22;
    Item.Y = 24;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("0");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.snst_fence;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 63;
    Item.X = 30;
    Item.Y = 26;
    Item.Rot = 2;
    Item.Z = 0;
    Item.extraData.setExtraData("1");
    this.fuseObjects.add(Item);
    
    Item = new GamefuseObject();
    Item.baseItem = BaseItem.xm09_man_b;
    Item.baseItem.allowWalk = false;
    Item.baseItem.Height = 0.0F;
    Item.itemId = 64;
    Item.X = 14;
    Item.Y = 9;
    Item.Rot = 4;
    Item.Z = 1280;
    Item.extraData.setExtraData("5");
    this.fuseObjects.add(Item);
    
    this.spawnsBLUE.add(new SpawnPoint(10, 10));
    this.spawnsRED.add(new SpawnPoint(11, 11));
  }
  
  public void gameObjects(Map<Integer, GameItemObject> gameObjects, SnowWarRoom room)
  {
    gameObjects.put(Integer.valueOf(0), new TreeGameObject(29, 23, 0, 1, 0, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(1), new TreeGameObject(11, 16, 2, 1, 1, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(2), new TreeGameObject(26, 28, 2, 1, 2, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(3), new TreeGameObject(31, 42, 2, 1, 3, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(4), new PileGameObject(27, 16, 12, 12, 4, room.map, room));
    gameObjects.put(Integer.valueOf(5), new TreeGameObject(32, 10, 0, 1, 5, 3, 1, room.map, room));
    gameObjects.put(Integer.valueOf(6), new TreeGameObject(13, 21, 2, 1, 6, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(7), new TreeGameObject(33, 14, 0, 1, 7, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(8), new PileGameObject(18, 14, 12, 12, 8, room.map, room));
    gameObjects.put(Integer.valueOf(9), new TreeGameObject(15, 36, 2, 1, 9, 3, 1, room.map, room));
    gameObjects.put(Integer.valueOf(10), new TreeGameObject(34, 17, 0, 1, 10, 3, 2, room.map, room));
    gameObjects.put(Integer.valueOf(11), new TreeGameObject(17, 33, 2, 1, 11, 3, 2, room.map, room));
    gameObjects.put(Integer.valueOf(12), new PileGameObject(31, 12, 12, 12, 13, room.map, room));
    gameObjects.put(Integer.valueOf(13), new TreeGameObject(34, 42, 2, 1, 14, 3, 2, room.map, room));
    gameObjects.put(Integer.valueOf(14), new TreeGameObject(41, 31, 0, 1, 15, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(15), new TreeGameObject(9, 20, 2, 1, 16, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(16), new TreeGameObject(30, 39, 0, 1, 17, 3, 1, room.map, room));
    gameObjects.put(Integer.valueOf(17), new PileGameObject(27, 12, 12, 12, 19, room.map, room));
    gameObjects.put(Integer.valueOf(18), new TreeGameObject(13, 31, 2, 1, 21, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(19), new TreeGameObject(10, 30, 2, 1, 22, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(20), new PileGameObject(22, 35, 12, 12, 23, room.map, room));
    gameObjects.put(Integer.valueOf(21), new PileGameObject(21, 28, 12, 12, 24, room.map, room));
    gameObjects.put(Integer.valueOf(22), new TreeGameObject(40, 34, 0, 1, 26, 3, 1, room.map, room));
    gameObjects.put(Integer.valueOf(23), new TreeGameObject(42, 21, 0, 1, 27, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(24), new PileGameObject(27, 32, 12, 12, 28, room.map, room));
    gameObjects.put(Integer.valueOf(25), new TreeGameObject(25, 20, 0, 1, 29, 3, 2, room.map, room));
    gameObjects.put(Integer.valueOf(26), new TreeGameObject(36, 15, 0, 1, 30, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(27), new TreeGameObject(11, 26, 2, 1, 31, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(28), new TreeGameObject(39, 36, 0, 1, 32, 3, 1, room.map, room));
    gameObjects.put(Integer.valueOf(29), new TreeGameObject(25, 24, 0, 1, 33, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(30), new PileGameObject(22, 13, 12, 12, 34, room.map, room));
    gameObjects.put(Integer.valueOf(31), new TreeGameObject(13, 13, 0, 1, 35, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(32), new TreeGameObject(28, 9, 0, 1, 37, 3, 1, room.map, room));
    gameObjects.put(Integer.valueOf(33), new TreeGameObject(19, 11, 0, 1, 39, 3, 1, room.map, room));
    gameObjects.put(Integer.valueOf(34), new PileGameObject(32, 20, 12, 12, 40, room.map, room));
    gameObjects.put(Integer.valueOf(35), new TreeGameObject(29, 44, 2, 1, 41, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(36), new TreeGameObject(43, 27, 0, 1, 42, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(37), new TreeGameObject(19, 36, 2, 1, 43, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(38), new TreeGameObject(29, 26, 0, 1, 44, 3, 1, room.map, room));
    gameObjects.put(Integer.valueOf(39), new TreeGameObject(14, 17, 2, 1, 47, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(40), new TreeGameObject(24, 9, 0, 1, 48, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(41), new TreeGameObject(23, 20, 0, 1, 49, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(42), new PileGameObject(17, 29, 12, 12, 52, room.map, room));
    gameObjects.put(Integer.valueOf(43), new TreeGameObject(37, 40, 2, 1, 54, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(44), new TreeGameObject(24, 42, 2, 1, 55, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(45), new TreeGameObject(17, 10, 0, 1, 56, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(46), new PileGameObject(14, 24, 12, 12, 57, room.map, room));
    gameObjects.put(Integer.valueOf(47), new TreeGameObject(20, 40, 2, 1, 58, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(48), new TreeGameObject(20, 8, 0, 1, 59, 3, 0, room.map, room));
    gameObjects.put(Integer.valueOf(49), new PileGameObject(17, 35, 12, 12, 60, room.map, room));
    gameObjects.put(Integer.valueOf(50), new TreeGameObject(22, 24, 2, 1, 62, 3, 0, room.map, room));
  }
}


