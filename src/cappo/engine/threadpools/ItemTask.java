package cappo.engine.threadpools;

import cappo.game.roomengine.entity.item.floor.GenericFloorItem;

public abstract class ItemTask
  extends GameTask
{
  public GenericFloorItem item;
  
  public static void addTask(GameTask task, int initDelay, int repeatDelay)
  {
    WorkerTasks.addTask(task, initDelay, repeatDelay, WorkerTasks.ItemsTasks);
  }
  
  public ItemTask(GenericFloorItem self)
  {
    this.item = self;
  }
}


