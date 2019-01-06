package at.fhv.sportsclub.controller.interfaces;

import at.fhv.sportsclub.interfacesReturn.IPersonControllerReturn;
import at.fhv.sportsclub.model.common.ListWrapper;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.person.PersonDTO;
import at.fhv.sportsclub.model.security.SessionDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPersonController extends Remote, IPersonControllerReturn {

    ListWrapper<PersonDTO> getAllEntries(SessionDTO session) throws RemoteException;

    PersonDTO getEntryDetails(SessionDTO session, String id) throws RemoteException;

    ResponseMessageDTO saveOrUpdateEntry(SessionDTO session, PersonDTO personDTO) throws RemoteException;
}
