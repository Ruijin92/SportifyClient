package at.fhv.team2.wettkampf;

import at.fhv.sportsclub.controller.interfaces.IDepartmentController;
import at.fhv.sportsclub.controller.interfaces.ITeamController;
import at.fhv.sportsclub.model.dept.LeagueDTO;
import at.fhv.sportsclub.model.dept.SportDTO;
import at.fhv.sportsclub.model.team.TeamDTO;
import at.fhv.team2.DataProvider;
import at.fhv.team2.teams.TeamViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
import org.controlsfx.control.ListSelectionView;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NewCompetition extends HBox implements Initializable {

    public HBox hBox;
    public ComboBox sportsCombo;
    public ComboBox leagueCombo;
    public DatePicker datePick;
    public TextField time;
    public ListSelectionView listView;
    public TextField teamName;

    private  ObservableList<ParticipantViewModel> participants;

    private ArrayList<TeamViewModel> teamViewModels;
    private ArrayList<SportViewModel> allSports;

    private ITeamController teamControllerInstance;
    private IDepartmentController departmentController;

    public NewCompetition(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/NewCompetition.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        listView.setSourceHeader(new Label("Verfügbare Mannschaften"));
        listView.setTargetHeader(new Label("Ausgewählte Mannschaften"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        leagueCombo.setDisable(true);

        this.departmentController = DataProvider.getDepartmentControllerInstance();

        addSports();

        sportsCombo.valueProperty().addListener(event ->  {
            leagueCombo.setDisable(false);
            addLeague();
            listView.getSourceItems().clear();
            listView.getTargetItems().clear();
        });

        leagueCombo.valueProperty().addListener(event -> {
            listView.getTargetItems().clear();
            listView.getSourceItems().clear();
            addParticipantsToList();
        });

    }

    public void addExternTeam(ActionEvent event) {
        listView.getSourceItems().add(new ParticipantViewModel(null,teamName.getText(),null,null));
    }
    public void saveComp(ActionEvent event){

    }

    private void addParticipantsToList(){
        participants = FXCollections.observableArrayList(addTestData());
        listView.getSourceItems().add(participants);
    }

    private void addSports(){

        ArrayList<SportDTO> sports = null;

        try {
            sports = this.departmentController.getAllSportEntriesFull(DataProvider.getSession()).getContents();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        this.allSports = new ArrayList<>();

        for (SportDTO sport: sports) {
            ArrayList<LeagueViewModel> leagues = new ArrayList<>();
            for (LeagueDTO league: sport.getLeagues()) {
                leagues.add(new LeagueViewModel(league.getId(), league.getName()));
            }
            this.allSports.add(new SportViewModel(sport.getId(), sport.getName(), leagues));
        }

        ObservableList<SportViewModel> allItems = FXCollections.observableArrayList(this.allSports);
        sportsCombo.setItems(allItems);
        sportsCombo.setConverter(new StringConverter<SportViewModel>() {
            @Override
            public String toString(SportViewModel object) {
                return object.getName();
            }

            @Override
            public SportViewModel fromString(String string) {
                return null;
            }
        });
    }

    private void addLeague(){

        SportViewModel sport = (SportViewModel) sportsCombo.getSelectionModel().getSelectedItem();
        ArrayList<LeagueViewModel> leagues = sport.getLeagues();

        ObservableList<LeagueViewModel> allItems = FXCollections.observableArrayList(leagues);
        leagueCombo.setItems(allItems);
        leagueCombo.setConverter(new StringConverter<LeagueViewModel>() {
            @Override
            public String toString(LeagueViewModel object) {
                return object.getName();
            }

            @Override
            public LeagueViewModel fromString(String string) {
                return null;
            }
        });
    }


    //FIXME: only for testing
    private List<ParticipantViewModel> addTestData(){

        List<ParticipantViewModel> list = new ArrayList<>();
        list.add(new ParticipantViewModel("22","FC-Dornbirn","FC",null));
        return list;
    }

    private void addTeamsFromLeagueToAvailableList(ActionEvent event) throws RemoteException {
        //TODO: Den Eintrag (Liga) holen, der ausgewählt wurde --> ID wird benötigt.

        this.teamControllerInstance = DataProvider.getTeamControllerInstance();
        ArrayList<TeamDTO> teams = new ArrayList<>();
        //teams = this.teamControllerInstance.getByLeague(DataProvider.getSession(), "1"); Methode im Backend fehlt noch, es sollt eine Liste an Teams zurückgeliefert werden, welche die LeagueID haben.

        teamViewModels = new ArrayList<>();
        for (TeamDTO team: teams) {
            teamViewModels.add(new TeamViewModel(team.getId(), team.getName(), team.getMembers(), team.getTrainers(), team.getLeague().getId(), team.getType()));
        }

        //TODO: Die teamViewModels auf die Liste der zur Verfügung stehenden Mannschaften binden.
    }
}
