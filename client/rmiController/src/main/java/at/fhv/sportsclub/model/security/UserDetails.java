package at.fhv.sportsclub.model.security;

/*
      Created: 14.11.2018
      Author: Moritz W.
      Co-Authors: 
*/

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
public @Data class UserDetails {
    private String userId;
    private String nonce;
    private List<RoleDTO> roles;
}
