package at.fhv.sportsclub.ejb.interfaces;

import at.fhv.sportsclub.interfacesReturn.IAuthenticationControllerReturn;
import at.fhv.sportsclub.model.security.SessionDTO;

import javax.ejb.Remote;

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
public interface IAuthenticationController extends IAuthenticationControllerReturn {
    SessionDTO authenticate(String userId, char[] password) ;
    void logout(SessionDTO session) ;
}
