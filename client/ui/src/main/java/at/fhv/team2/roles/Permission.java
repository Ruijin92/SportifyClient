package at.fhv.team2.roles;

import at.fhv.sportsclub.model.security.PrivilegeDTO;
import at.fhv.sportsclub.model.security.RoleDTO;

import javax.management.relation.RoleInfo;
import java.util.List;

/**
 * Created by Uray Örnek on 11/20/2018.
 */
public class Permission implements IRole {

    List<RoleDTO> roleDTO;

    //Domain Person
    private boolean createMember = false;
    private boolean viewMember = false;

    //Domain Team
    private boolean createTeam = false;
    private boolean viewTeam = false;

    //Domain Competition
    private boolean viewCompetition = false;
    private boolean createCompetition = false;

    public Permission(){

    }

    public void setRoles(List<RoleDTO> role){
        roleDTO = role;
    }

    //TODO: Hässlich gelöst aber wir haben hier eh keine Performance Problems
    public void checkPermission(){
       for(RoleDTO role : roleDTO){
           List<PrivilegeDTO> privileges = role.getPrivileges();
           for(PrivilegeDTO privilegeDTO: privileges){
               String domain = privilegeDTO.getDomain();
               if(domain.equals("Person")){

               }
               if(domain.equals("test")){

               }
           }
       }

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
}
