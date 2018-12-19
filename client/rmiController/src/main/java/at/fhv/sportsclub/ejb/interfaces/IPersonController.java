package at.fhv.sportsclub.ejb.interfaces;

import at.fhv.sportsclub.interfacesReturn.IPersonControllerReturn;
import at.fhv.sportsclub.model.common.ListWrapper;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.person.PersonDTO;
import at.fhv.sportsclub.model.security.SessionDTO;

import javax.ejb.Remote;

@Remote
public interface IPersonController extends IPersonControllerReturn {

    ListWrapper<PersonDTO> getAllEntries(SessionDTO session) ;

    PersonDTO getEntryDetails(SessionDTO session, String id) ;

    ResponseMessageDTO saveOrUpdateEntry(SessionDTO session, PersonDTO personDTO) ;
}
