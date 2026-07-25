package cappo.engine;

import java.rmi.Remote;
import java.rmi.RemoteException;

public abstract interface RemoteCommand
  extends Remote
{
  public abstract String exec()
    throws RemoteException;
}


