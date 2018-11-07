package at.fhv.team2.member;

import at.fhv.sportsclub.controller.interfaces.IDepartmentController;
import at.fhv.sportsclub.controller.interfaces.IPersonController;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.dept.SportDTO;
import at.fhv.sportsclub.model.person.AddressDTO;
import at.fhv.sportsclub.model.person.ContactDTO;
import at.fhv.sportsclub.model.person.PersonDTO;
import at.fhv.team2.DataProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Uray Örnek on 11/6/2018.
 */
public class Member extends HBox implements Initializable {

    //region UI-TexFields
    public TextField firstName;
    public TextField lastName;
    public TextField street;
    public TextField zipCode;
    public TextField city;
    public TextField dateOfBirth;
    public TextField emailAddress;
    public TextField phoneNumber;
    public TextField searchInput;
    //endregion
    //region UI-Table
    public TableView table;
    public TableColumn firstNameTable;
    public TableColumn lastNameTable;
    public TableColumn cityTable;
    //endregion

    public VBox vBoxSports;

    public Button saveButton;
    public Button changeButton;

    private List<CheckBox> sportChecks = new ArrayList<>();
    private ObservableList<PersonViewModel> personTableList;
    private List<PersonViewModel> persons;

    public Member() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Member.fxml"));
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

        IPersonController personControllerInstance = DataProvider.get().getPersonControllerInstance();
        IDepartmentController departmentController = DataProvider.get().getDepartmentControllerInstance();

        ArrayList<PersonDTO> personEntries = null;
        ArrayList<SportDTO> sportEntries = null;
        try {
             personEntries = personControllerInstance.getAllEntries();
             sportEntries = departmentController.getAllSportEntries();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        persons = new ArrayList<>();

        for (PersonDTO personEntry : personEntries) {
            persons.add(new PersonViewModel(personEntry.getId(), personEntry.getFirstName(), personEntry.getLastName(),
                    personEntry.getAddress().getCity(), personEntry.getAddress().getStreet(),
                    personEntry.getAddress().getZipCode(), personEntry.getContact().getPhoneNumber()));
        }

        /*
        persons.add(new PersonViewModel("Uray", "Örnek", "Hohenems", "Schubert", "6845", "864345684323"));
        persons.add(new PersonViewModel("Alex", "Zeyer", "Dornbirn", "Hämmerle", "6800", "999523446101"));
        persons.add(new PersonViewModel("Lukas", "Stadlmann", "Dornbirn", "Rauf", "6800", "11111111988"));
        persons.add(new PersonViewModel("Robert", "Schmitzer", "Dornbirn", "Runter", "6860", "621699"));
        persons.add(new PersonViewModel("Marco", "Simeth", "Dornbirn", "Straße", "6845", "694613161"));
        persons.add(new PersonViewModel("Melanie", "Zumtobel", "Dornbirn", "Autsch", "6845", "6966456"));
        */

        addSport(sportEntries);

        saveButton.setDisable(true);
        changeButton.setDisable(true);

        addMemberToTable(persons);
    }

    /**
     * adding checkboxes based on the sports in the database
     *
     * @param sports
     */
    private void addSport(List<SportDTO> sports) {
        for (SportDTO sp : sports) {
            CheckBox checkBox = new CheckBox(sp.getName());
            sportChecks.add(checkBox);
            vBoxSports.getChildren().add(checkBox);
        }
    }

    private void addMemberToTable(List<PersonViewModel> person){

        personTableList = FXCollections.observableArrayList(person);

        firstNameTable.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameTable.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        cityTable.setCellValueFactory(new PropertyValueFactory<>("city"));

        table.setItems(personTableList);
    }

    /**
     * searching for the selected sports
     *
     * @return a list of String which are selected
     */
    private List<String> getSelectedSports() {
        List<String> selectedSports = new ArrayList<>();
        for (CheckBox cb : sportChecks) {
            if (cb.isSelected()) {
                selectedSports.add(cb.getText());
            }
        }
        return selectedSports;
    }

    /**
     * for the detail view on the right side
     *
     * @param mouseEvent
     */
    public void clickItem(MouseEvent mouseEvent) {

        changeButton.setDisable(false);
        saveButton.setDisable(true);

        PersonViewModel pr = (PersonViewModel) table.getSelectionModel().getSelectedItem();

        firstName.setText(pr.getFirstName());
        lastName.setText(pr.getLastName());
        city.setText(pr.getCity());
        street.setText(pr.getStreet());
        zipCode.setText(pr.getZipCode());
        phoneNumber.setText(pr.getPhoneNumber());

    }

    /**
     * get's triggerd if someone presses the NEW MEMBER Button
     *
     * @param event
     */
    public void newMember(ActionEvent event) {

        saveButton.setDisable(false);
        changeButton.setDisable(true);

        firstName.setText("");
        lastName.setText("");
        city.setText("");
        street.setText("");
        zipCode.setText("");
        phoneNumber.setText("");

    }

    /**
     * get's triggerd if someone prsses the CHANGE Button
     *
     * @param event
     */
    public void changeData(ActionEvent event) {
        PersonViewModel pr = (PersonViewModel) table.getSelectionModel().getSelectedItem();

        pr.setFirstName(firstName.getText());
        pr.setLastName(lastName.getText());
        pr.setCity(city.getText());
        pr.setStreet(street.getText());
        pr.setPhoneNumber(phoneNumber.getText());
        pr.setZipCode(zipCode.getText());

        //TODO: RMI CONTROLLER der die real Person verändert in der Datenbank

        changeButton.setDisable(true);

        saveData(pr.getId());
    }

    private void saveData(String id) {
        IPersonController personController = DataProvider.get().getPersonControllerInstance();
        AddressDTO addressDTO = new AddressDTO(null, street.getText(), zipCode.getText(), city.getText());
        ContactDTO contactDTO = new ContactDTO(null, phoneNumber.getText(), "placeholder@example.com");
        PersonDTO personDTO = new PersonDTO(id, firstName.getText(), lastName.getText(), LocalDate.now(), addressDTO, contactDTO);

        ResponseMessageDTO response = null;
        try {
            response = personController.saveOrUpdateEntry(personDTO);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println(response.toString());
    }

    /**
     * get's trigger if some presses the SAVE Button
     *
     * @param event
     */
    public void saveMember(ActionEvent event) {

        PersonViewModel pr = new PersonViewModel(null, firstName.getText(),lastName.getText(),city.getText(),street.getText(),zipCode.getText(),phoneNumber.getText());
        saveButton.setDisable(true);
        personTableList.add(pr);

        saveData(null);
    }


    public void searchMemberByFirstName() {
        String searchCriteria = searchInput.getText();

        String searchPattern = "^" + searchCriteria + "\\gi";
        List<PersonViewModel> filteredPersons = new LinkedList<>();

        if (searchCriteria.isEmpty()) {
            addMemberToTable(persons);
            return;
        }

        for (PersonViewModel person : persons) {
            if (person.getFirstName().matches(searchCriteria)) {
                filteredPersons.add(person);
            }
        }

        addMemberToTable(filteredPersons);
    }
}
