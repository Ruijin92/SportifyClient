package at.fhv.team2.roles;

/**
 * Created by Uray Örnek on 11/20/2018.
 */
public interface IRole {

    enum Role {
        READ, WRITE, SPECIAL, NONE;
    }

    boolean createMemberPremission();

    boolean createCompetitionPremission();

    boolean createTeamPremission();

    boolean viewMemberPremission();

    boolean viewCompetionPremission();

    boolean viewTeamPremission();
}
