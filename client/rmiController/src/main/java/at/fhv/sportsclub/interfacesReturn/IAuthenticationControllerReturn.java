package at.fhv.sportsclub.interfacesReturn;

import at.fhv.sportsclub.model.security.SessionDTO;

import javax.ejb.Remote;
import java.rmi.RemoteException;

/*
      Created: 13.11.2018
      Author: Moritz W.
      Co-Authors: 
*/

/**
 * The reason why this interface is in a separate package, is that Spring AOP
 * scans at.fhv.sportsclub.controller.ejbinterfaces package and inserts proxies into
 * those interface. But for the Authentication Controller, no proxy should intercept
 * the method calls. That's why it was moved to this package.
 */
@Remote
public interface IAuthenticationControllerReturn {
    SessionDTO authenticate(String userId, char[] password) throws RemoteException;
    void logout(SessionDTO session) throws RemoteException;
}
