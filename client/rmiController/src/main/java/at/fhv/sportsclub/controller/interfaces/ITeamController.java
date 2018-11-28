package at.fhv.sportsclub.controller.interfaces;

import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.security.SessionDTO;
import at.fhv.sportsclub.model.team.TeamDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * businessMonkey
 * at.fhv.sportsclub.controller
 * ITeamController
 * 07.11.2018 sge
 */
public interface ITeamController extends Remote {
    ArrayList<TeamDTO> getAllEntries(SessionDTO session) throws RemoteException;
    ResponseMessageDTO saveOrUpdateEntry(SessionDTO session, TeamDTO teamDTO) throws RemoteException;
    TeamDTO getByLeague(SessionDTO session, String leagueId) throws RemoteException;
    TeamDTO getById(SessionDTO session, String id) throws RemoteException;
    TeamDTO getByIdFull(SessionDTO session, String id) throws RemoteException;
}
