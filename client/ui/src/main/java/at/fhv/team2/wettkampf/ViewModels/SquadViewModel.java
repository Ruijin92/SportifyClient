package at.fhv.team2.wettkampf.ViewModels;

import at.fhv.sportsclub.model.tournament.SquadMemberDTO;
import at.fhv.team2.member.PersonViewModel;

public class SquadViewModel {

    private PersonViewModel member;
    private boolean participating;

    public SquadViewModel(PersonViewModel person, boolean participating) {
        this.member = person;
        this.participating = participating;
    }

    public PersonViewModel getMember() {
        return member;
    }

    public void setMember(PersonViewModel member) {
        this.member = member;
    }

    public boolean isParticipating() {
        return participating;
    }

    public void setParticipating(boolean participating) {
        this.participating = participating;
    }
}
