package at.fhv.sportsclub.factory;

import at.fhv.sportsclub.controller.interfaces.IDepartmentController;
import at.fhv.sportsclub.controller.interfaces.IPersonController;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by Alex on 06.11.2018.
 */

public interface IControllerFactory extends Remote {
    IPersonController getPersonController() throws RemoteException;
    IDepartmentController getDepartmentController() throws RemoteException;
}
