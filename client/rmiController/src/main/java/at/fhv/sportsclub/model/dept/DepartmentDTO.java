package at.fhv.sportsclub.model.dept;

import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.person.PersonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
public @Data class DepartmentDTO implements Serializable {

    public DepartmentDTO() { }

    private static final long serialVersionUID = 1009685098267757690L;

    private String id;

    private String deptName;
    private PersonDTO deptLeader;
    private List<SportDTO> sports;
    private ResponseMessageDTO response;
}
