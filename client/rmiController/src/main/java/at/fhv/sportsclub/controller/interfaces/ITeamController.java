package at.fhv.sportsclub.controller.interfaces;

import at.fhv.sportsclub.model.common.ResponseMessageDTO;
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

    ArrayList<TeamDTO> getAllEntries() throws RemoteException;

    ResponseMessageDTO saveOrUpdateEntry(TeamDTO teamDTO) throws RemoteException;
}
