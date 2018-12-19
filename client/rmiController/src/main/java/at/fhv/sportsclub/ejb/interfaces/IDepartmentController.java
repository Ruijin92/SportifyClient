package at.fhv.sportsclub.ejb.interfaces;

import at.fhv.sportsclub.interfacesReturn.IDepartmentControllerReturn;
import at.fhv.sportsclub.model.common.ListWrapper;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.dept.DepartmentDTO;
import at.fhv.sportsclub.model.dept.LeagueDTO;
import at.fhv.sportsclub.model.dept.SportDTO;
import at.fhv.sportsclub.model.security.SessionDTO;

import javax.ejb.Remote;


@Remote
public interface IDepartmentController extends IDepartmentControllerReturn {

    ListWrapper<DepartmentDTO> getAllEntries(SessionDTO session) ;

    ResponseMessageDTO saveOrUpdateEntry(SessionDTO session, DepartmentDTO departmentDTO) ;

    ListWrapper<SportDTO> getAllSportEntries(SessionDTO session) ;

    ListWrapper<SportDTO> getAllSportEntriesFull(SessionDTO session) ;

    SportDTO getSportByLeagueId(SessionDTO session, String leagueId) ;

    LeagueDTO getLeagueById(SessionDTO session, String id) ;

    ListWrapper<LeagueDTO> getLeaguesBySportId(SessionDTO session, String sportId) ;

    DepartmentDTO getDepartmentBySportId(SessionDTO session, String sportId) ;

}

