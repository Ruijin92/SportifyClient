package at.fhv.sportsclub.interfacesReturn;

import at.fhv.sportsclub.model.common.ListWrapper;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.security.SessionDTO;
import at.fhv.sportsclub.model.team.TeamDTO;

import javax.ejb.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * businessMonkey
 * at.fhv.sportsclub.controller
 * ITeamController
 * 07.11.2018 sge
 */
@Remote
public interface ITeamControllerReturn {
    ArrayList<TeamDTO> getAllEntries(SessionDTO session) throws RemoteException;
    ResponseMessageDTO saveOrUpdateEntry(SessionDTO session, TeamDTO teamDTO) throws RemoteException;
    TeamDTO getById(SessionDTO session, String id) throws RemoteException;
    TeamDTO getEntryDetails(SessionDTO session, String id) throws RemoteException;
    ListWrapper<TeamDTO> getByLeague(SessionDTO session, String leagueId) throws RemoteException;
    ListWrapper<TeamDTO> getBySport(SessionDTO session, String sportId) throws RemoteException;
    ListWrapper<TeamDTO> getTeamsByTrainerId(SessionDTO session, String trainerPersonId) throws RemoteException;
}
