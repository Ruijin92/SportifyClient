package remoteobjects.controllerinterfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Alex on 06.11.2018.
 */

public interface ICreateMemberController extends Remote {

    boolean createMember() throws RemoteException;
}
