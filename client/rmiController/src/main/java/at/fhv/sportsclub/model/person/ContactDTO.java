package at.fhv.sportsclub.model.person;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
public @Data class ContactDTO implements Serializable {
    public ContactDTO() {}

    private String id;
    private String phoneNumber;
    private String emailAddress;
}
