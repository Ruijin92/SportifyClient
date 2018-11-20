package at.fhv.team2.member;

import at.fhv.sportsclub.controller.interfaces.IDepartmentController;
import at.fhv.sportsclub.controller.interfaces.IPersonController;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.dept.SportDTO;
import at.fhv.sportsclub.model.person.AddressDTO;
import at.fhv.sportsclub.model.person.ContactDTO;
import at.fhv.sportsclub.model.person.PersonDTO;
import at.fhv.team2.DataProvider;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

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
    //region UI-Button
    public Button saveButton;
    public Button changeButton;
    //endregion
    //region UI-DatePicker
    public DatePicker dateOfBirth;
    //endregion

    public VBox vBoxSports;

    private List<CheckBox> sportChecks = new ArrayList<>();
    private ObservableList<PersonViewModel> personTableList;
    private List<PersonViewModel> persons;

    private ValidationSupport validation = new ValidationSupport();

    private IPersonController personControllerInstance;
    private IDepartmentController departmentController;

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

        this.personControllerInstance = DataProvider.get().getPersonControllerInstance();
        this.departmentController = DataProvider.get().getDepartmentControllerInstance();

        ArrayList<PersonDTO> personEntries = null;
        ArrayList<SportDTO> sportEntries = null;

        try {
            personEntries = personControllerInstance.getAllEntries();
            sportEntries = departmentController.getAllSportEntries();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        persons = new ArrayList<>();

        /*for (PersonDTO personEntry : personEntries) {
            persons.add(new PersonViewModel(personEntry.getId(), personEntry.getFirstName(), personEntry.getLastName(),
                    personEntry.getAddress().getCity(), personEntry.getAddress().getStreet(),
                    personEntry.getAddress().getZipCode(), personEntry.getContact().getPhoneNumber()));
        }*/

        for (PersonDTO personEntry : personEntries) {
            List<String> sports = new LinkedList<>();
            for (SportDTO sport : personEntry.getSports()) {
                sports.add(sport.getName());
            }
            persons.add(new PersonViewModel(personEntry.getId(), personEntry.getFirstName(), personEntry.getLastName(),
                    personEntry.getAddress().getCity(),null,
                   personEntry.getAddress().getZipCode(), null,  sports));
        }

        addSport(sportEntries);
        addMemberToTable(persons);

        saveButton.setDisable(true);
        changeButton.setDisable(true);


        validateTheInput();
    }

    /**
     * for the detail view on the right side
     *
     * @param mouseEvent
     */
    public void clickItem(MouseEvent mouseEvent) {

        PersonViewModel pr = (PersonViewModel) table.getSelectionModel().getSelectedItem();
        PersonDTO entryDetails = new PersonDTO();

        try {
            entryDetails = personControllerInstance.getEntryDetails(pr.getId());
            if (entryDetails.getResponse() != null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Loading failed");
                alert.setContentText("Loading of member failed");
                alert.showAndWait();
                return;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        firstName.setText(entryDetails.getFirstName());
        lastName.setText(entryDetails.getLastName());
        city.setText(entryDetails.getAddress().getCity());
        street.setText(entryDetails.getAddress().getStreet());
        zipCode.setText(entryDetails.getAddress().getZipCode());
        phoneNumber.setText(entryDetails.getContact().getPhoneNumber());
        emailAddress.setText(entryDetails.getContact().getEmailAddress());
        dateOfBirth.setValue(LocalDate.parse(entryDetails.getDateOfBirth().toString()));

        vBoxSports.getChildren().clear();
        setAllCheckboxesToUnselect();

        for (SportDTO sport : entryDetails.getSports()) {
            for (CheckBox sportCheck : sportChecks) {
                if (sportCheck.getId().equals(sport.getName())) {
                    sportCheck.setSelected(true);
                }
            }
        }

        for (CheckBox sportCheck : sportChecks) {
            vBoxSports.getChildren().add(sportCheck);
        }

        changeButton.setDisable(false);
        saveButton.setDisable(true);
    }

    /**
     * get's triggerd if someone presses the NEW MEMBER Button
     *
     * @param event
     */
    public void newMember(ActionEvent event) {
        setAllCheckboxesToUnselect();
        saveButton.setDisable(false);
        changeButton.setDisable(true);

        firstName.setText("");
        lastName.setText("");
        dateOfBirth.setValue(LocalDate.of(2000, 1, 1));
        city.setText("");
        street.setText("");
        zipCode.setText("");
        phoneNumber.setText("");
        emailAddress.setText("");
    }

    /**
     * get's triggerd if someone presses the CHANGE Button
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

        if (!validation.isInvalid()) {
            saveData(pr.getId());
            changeButton.setDisable(true);
        }
    }

    /**
     * get's trigger if some presses the SAVE Button
     *
     * @param event
     */
    public void saveMember(ActionEvent event) {
        //TODO: Fix NullPointerException (Sports)
        PersonViewModel pr = new PersonViewModel(null, firstName.getText(), lastName.getText(), city.getText(), street.getText(), zipCode.getText(), phoneNumber.getText(), null);

        if (!validation.isInvalid()) {
            saveData(null);
            personTableList.add(pr);
            saveButton.setDisable(true);
        }
    }

    public void searchMemberByFirstName() {
        String searchCriteria = searchInput.getText();

        Pattern p = Pattern.compile("^" + searchCriteria, Pattern.CASE_INSENSITIVE);
        Matcher m;

        if (searchCriteria.isEmpty()) {
            addMemberToTable(persons);
            return;
        }

        List<PersonViewModel> filteredPersons =
                persons.stream()
                        .filter(t -> p.matcher(t.getFirstName()).find()
                                || p.matcher(t.getLastName()).find()
                                || p.matcher(t.getZipCode()).find()
                                || p.matcher(t.getCity()).find()
                                || t.getSports().stream().allMatch(s -> p.matcher(s).find()))
                        .collect(toList());

        addMemberToTable(filteredPersons);
    }

    private void saveData(String id) {
        IPersonController personController = DataProvider.get().getPersonControllerInstance();
        List<SportDTO> sports = new LinkedList<>();

        AddressDTO addressDTO = new AddressDTO(null, street.getText(), zipCode.getText(), city.getText());
        ContactDTO contactDTO = new ContactDTO(null, phoneNumber.getText(), "placeholder@example.com");
        SportDTO sportDTO = new SportDTO();
        PersonDTO personDTO = new PersonDTO(id, firstName.getText(), lastName.getText(), LocalDate.now(), addressDTO, contactDTO, sports, null);

        ResponseMessageDTO response = null;

        try {
            response = personController.saveOrUpdateEntry(personDTO);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println(response.toString());
    }

    /**
     * adding checkboxes based on the sports in the database
     *
     * @param sports
     */
    private void addSport(List<SportDTO> sports) {
        for (SportDTO sp : sports) {
            CheckBox checkBox = new CheckBox(sp.getName());
            checkBox.setId(sp.getName());
            sportChecks.add(checkBox);
            vBoxSports.getChildren().add(checkBox);
        }
    }

    /**
     * adding Member to the TableView
     *
     * @param person is a List of PersonViewModels as an input
     */
    private void addMemberToTable(List<PersonViewModel> person) {

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

    private void setAllCheckboxesToUnselect() {
        for (CheckBox sportCheck : sportChecks) {
            sportCheck.setSelected(false);
        }
    }

    /**
     * Validates the MemberDetail input
     */
    private void validateTheInput() {

        validation.registerValidator(firstName, Validator.createEmptyValidator("Have to be filled"));
        validation.registerValidator(lastName, Validator.createEmptyValidator("Have to be filled"));
        validation.registerValidator(street, Validator.createEmptyValidator("Have to be filled"));
        validation.registerValidator(zipCode, Validator.createEmptyValidator("Have to be filled"));
        validation.registerValidator(city, Validator.createEmptyValidator("Have to be filled"));
        validation.registerValidator(phoneNumber, Validator.createEmptyValidator("Have to be filled"));
        validation.registerValidator(dateOfBirth, Validator.createEmptyValidator("Have to be selected"));
        validation.registerValidator(emailAddress, Validator.createEmptyValidator("Have to be filled"));
    }
}
