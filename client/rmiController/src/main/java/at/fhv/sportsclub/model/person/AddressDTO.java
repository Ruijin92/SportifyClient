package at.fhv.sportsclub.model.person;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
public @Data class AddressDTO implements Serializable {

    private String id;
    private String street;
    private String zipCode;
    private String city;

    public AddressDTO() { }
}
