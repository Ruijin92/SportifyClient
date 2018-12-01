package at.fhv.team2;

import at.fhv.team2.dashboard.Dashboard;
import at.fhv.team2.mainpage.MainPage;
import at.fhv.team2.member.Member;
import at.fhv.team2.teams.AllTeams;
import at.fhv.team2.teams.TeamSquad;
import at.fhv.team2.teams.TeamViewModel;
import at.fhv.team2.wettkampf.AllCompetition;
import at.fhv.team2.wettkampf.NewCompetition;
import javafx.scene.layout.BorderPane;
import sun.applet.Main;

/**
 * Created by Uray Örnek on 11/6/2018.
 */
public class PageProvider {

    private MainPage mainPage;
    private static PageProvider instance = null;


    public static PageProvider getPageProvider(){
        if(instance == null) {
            instance = new PageProvider();
        }
        return instance;
    }

    public void setMainPage(MainPage mainPage){
        this.mainPage = mainPage;
    }

    public void switchDashboard() {
        mainPage.mainPane.setCenter(new Dashboard());
    }

    public void switchMember() {

        mainPage.mainPane.setCenter(new Member());
    }

    public void switchCompetitions() {
        mainPage.mainPane.setCenter(new AllCompetition());
    }

    public void switchTeams() {
        mainPage.mainPane.setCenter(new AllTeams());
    }

    public void switchNewComp(){
        mainPage.mainPane.setCenter(new NewCompetition());
    }

    public void switchTeamSquad(TeamViewModel team) {
        mainPage.mainPane.setCenter(new TeamSquad(team));
    }
}
