package at.fhv.team2;

import at.fhv.sportsclub.ejb.interfaces.*;
import at.fhv.sportsclub.model.security.SessionDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public class DataProviderEJB implements IDataProvider {

    private static IPersonController personController = null;
    private static IDepartmentController departmentController = null;
    private static ITeamController teamController = null;
    private static ITournamentController tournamentController = null;
    private static IAuthenticationController authenticationController = null;
    private static IMessageController messageController = null;

    private static DataProviderEJB instance = null;
    private static InitialContext initialContext;
    private static SessionDTO session;

    private static SimpleStringProperty messageStatus = new SimpleStringProperty();


    public DataProviderEJB(){
        initialContext = DataProviderFactory.getInitialContext();
    }

    public IPersonController getPersonControllerInstance() {
        if (personController == null) {
            try {
                return (IPersonController)  initialContext.lookup(
                        "ejb:/enterpriseBeans-1.0-SNAPSHOT/PersonControllerBean!at.fhv.sportsclub.ejb.interfaces.IPersonController");
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        return personController;
    }

    public IDepartmentController getDepartmentControllerInstance() {
        if (departmentController == null) {
            try {
                return (IDepartmentController)  initialContext.lookup(
                        "ejb:/enterpriseBeans-1.0-SNAPSHOT/DepartmentControllerBean!at.fhv.sportsclub.ejb.interfaces.IDepartmentController");
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        return departmentController;
    }

    public ITeamController getTeamControllerInstance() {
        if (teamController == null) {
            try {
                return (ITeamController)  initialContext.lookup(
                        "ejb:/enterpriseBeans-1.0-SNAPSHOT/TeamControllerBean!at.fhv.sportsclub.ejb.interfaces.ITeamController");
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        return teamController;
    }

    public ITournamentController getTournamentControllerInstance() {
        if (tournamentController == null) {
            try {
                return (ITournamentController)  initialContext.lookup(
                        "ejb:/enterpriseBeans-1.0-SNAPSHOT/TournamentControllerBean!at.fhv.sportsclub.ejb.interfaces.ITournamentController");
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        return tournamentController;
    }

    public IMessageController getMessageControllerInstance() {
        if (messageController == null) {
            try {
                return (IMessageController)  initialContext.lookup(
                        "ejb:/enterpriseBeans-1.0-SNAPSHOT/MessageControllerBean!at.fhv.sportsclub.ejb.interfaces.IMessageController");
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        return messageController;
    }

    public static void setInitialContext(InitialContext initialContext) {
        DataProviderEJB.initialContext = initialContext;
    }

    public String authenticate(String userId, char[] pw) throws NotBoundException, RemoteException, NamingException {
        IAuthenticationController authenticationController = (IAuthenticationController) initialContext.lookup(
                "ejb:/enterpriseBeans-1.0-SNAPSHOT/AuthenticationControllerBean!at.fhv.sportsclub.ejb.interfaces.IAuthenticationController");
        session = authenticationController.authenticate(userId, pw);
        if (session.getResponseMessage() == null) {
            return "";
        } else {
            return session.getResponseMessage().getInfoMessage();
        }
    }

    public void logout() throws RemoteException, NotBoundException, NamingException {
        IAuthenticationController authenticationController = (IAuthenticationController) initialContext.lookup(
                "ejb:/enterpriseBeans-1.0-SNAPSHOT/AuthenticationControllerBean!at.fhv.sportsclub.ejb.interfaces.IAuthenticationController");
        authenticationController.logout(session);
        session = null;
    }

    public SessionDTO getSession() {
        return session;
    }


    @Override
    public void setRegistry(Registry registry) {

    }

    @Override
    public void setMessageStatus(String newMessageStatus) {
        messageStatus.set(newMessageStatus);
    }

    @Override
    public SimpleStringProperty getMessageStatus() {
        return messageStatus;
    }
}
