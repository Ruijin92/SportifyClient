package at.fhv.sportsclub.model.person;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
public @Data class PersonDTO implements Serializable {

    public PersonDTO() {}

    private String id;
    private String firstName;
    private String lastName;

    private LocalDate dateOfBirth;
    private AddressDTO address;
    private ContactDTO contact;
}
