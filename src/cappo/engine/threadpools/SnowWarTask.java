package cappo.engine.threadpools;

import cappo.engine.logging.Log;
import cappo.game.games.snowwar.SnowPlayerQueue;
import cappo.game.games.snowwar.SnowWarRoom;
import cappo.game.games.snowwar.tasks.SnowArenaEnd;
import cappo.game.games.snowwar.tasks.SnowArenaRun;
import cappo.game.games.snowwar.tasks.SnowStageLoading;
import cappo.game.games.snowwar.tasks.SnowStageRun;
import cappo.game.games.snowwar.tasks.SnowStageStarting;
import java.util.concurrent.ScheduledFuture;

public class SnowWarTask
  extends GameTask
{
  public SnowWarRoom room;
  
  public static void addTask(GameTask task, int initDelay, int repeatDelay)
  {
    WorkerTasks.addTask(task, initDelay, repeatDelay, WorkerTasks.SnowWarTasks);
  }
  
  public SnowWarTask(SnowWarRoom snowRoom)
  {
    this.room = snowRoom;
  }
  
  public void run()
  {
    try
    {
      if (this.room.STATUS == 6)
      {
        this.future.cancel(false);
        SnowArenaEnd.exec(this.room);
        return;
      }
      if (this.room.STATUS == 5)
      {
        SnowArenaRun.exec(this.room);
        return;
      }
      if (this.room.STATUS == 4)
      {
        SnowStageRun.exec(this.room);
        this.room.STATUS = 5;
        return;
      }
      if (this.room.STATUS == 3)
      {
        SnowStageStarting.exec(this.room);
        this.room.STATUS = 4;
        addTask(this, 6000, 150);
        return;
      }
      if (this.room.STATUS == 2)
      {
        SnowStageLoading.exec(this.room);
        if (this.room.STATUS == 3)
        {
          this.future.cancel(false);
          addTask(this, 6000, 0);
        }
        return;
      }
      if (this.room.STATUS == 1)
      {
        if (this.room.TimeToStart-- == 0)
        {
          this.future.cancel(false);
          
          SnowPlayerQueue.roomLoaded(this.room);
          this.room.STATUS = 2;
          addTask(this, 100, 200);
        }
        return;
      }
    }
    catch (Exception ex)
    {
      this.future.cancel(false);
      Log.printException("SnowEngine", ex);
    }
  }
}


