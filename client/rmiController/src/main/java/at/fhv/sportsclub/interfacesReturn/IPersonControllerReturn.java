package at.fhv.sportsclub.interfacesReturn;

import at.fhv.sportsclub.model.common.ListWrapper;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.person.PersonDTO;
import at.fhv.sportsclub.model.security.SessionDTO;

import javax.ejb.Remote;
import java.rmi.RemoteException;

@Remote
public interface IPersonControllerReturn {

    ListWrapper<PersonDTO> getAllEntries(SessionDTO session) throws RemoteException;

    PersonDTO getEntryDetails(SessionDTO session, String id) throws RemoteException;

    ResponseMessageDTO saveOrUpdateEntry(SessionDTO session, PersonDTO personDTO) throws RemoteException;
}
