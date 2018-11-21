package at.fhv.sportsclub.security.authentication;

import at.fhv.sportsclub.model.security.SessionDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;

/*
      Created: 13.11.2018
      Author: Moritz W.
      Co-Authors: 
*/
public interface IAuthenticationController extends Remote {
    SessionDTO authenticate(String userId, char[] password) throws RemoteException;
    void logout(SessionDTO session) throws RemoteException;
}
