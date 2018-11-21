package at.fhv.sportsclub.model.person;

import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.dept.SportDTO;
import at.fhv.sportsclub.model.security.RoleDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
public @Data class PersonDTO implements Serializable {

    public PersonDTO() {}

    private static final long serialVersionUID = 5529685098267757695L; // changed 18.11.2018

    private String id;
    private String firstName;
    private String lastName;

    private LocalDate dateOfBirth;
    private AddressDTO address;
    private ContactDTO contact;
    private List<SportDTO> sports;
    private List<RoleDTO> roles;

    private ResponseMessageDTO response = null;
}
