package at.fhv.team2;

import at.fhv.sportsclub.controller.interfaces.*;
import at.fhv.sportsclub.factory.IControllerFactory;
import at.fhv.sportsclub.model.security.SessionDTO;
import at.fhv.sportsclub.security.authentication.IAuthenticationController;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public class DataProviderRMI implements IDataProvider{

    private static IControllerFactory controllerFactory = null;
    private static IPersonController personController = null;
    private static IDepartmentController departmentController = null;
    private static ITeamController teamController = null;
    private static ITournamentController tournamentController = null;
    private static IAuthenticationController authenticationController = null;
    private static IMessageController messageController = null;

    private static DataProviderRMI instance = null;
    private static Registry registry;
    private static SessionDTO session;

    public DataProviderRMI(){
        registry = DataProviderFactory.getRegistry();
        if (controllerFactory == null) {
            try {
                controllerFactory = (IControllerFactory) registry.lookup("ControllerFactory");
            } catch (RemoteException | NotBoundException e) {
                e.printStackTrace();
            }
        }
    }


    public IPersonController getPersonControllerInstance() {
        if (personController == null) {
            try {
                return controllerFactory.getPersonController();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return personController;
    }

    public IDepartmentController getDepartmentControllerInstance() {
        if (departmentController == null) {
            try {
                return controllerFactory.getDepartmentController();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return departmentController;
    }

    public ITeamController getTeamControllerInstance() {
        if (teamController == null) {
            try {
                return controllerFactory.getTeamController();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return teamController;
    }

    public ITournamentController getTournamentControllerInstance() {
        if (tournamentController == null) {
            try {
                return controllerFactory.getTournamentController();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return tournamentController;
    }

    public IMessageController getMessageControllerInstance() {
        if (messageController == null) {
            try {
                return controllerFactory.getMessageController();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return messageController;
    }


    public void setRegistry(Registry registry) {
        DataProviderRMI.registry = registry;
    }

    public String authenticate(String userId, char[] pw) throws RemoteException, NotBoundException {
        IAuthenticationController authenticationController = (IAuthenticationController) registry.lookup("AuthenticationService");
        session = authenticationController.authenticate(userId, pw);
        if (session.getResponseMessage() == null) {
            return "";
        } else {
            return session.getResponseMessage().getInfoMessage();
        }
    }

    public void logout() throws RemoteException, NotBoundException {
        IAuthenticationController authenticationController = (IAuthenticationController) registry.lookup("AuthenticationService");
        authenticationController.logout(session);
        session = null;
    }

    public SessionDTO getSession() {
        return session;
    }
}
