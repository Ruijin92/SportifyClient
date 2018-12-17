package at.fhv.team2.wettkampf;

import at.fhv.sportsclub.controller.interfaces.ITeamController;
import at.fhv.sportsclub.controller.interfaces.ITournamentController;
import at.fhv.sportsclub.model.common.ModificationType;
import at.fhv.sportsclub.model.person.PersonDTO;
import at.fhv.sportsclub.model.team.TeamDTO;
import at.fhv.sportsclub.model.tournament.EncounterDTO;
import at.fhv.sportsclub.model.tournament.ParticipantDTO;
import at.fhv.sportsclub.model.tournament.SquadMemberDTO;
import at.fhv.sportsclub.model.tournament.TournamentDTO;
import at.fhv.team2.DataProvider;
import at.fhv.team2.PageProvider;
import at.fhv.team2.member.PersonViewModel;
import at.fhv.team2.wettkampf.ViewModels.CompetitionViewModel;
import at.fhv.team2.wettkampf.ViewModels.EncounterViewModel;
import at.fhv.team2.wettkampf.ViewModels.ParticipantViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import org.controlsfx.control.ListSelectionView;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TeamSquad extends VBox implements Initializable {

    private CompetitionViewModel comp;
    public ListSelectionView listView;

    private ITeamController teamController;
    private ITournamentController tournamentController;

    private boolean loadTeamSquadToChange;

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

    public TeamSquad(CompetitionViewModel comp, boolean changeTeamSquad) {
        this.loadTeamSquadToChange = changeTeamSquad;
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
        listView.setCellFactory(view -> {
            ListCell<PersonViewModel> cell = new ListCell<PersonViewModel>() {

                @Override
                public void updateItem(PersonViewModel item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        setText(item.getFirstName() + "    " + item.getLastName());
                        setGraphic(null);
                    }
                }
            };
            return cell;
        });

        this.teamController = DataProvider.getTeamControllerInstance();
        this.tournamentController = DataProvider.getTournamentControllerInstance();

        TeamDTO team = null;
        String id =  comp.getParticipants().get(0).getTeam();
        try {
            team = teamController.getEntryDetails(DataProvider.getSession(), id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        if (loadTeamSquadToChange) {
            TournamentDTO tournamentDTO = null;
            try {
                tournamentDTO = this.tournamentController.getEntryDetails(DataProvider.getSession(), comp.getId());
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            int indexUpdatingParticipants = -1;
            boolean participantAlreadyFound = false;
            for (int i = 0; i < tournamentDTO.getTeams().size() && !participantAlreadyFound; i++) {
                if (tournamentDTO.getTeams().get(i).getTeam().equals(comp.getParticipants().get(0).getTeam())) {
                    indexUpdatingParticipants = i;
                    participantAlreadyFound = true;
                }
            }


            List<SquadMemberDTO> loadedParticipants= tournamentDTO.getTeams().get(indexUpdatingParticipants).getParticipants();
            ArrayList<PersonViewModel> persons = new ArrayList<>();

            for (PersonDTO member : team.getMembers()) {
                boolean matched = false;
                for (SquadMemberDTO loadedParticipant : loadedParticipants) {
                    if (member.getFirstName().equals(loadedParticipant.getMember().getFirstName())) {
                        matched = true;
                    }
                }
                if (!matched) {
                    persons.add(new PersonViewModel(member.getId(), member.getFirstName(), member.getLastName(), null, null, null, null, null, false));
                }
            }

            ArrayList<PersonViewModel> loadedMembersOfTeamSquad = new ArrayList<>();
            for (SquadMemberDTO loadedParticipant : loadedParticipants) {
                loadedMembersOfTeamSquad.add(new PersonViewModel(loadedParticipant.getMember().getId(), loadedParticipant.getMember().getFirstName(), loadedParticipant.getMember().getLastName(),
                                            null, null, null, null, null, true));
            }

            listView.setSourceItems(FXCollections.observableArrayList(persons));
            listView.setTargetItems(FXCollections.observableArrayList(loadedMembersOfTeamSquad));
        } else {
            ArrayList<PersonViewModel> persons = new ArrayList<>();
            for (PersonDTO member : team.getMembers()) {
                persons.add(new PersonViewModel(member.getId(), member.getFirstName(), member.getLastName(), null, null, null, null, null, false));
            }
            listView.setSourceItems(FXCollections.observableArrayList(persons));
        }
    }

    public void teamSquadSave(ActionEvent event) throws RemoteException {
        ObservableList targetItems = listView.getTargetItems();
        List<PersonViewModel> list = (List<PersonViewModel>) targetItems.stream().collect(Collectors.toList());

        ArrayList<PersonDTO> participants = new ArrayList<>();
        ArrayList<SquadMemberDTO> parti = new ArrayList<>();
        for (PersonViewModel personViewModel : list) {
            PersonDTO person = new PersonDTO(personViewModel.getId(), null, null, null, null, null, null, null, null);
            parti.add(new SquadMemberDTO(person, false, null, personViewModel.getAlreadyAddedToSquad()));
        }
        SquadMemberDTO squad = new SquadMemberDTO();

        TournamentDTO tournamentDTO = this.tournamentController.getEntryDetails(DataProvider.getSession(), comp.getId());

        int indexUpdatingParticipants = -1;
        boolean participantAlreadyFound = false;
        for (int i = 0; i < tournamentDTO.getTeams().size() && !participantAlreadyFound; i++) {
            if (tournamentDTO.getTeams().get(i).getTeam().equals(comp.getParticipants().get(0).getTeam())) {
                indexUpdatingParticipants = i;
                participantAlreadyFound = true;
            }
        }

        //set SquadMemberDTO
        tournamentDTO.getTeams().get(indexUpdatingParticipants).setParticipants(parti);
        tournamentDTO.getTeams().get(indexUpdatingParticipants).setModificationType(ModificationType.INFORMEDANDMODIFIED);

        TournamentDTO savedTournament = this.tournamentController.saveOrUpdateEntry(DataProvider.getSession(), tournamentDTO);

        if (savedTournament.getResponse() == null) {
            PageProvider.getPageProvider().switchCompetitions();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Can't save the participants.");
        }
    }
}
