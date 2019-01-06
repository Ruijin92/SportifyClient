package at.fhv.sportsclub.ejb.interfaces;

import at.fhv.sportsclub.interfacesReturn.ITournamentControllerReturn;
import at.fhv.sportsclub.model.common.ListWrapper;
import at.fhv.sportsclub.model.security.SessionDTO;
import at.fhv.sportsclub.model.tournament.TournamentDTO;

import javax.ejb.Remote;


@Remote
public interface ITournamentController extends ITournamentControllerReturn {
    ListWrapper<TournamentDTO> getAllEntries(SessionDTO session) ;
    TournamentDTO getEntryDetails(SessionDTO session, String id) ;
    TournamentDTO getById(SessionDTO session, String id) ;
    ListWrapper<TournamentDTO> getTournamentByTrainerId(SessionDTO session, String personId) ;
    TournamentDTO saveOrUpdateEntry(SessionDTO session, TournamentDTO tournament) ;
}
