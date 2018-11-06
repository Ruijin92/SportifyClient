package factory;

import remoteobjects.controllerinterfaces.ICreateMemberController;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Alex on 06.11.2018.
 */

public interface IControllerFactory extends Remote {

    ICreateMemberController getCreateMemberController() throws RemoteException;

}
