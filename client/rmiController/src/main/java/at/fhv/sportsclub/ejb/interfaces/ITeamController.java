package at.fhv.sportsclub.ejb.interfaces;

import at.fhv.sportsclub.interfacesReturn.ITeamControllerReturn;
import at.fhv.sportsclub.model.common.ListWrapper;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.security.SessionDTO;
import at.fhv.sportsclub.model.team.TeamDTO;

import javax.ejb.Remote;
import java.util.ArrayList;

/**
 * businessMonkey
 * at.fhv.sportsclub.controller
 * ITeamController
 * 07.11.2018 sge
 */
@Remote
public interface ITeamController extends ITeamControllerReturn {
    ArrayList<TeamDTO> getAllEntries(SessionDTO session) ;
    ResponseMessageDTO saveOrUpdateEntry(SessionDTO session, TeamDTO teamDTO) ;
    TeamDTO getById(SessionDTO session, String id) ;
    TeamDTO getEntryDetails(SessionDTO session, String id) ;
    ListWrapper<TeamDTO> getByLeague(SessionDTO session, String leagueId) ;
    ListWrapper<TeamDTO> getBySport(SessionDTO session, String sportId) ;
    ListWrapper<TeamDTO> getTeamsByTrainerId(SessionDTO session, String trainerPersonId) ;
}
