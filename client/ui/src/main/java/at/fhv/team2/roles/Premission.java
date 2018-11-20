package at.fhv.team2.roles;

/**
 * Created by Uray Örnek on 11/20/2018.
 */
public class Premission implements IRole {

    @Override
    public boolean createMemberPremission() {
        return false;
    }

    @Override
    public boolean createCompetitionPremission() {
        return false;
    }

    @Override
    public boolean createTeamPremission() {
        return false;
    }

    @Override
    public boolean viewMemberPremission() {
        return false;
    }

    @Override
    public boolean viewCompetionPremission() {
        return false;
    }

    @Override
    public boolean viewTeamPremission() {
        return false;
    }
}
