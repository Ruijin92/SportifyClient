package at.fhv.sportsclub.controller.interfaces;

import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.tournament.TournamentDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ITournamentController extends Remote {

    ArrayList<TournamentDTO> getAllEntries() throws RemoteException;

    ResponseMessageDTO saveOrUpdateEntry(TournamentDTO tournamentDTO) throws RemoteException;

}
