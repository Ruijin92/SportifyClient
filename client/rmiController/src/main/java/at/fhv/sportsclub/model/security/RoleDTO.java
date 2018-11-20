package at.fhv.sportsclub.model.security;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/*
      Created: 14.11.2018
      Author: Moritz W.
      Co-Authors: 
*/
public @Data class RoleDTO implements Serializable {
    private String id;
    private String name;
    List<PrivilegeDTO> privileges;
}
