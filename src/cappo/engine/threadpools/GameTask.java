package cappo.engine.threadpools;

import java.util.concurrent.ScheduledFuture;

public abstract class GameTask
  extends Thread
{
  public ScheduledFuture<?> future;
}


