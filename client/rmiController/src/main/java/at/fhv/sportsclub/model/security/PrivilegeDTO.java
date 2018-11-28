package at.fhv.sportsclub.model.security;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/*
      Created: 18.11.2018
      Author: Moritz W.
      Co-Authors: 
*/
public @Data class PrivilegeDTO implements Serializable {
    private String domain;
    private Set<AccessLevel> accessLevels;
}
