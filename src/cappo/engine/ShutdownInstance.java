package cappo.engine;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ShutdownInstance
  extends UnicastRemoteObject
  implements RemoteCommand
{
  public ShutdownInstance()
    throws RemoteException
  {}
  
  public String exec()
    throws RemoteException
  {
    ServerProps.STATUS = false;
    return "WORKS!: ";
  }
}


