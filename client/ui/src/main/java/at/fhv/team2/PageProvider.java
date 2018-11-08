package at.fhv.team2;

import at.fhv.team2.dashboard.Dashboard;
import at.fhv.team2.member.Member;
import at.fhv.team2.wettkampf.AllCompetition;

/**
 * Created by Uray Örnek on 11/6/2018.
 */
public class PageProvider {

    MainPage mainPage;

    public PageProvider(MainPage mainPage) {
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
}
