package at.fhv.team2.roles;

import at.fhv.sportsclub.model.security.AccessLevel;
import at.fhv.sportsclub.model.security.PrivilegeDTO;
import at.fhv.sportsclub.model.security.RoleDTO;

import java.util.List;

/**
 * Created by Uray Örnek on 11/20/2018.
 */
public class Permission implements IRole {

    private static Permission permission;

    List<RoleDTO> roleDTO;
    private String username;

    //Domain Person
    private boolean createMember = false;
    private boolean viewMember = false;

    //Domain Team
    private boolean createTeam = false;
    private boolean viewTeam = false;

    //Domain Competition
    private boolean viewCompetition = false;
    private boolean createCompetition = false;

    public Permission() {

    }

    public void setRoles(List<RoleDTO> role) {
        roleDTO = role;
    }

    public void checkPermission() {

        for (RoleDTO role : roleDTO) {
            List<PrivilegeDTO> privileges = role.getPrivileges();
            for (PrivilegeDTO privilegeDTO : privileges) {
                String domain = privilegeDTO.getDomain();
                if (domain.equals("Person")) {
                    if (privilegeDTO.getAccessLevels().contains(AccessLevel.READ)) {
                        viewMember = true;
                    }
                    if (privilegeDTO.getAccessLevels().contains(AccessLevel.WRITE)) {
                        createMember = true;
                    }
                }
                if (domain.equals("Team")) {
                    if (privilegeDTO.getAccessLevels().contains(AccessLevel.READ)) {
                        viewTeam = true;
                    }
                    if (privilegeDTO.getAccessLevels().contains(AccessLevel.WRITE)) {
                        createTeam = true;
                    }
                }
                if (domain.equals("Tournament")) {
                    if (privilegeDTO.getAccessLevels().contains(AccessLevel.READ)) {
                        viewCompetition = true;
                    }
                    if (privilegeDTO.getAccessLevels().contains(AccessLevel.WRITE)) {
                        createCompetition = true;
                    }
                }
            }
        }
    }

    public static Permission getPermission() {
        if (permission == null) {
            permission = new Permission();
        }
        return permission;
    }

    @Override
    public boolean createMemberPermission() {
        return createMember;
    }

    @Override
    public boolean createCompetitionPermission() {
        return createCompetition;
    }

    @Override
    public boolean createTeamPermission() {
        return createTeam;
    }

    @Override
    public boolean viewMemberPermission() {
        return viewMember;
    }

    @Override
    public boolean viewCompetitionPermission() {
        return viewCompetition;
    }

    @Override
    public boolean viewTeamPermission() {
        return viewTeam;
    }

    public void loadGuest() {
        //Domain Person
        createMember = false;
        viewMember = true;

        //Domain Team
        createTeam = false;
        viewTeam = true;

        //Domain Competition
        createCompetition = false;
        viewCompetition = true;

        username = "Guest";
    }

    public void loadAdmin() {
        //Domain Person
        createMember = true;
        viewMember = true;

        //Domain Team
        createTeam = true;
        viewTeam = true;

        //Domain Competition
        createCompetition = true;
        viewCompetition = true;
    }

    public String getUsername() {
        return username;
    }
}
