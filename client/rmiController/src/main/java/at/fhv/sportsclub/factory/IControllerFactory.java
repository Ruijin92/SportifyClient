package at.fhv.sportsclub.factory;

import at.fhv.sportsclub.controller.interfaces.*;

import java.rmi.Remote;
import java.rmi.RemoteException;

/*
 * Created by Alex on 06.11.2018.
 */

public interface IControllerFactory extends Remote {
    IPersonController getPersonController() throws RemoteException;
    IDepartmentController getDepartmentController() throws RemoteException;
    ITeamController getTeamController() throws RemoteException;
    ITournamentController getTournamentController() throws RemoteException;
    IMessageController getMessageController() throws RemoteException;
}
