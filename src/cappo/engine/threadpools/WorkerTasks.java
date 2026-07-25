package cappo.engine.threadpools;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WorkerTasks
{
  public static int serverType;
  public static final int SERVER_TINY = 0;
  public static final int SERVER_SMALL = 1;
  public static final int SERVER_NORMAL = 2;
  public static final int SERVER_LARGE = 3;
  public static final int SERVER_EXTRALARGE = 4;
  public static final int SERVER_TURBO = 5;
  public static ScheduledThreadPoolExecutor ItemsTasks;
  public static ScheduledThreadPoolExecutor RoomsTasks;
  public static ScheduledThreadPoolExecutor SnowWarTasks;
  public static ScheduledThreadPoolExecutor DatabaseExecTasks;
  public static ScheduledThreadPoolExecutor DatabaseQueryTasks;
  
  public static void initWorkers(int type)
  {
    serverType = type;
    if (serverType == 0)
    {
      RoomsTasks = new ScheduledThreadPoolExecutor(1);
      SnowWarTasks = new ScheduledThreadPoolExecutor(1);
      ItemsTasks = new ScheduledThreadPoolExecutor(1);
      DatabaseExecTasks = new ScheduledThreadPoolExecutor(1);
      DatabaseQueryTasks = new ScheduledThreadPoolExecutor(1);
    }
    else if (serverType == 1)
    {
      RoomsTasks = new ScheduledThreadPoolExecutor(2);
      SnowWarTasks = new ScheduledThreadPoolExecutor(1);
      ItemsTasks = new ScheduledThreadPoolExecutor(1);
      DatabaseExecTasks = new ScheduledThreadPoolExecutor(1);
      DatabaseQueryTasks = new ScheduledThreadPoolExecutor(1);
    }
    else if (serverType == 2)
    {
      RoomsTasks = new ScheduledThreadPoolExecutor(4);
      SnowWarTasks = new ScheduledThreadPoolExecutor(2);
      ItemsTasks = new ScheduledThreadPoolExecutor(1);
      DatabaseExecTasks = new ScheduledThreadPoolExecutor(2);
      DatabaseQueryTasks = new ScheduledThreadPoolExecutor(2);
    }
    else if (serverType == 3)
    {
      RoomsTasks = new ScheduledThreadPoolExecutor(8);
      SnowWarTasks = new ScheduledThreadPoolExecutor(3);
      ItemsTasks = new ScheduledThreadPoolExecutor(2);
      DatabaseExecTasks = new ScheduledThreadPoolExecutor(4);
      DatabaseQueryTasks = new ScheduledThreadPoolExecutor(3);
    }
    else
    {
      RoomsTasks = new ScheduledThreadPoolExecutor(10);
      SnowWarTasks = new ScheduledThreadPoolExecutor(3);
      ItemsTasks = new ScheduledThreadPoolExecutor(2);
      DatabaseExecTasks = new ScheduledThreadPoolExecutor(6);
      DatabaseQueryTasks = new ScheduledThreadPoolExecutor(4);
    }
  }
  
  public static void addTask(GameTask task, int initDelay, int repeatRate, ScheduledThreadPoolExecutor worker)
  {
    if (repeatRate > 0) {
      task.future = worker.scheduleAtFixedRate(task, initDelay, repeatRate, TimeUnit.MILLISECONDS);
    } else {
      task.future = worker.schedule(task, initDelay, TimeUnit.MILLISECONDS);
    }
  }
}


