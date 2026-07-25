package cappo.game.roomengine.itemInteractor;

import cappo.engine.player.Connection;
import cappo.engine.threadpools.RoomTask;
import cappo.game.roomengine.entity.item.floor.FloorItem;
import cappo.game.roomengine.entity.item.floor.GenericFloorItem;
import cappo.game.roomengine.entity.item.wall.GenericWallItem;

public abstract class Interactor
{
  public static final Interactor iterWired = new InteractorWiredFurnis();
  public static final Interactor iterDefault = new InteractorSimple();
  public static final Interactor iterOutfit = new InteractorOutfit();
  public static final Interactor iterTeleport = new InteractorTeleport();
  public static final Interactor iterVendingMachine = new InteractorVendingMachine();
  public static final Interactor iterOneWayGate = new InteractorOneWayGate();
  public static final Interactor iterDice = new InteractorDice();
  public static final Interactor iterHabboWheel = new InteractorHabboWheel();
  public static final Interactor iterTimer = new InteractorTimer();
  public static final Interactor iterJukebox = new InteractorTrax();
  public abstract void OnPlace(RoomTask paramRoomTask, Connection paramConnection, GenericFloorItem paramGenericFloorItem);
  
  public abstract void OnPickUp(RoomTask paramRoomTask, Connection paramConnection, GenericFloorItem paramGenericFloorItem);
  
  public abstract void OnTriggerFloor(RoomTask paramRoomTask, Connection paramConnection, FloorItem paramFloorItem, int paramInt, boolean paramBoolean);
  
  public abstract void OnTriggerWall(RoomTask paramRoomTask, Connection paramConnection, GenericWallItem paramGenericWallItem, int paramInt, boolean paramBoolean);
  
  public static enum InteractorType
  {
    none,  gift,  postit,  walkeablechange,  roomeffect,  ecotron_box,  bed,  scoreboard,  vendingmachine,  alert,  onewaygate,  loveshuffler,  habbowheel,  dice,  bottle,  teleport,  rentals,  pet,  pool,  roller,  iceskates,  normslaskates,  lowpool,  haloweenpool,  football,  fbgate,  footballcountergreen,  footballcounteryellow,  footballcounterblue,  footballcounterred,  banzaigateblue,  banzaigatered,  banzaigateyellow,  banzaigategreen,  banzaifloor,  banzaiscoreblue,  banzaiscorered,  banzaiscoreyellow,  banzaiscoregreen,  banzaicounter,  banzaitele,  banzaipuck,  banzaipyramid,  freezetimer,  freezeexit,  freezeredcounter,  freezebluecounter,  freezeyellowcounter,  freezegreencounter,  freezeyellowgate,  freezeredgate,  freezegreengate,  freezebluegate,  freezetileblock,  freezetile,  jukebox,  puzzlebox,  triggertimer,  triggerroomenter,  triggergameend,  triggergamestart,  triggerrepeater,  triggeronusersay,  triggerscoreachieved,  triggerstatechanged,  triggerwalkonfurni,  triggerwalkofffurni,  actiongivescore,  actionposreset,  actionmoverotate,  actionresettimer,  actionshowmessage,  actionteleportto,  actiontogglestate,  actiongivereward,  conditionfurnishaveusers,  conditionstatepos,  conditiontimelessthan,  conditiontimemorethan,  conditiontriggeronfurni,  arrowplate,  preassureplate,  ringplate,  colortile,  colorwheel,  floorswitch1,  floorswitch2,  firegate,  glassfoor,  specialrandom,  specialunseen,  wire,  wireCenter,  wireCorner,  wireSplitter,  wireStandard;
  }
  
  public static InteractorType GetInteractorType(String type)
  {
    switch (type)
    {
/*    :    */     case "triggerwalkonfurni": return InteractorType.triggerwalkonfurni;
/*    :    */     case "banzaifloor": return InteractorType.banzaifloor;
/*    :    */     case "actionmoverotate": return InteractorType.actionmoverotate;
/*    :    */     case "preassureplate": return InteractorType.preassureplate;
/*    :    */     case "freezegreencounter": return InteractorType.freezegreencounter;
/*    :    */     case "vendingmachine": return InteractorType.vendingmachine;
/*    :    */     case "conditiontimelessthan": return InteractorType.conditiontimelessthan;
/*    :    */     case "freezeredcounter": return InteractorType.freezeredcounter;
/*    :    */     case "arrowplate": return InteractorType.arrowplate;
/*    :    */     case "triggergameend": return InteractorType.triggergameend;
/*    :    */     case "banzaicounter": return InteractorType.banzaicounter;
/*    :    */     case "iceskates": return InteractorType.iceskates;
/*    :    */     case "freezetileblock": return InteractorType.freezetileblock;
/*    :    */     case "footballcountered": return InteractorType.footballcounterred;
/*    :    */     case "freezeyellowcounter": return InteractorType.freezeyellowcounter;
/*    :    */     case "bottle": return InteractorType.bottle;
/*    :    */     case "teleport": return InteractorType.teleport;
/*    :    */     case "triggergamestart": return InteractorType.triggergamestart;
/*    :    */     case "fbgate": return InteractorType.fbgate;
/*    :    */     case "jukebox": return InteractorType.jukebox;
/*    :    */     case "actiontogglestate": return InteractorType.actiontogglestate;
/*    :    */     case "banzaiscoreyellow": return InteractorType.banzaiscoreyellow;
/*    :    */     case "haloweenpool": return InteractorType.haloweenpool;
/*    :    */     case "postit": return InteractorType.postit;
/*    :    */     case "footballcounterblue": return InteractorType.footballcounterblue;
/*    :    */     case "freezebluegate": return InteractorType.freezebluegate;
/*    :    */     case "roller": return InteractorType.roller;
/*    :    */     case "actionshowmessage": return InteractorType.actionshowmessage;
/*    :    */     case "loveshuffler": return InteractorType.loveshuffler;
/*    :    */     case "firegate": return InteractorType.firegate;
/*    :    */     case "triggerstatechanged": return InteractorType.triggerstatechanged;
/*    :    */     case "onewaygate": return InteractorType.onewaygate;
/*    :    */     case "freezebluecounter": return InteractorType.freezebluecounter;
/*    :    */     case "triggerwalkofffurni": return InteractorType.triggerwalkofffurni;
/*    :    */     case "ecotron_box": return InteractorType.ecotron_box;
/*    :    */     case "triggerscoreachieved": return InteractorType.triggerscoreachieved;
/*    :    */     case "floorswitch1": return InteractorType.floorswitch1;
/*    :    */     case "floorswitch2": return InteractorType.floorswitch2;
/*    :    */     case "wireCenter": return InteractorType.wireCenter;
/*    :    */     case "wireCorner": return InteractorType.wireCorner;
/*    :    */     case "specialrandom": return InteractorType.specialrandom;
/*    :    */     case "bed": return InteractorType.bed;
/*    :    */     case "dice": return InteractorType.dice;
/*    :    */     case "gift": return InteractorType.gift;
/*    :    */     case "pool": return InteractorType.pool;
/*    :    */     case "wire": return InteractorType.wire;
/*    :    */     case "specialunseen": return InteractorType.specialunseen;
/*    :    */     case "alert": return InteractorType.alert;
/*    :    */     case "conditiontimemorethan": return InteractorType.conditiontimemorethan;
/*    :    */     case "freezeexit": return InteractorType.freezeexit;
/*    :    */     case "freezetile": return InteractorType.freezetile;
/*    :    */     case "ringplate": return InteractorType.ringplate;
/*    :    */     case "lowpool": return InteractorType.lowpool;
/*    :    */     case "football": return InteractorType.football;
/*    :    */     case "triggertimer": return InteractorType.triggertimer;
/*    :    */     case "conditionfurnishaveusers": return InteractorType.conditionfurnishaveusers;
/*    :    */     case "banzaigateblue": return InteractorType.banzaigateblue;
/*    :    */     case "conditionstatepos": return InteractorType.conditionstatepos;
/*    :    */     case "footballcountergreen": return InteractorType.footballcountergreen;
/*    :    */     case "actionposreset": return InteractorType.actionposreset;
/*    :    */     case "conditiontriggeronfurni": return InteractorType.conditiontriggeronfurni;
/*    :    */     case "habbowheel": return InteractorType.habbowheel;
/*    :    */     case "triggerroomenter": return InteractorType.triggerroomenter;
/*    :    */     case "banzaipuck": return InteractorType.banzaipuck;
/*    :    */     case "banzaitele": return InteractorType.banzaitele;
/*    :    */     case "banzaiscoreblue": return InteractorType.banzaiscoreblue;
/*    :    */     case "normalskates": return InteractorType.normslaskates;
/*    :    */     case "wireStandard": return InteractorType.wireStandard;
/*    :    */     case "colorwheel": return InteractorType.colorwheel;
/*    :    */     case "glassfoor": return InteractorType.glassfoor;
/*    :    */     case "actionresettimer": return InteractorType.actionresettimer;
/*    :    */     case "roomeffect": return InteractorType.roomeffect;
/*    :    */     case "freezegreengate": return InteractorType.freezegreengate;
/*    :    */     case "banzaigateyellow": return InteractorType.banzaigateyellow;
/*    :    */     case "actiongivereward": return InteractorType.actiongivereward;
/*    :    */     case "triggeronusersay": return InteractorType.triggeronusersay;
/*    :    */     case "banzaigatered": return InteractorType.banzaigatered;
/*    :    */     case "actionteleportto": return InteractorType.actionteleportto;
/*    :    */     case "banzaiscorered": return InteractorType.banzaiscorered;
/*    :    */     case "scoreboard": return InteractorType.scoreboard;
/*    :    */     case "banzaipyramid": return InteractorType.banzaipyramid;
/*    :    */     case "freezeyellowgate": return InteractorType.freezeyellowgate;
/*    :    */     case "footballcounteryellow": return InteractorType.footballcounteryellow;
/*    :    */     case "puzzlebox": return InteractorType.puzzlebox;
/*    :    */     case "banzaigategreen": return InteractorType.banzaigategreen;
/*    :    */     case "triggerrepeater": return InteractorType.triggerrepeater;
/*    :    */     case "colortile": return InteractorType.colortile;
/*    :    */     case "banzaiscoregreen": return InteractorType.banzaiscoregreen;
/*    :    */     case "freezeredgate": return InteractorType.freezeredgate;
/*    :    */     case "wireSplitter": return InteractorType.wireSplitter;
/*    :    */     case "actiongivescore": return InteractorType.actiongivescore;
/*    :    */     default: return InteractorType.none;
/*    :    */     }
/*    :    */   }
}
