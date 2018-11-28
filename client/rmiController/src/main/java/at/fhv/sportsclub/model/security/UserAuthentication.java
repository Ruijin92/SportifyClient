package at.fhv.sportsclub.model.security;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
      Created: 14.11.2018
      Author: Moritz W.
      Co-Authors: 
*/
@AllArgsConstructor
public @Data class UserAuthentication {
    private String id;
    private char[] credentials;

    public void clearCredentials(){
        for (int i = 0; i < credentials.length; i++) {
            credentials[i] = '*';
        }
    }
}
