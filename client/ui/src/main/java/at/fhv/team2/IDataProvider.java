package at.fhv.team2;


import at.fhv.sportsclub.interfacesReturn.*;
import at.fhv.sportsclub.model.security.SessionDTO;
import javafx.beans.property.SimpleStringProperty;

import javax.naming.NamingException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public interface IDataProvider {

    IPersonControllerReturn getPersonControllerInstance();

    IDepartmentControllerReturn getDepartmentControllerInstance();

    ITeamControllerReturn getTeamControllerInstance();

    ITournamentControllerReturn getTournamentControllerInstance();

    IMessageControllerReturn getMessageControllerInstance();

    String authenticate(String userId, char[] pw) throws RemoteException, NotBoundException, NamingException;

    void logout() throws RemoteException, NotBoundException, NamingException;

    SessionDTO getSession();

    void setRegistry(Registry registry);

    void setMessageStatus(String newMessageStatus);

    SimpleStringProperty getMessageStatus();
}


