package at.fhv.sportsclub.controller.interfaces;

import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.person.PersonDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IPersonController extends Remote {

    ArrayList<PersonDTO> getAllEntries() throws RemoteException;

    PersonDTO getEntryDetails(String id) throws RemoteException;

    ResponseMessageDTO saveOrUpdateEntry(PersonDTO personDTO) throws RemoteException;
}
