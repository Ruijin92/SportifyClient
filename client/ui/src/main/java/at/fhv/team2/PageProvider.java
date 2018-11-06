package at.fhv.team2;

import at.fhv.team2.dashboard.Dashboard;
import at.fhv.team2.mitglieder.Mitglieder;
import sun.applet.Main;

/**
 * Created by Uray Örnek on 11/6/2018.
 */
public class PageProvider {

    MainPage mainPage;

    public PageProvider(MainPage mainPage) {
        this.mainPage = mainPage;
    }


    public void switchDashboard() {
        mainPage.anchorPane.setCenter(new Dashboard());
    }

    public void switchMitglieder() {
        mainPage.anchorPane.setCenter(new Mitglieder());
    }
}
