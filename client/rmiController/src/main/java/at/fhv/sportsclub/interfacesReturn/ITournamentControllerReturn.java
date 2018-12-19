package at.fhv.sportsclub.interfacesReturn;

import at.fhv.sportsclub.model.common.ListWrapper;
import at.fhv.sportsclub.model.security.SessionDTO;
import at.fhv.sportsclub.model.tournament.TournamentDTO;

import javax.ejb.Remote;
import java.rmi.RemoteException;


@Remote
public interface ITournamentControllerReturn {
    ListWrapper<TournamentDTO> getAllEntries(SessionDTO session) throws RemoteException;
    TournamentDTO getEntryDetails(SessionDTO session, String id) throws RemoteException;
    TournamentDTO getById(SessionDTO session, String id) throws RemoteException;
    ListWrapper<TournamentDTO> getTournamentByTrainerId(SessionDTO session, String personId) throws RemoteException;
    TournamentDTO saveOrUpdateEntry(SessionDTO session, TournamentDTO tournament) throws RemoteException;
}
