package at.fhv.sportsclub.controller.interfaces;

import at.fhv.sportsclub.interfacesReturn.IDepartmentControllerReturn;
import at.fhv.sportsclub.model.common.ListWrapper;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.dept.DepartmentDTO;
import at.fhv.sportsclub.model.dept.LeagueDTO;
import at.fhv.sportsclub.model.dept.SportDTO;
import at.fhv.sportsclub.model.security.SessionDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IDepartmentController extends Remote, IDepartmentControllerReturn {

    ListWrapper<DepartmentDTO> getAllEntries(SessionDTO session) throws RemoteException;

    ResponseMessageDTO saveOrUpdateEntry(SessionDTO session, DepartmentDTO departmentDTO) throws RemoteException;

    ListWrapper<SportDTO> getAllSportEntries(SessionDTO session) throws RemoteException;

    ListWrapper<SportDTO> getAllSportEntriesFull(SessionDTO session) throws RemoteException;

    SportDTO getSportByLeagueId(SessionDTO session, String leagueId) throws RemoteException;

    LeagueDTO getLeagueById(SessionDTO session, String id) throws RemoteException;

    ListWrapper<LeagueDTO> getLeaguesBySportId(SessionDTO session, String sportId) throws RemoteException;

    DepartmentDTO getDepartmentBySportId(SessionDTO session, String sportId) throws RemoteException;

}

