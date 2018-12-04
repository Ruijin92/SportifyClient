package at.fhv.team2.wettkampf;

import at.fhv.team2.member.PersonViewModel;
import at.fhv.team2.wettkampf.ViewModels.CompetitionViewModel;
import at.fhv.team2.wettkampf.ViewModels.ParticipantViewModel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import org.controlsfx.control.ListSelectionView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static at.fhv.sportsclub.model.common.ModificationType.NONE;

public class TeamSquad extends VBox implements Initializable {

    private CompetitionViewModel comp;
    public ListSelectionView listView;

    public TeamSquad(CompetitionViewModel comp) {
        this.comp = comp;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TeamSquad.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addTestData();

        //FIXME: GET PART BY ID
        List<ParticipantViewModel> participants = comp.getParticipants();
        ParticipantViewModel partTeam = participants.stream().filter(x -> x.getId().equals("1")).collect(Collectors.toList()).get(0);

        List<PersonViewModel> persons = partTeam.getParticipants();
        listView.setSourceItems(FXCollections.observableArrayList(persons));
    }

    public void teamSquadSave(ActionEvent event) {

    }


    private void addTestData(){

        PersonViewModel p1 = new PersonViewModel("11","Ray","Örnek","Dornbirn",null,null,null,null);
        PersonViewModel p2 = new PersonViewModel("12","Luki","Dödel","Dornbirn",null,null,null,null);
        ArrayList<PersonViewModel> pvm = new ArrayList<>();
        comp.getParticipants().add(new ParticipantViewModel("1","FC-Dornbirn","FC-Dornbirn", pvm, NONE));
    }
}
