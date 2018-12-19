package at.fhv.team2.member;

import at.fhv.sportsclub.interfacesReturn.IDepartmentControllerReturn;
import at.fhv.sportsclub.interfacesReturn.IPersonControllerReturn;
import at.fhv.sportsclub.model.common.ListWrapper;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.dept.SportDTO;
import at.fhv.sportsclub.model.person.AddressDTO;
import at.fhv.sportsclub.model.person.ContactDTO;
import at.fhv.sportsclub.model.person.PersonDTO;
import at.fhv.team2.DataProviderFactory;
import at.fhv.team2.IDataProvider;
import at.fhv.team2.PageProvider;
import at.fhv.team2.roles.Permission;
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
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.*;
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
    public Button newMemeberButton;
    //endregion
    //region UI-DatePicker
    public DatePicker dateOfBirth;
    //endregion

    public VBox vBoxSports;

    private List<CheckBox> sportChecks = new ArrayList<>();
    private ObservableList<PersonViewModel> personTableList;
    private List<PersonViewModel> persons;

    private ValidationSupport validation = new ValidationSupport();

    private IPersonControllerReturn personControllerInstance;
    private IDepartmentControllerReturn departmentController;
    private IDataProvider dataProvider;

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
        dataProvider = DataProviderFactory.getCurrentDataProvider();

        saveButton.setVisible(Permission.getPermission().createMemberPermission());
        changeButton.setVisible(Permission.getPermission().createMemberPermission());
        newMemeberButton.setVisible(Permission.getPermission().createMemberPermission());

        this.personControllerInstance = dataProvider.getPersonControllerInstance();
        this.departmentController = dataProvider.getDepartmentControllerInstance();

        ArrayList<PersonDTO> personEntries = null;
        ArrayList<SportDTO> sportEntries = null;

        try {
            ListWrapper<PersonDTO> allEntries = personControllerInstance.getAllEntries(dataProvider.getSession());
            personEntries = personControllerInstance.getAllEntries(dataProvider.getSession()).getContents();
            sportEntries = departmentController.getAllSportEntries(dataProvider.getSession()).getContents();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        persons = new ArrayList<>();

        for (PersonDTO personEntry : personEntries) {
            List<String> sports = new LinkedList<>();
            if (personEntry.getSports() != null) {
                for (SportDTO sport : personEntry.getSports()) {
                    sports.add(sport.getName());
                }
            }
            persons.add(new PersonViewModel(personEntry.getId(), personEntry.getFirstName(), personEntry.getLastName(),
                    personEntry.getAddress().getCity(), null,
                    personEntry.getAddress().getZipCode(), null, sports));
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
            entryDetails = personControllerInstance.getEntryDetails(dataProvider.getSession(), pr.getId());
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

        if (entryDetails.getSports() != null) {
            for (SportDTO sport : entryDetails.getSports()) {
                for (CheckBox sportCheck : sportChecks) {
                    if (sportCheck.getId().equals(sport.getName())) {
                        sportCheck.setSelected(true);
                    }
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
    public void changeData(ActionEvent event) throws RemoteException {
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
    public void saveMember(ActionEvent event) throws RemoteException {
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
                                || t.getSports().stream().anyMatch(s -> p.matcher(s).find()))
                        .collect(toList());

        addMemberToTable(filteredPersons);
    }

    private void saveData(String id) throws RemoteException {
        IPersonControllerReturn personController = dataProvider.getPersonControllerInstance();
        List<SportDTO> sports = new LinkedList<>();
        ListWrapper<SportDTO> allSportEntries = this.departmentController.getAllSportEntries(dataProvider.getSession());
        ArrayList<SportDTO> contents = allSportEntries.getContents();
        HashMap<String, String> allSports = new HashMap<>();
        for (SportDTO content : contents) {
            allSports.put(content.getName(), content.getId());
        }

        AddressDTO addressDTO;
        ContactDTO contactDTO;
        PersonDTO personDTO;
        ResponseMessageDTO response = null;
        if (id == null) {
            addressDTO = new AddressDTO(null, street.getText(), zipCode.getText(), city.getText());
            contactDTO = new ContactDTO(null, phoneNumber.getText(), emailAddress.getText());

            List<String> selectedSports = getSelectedSports();
            for (String selectedSport : selectedSports) {
                sports.add(new SportDTO(allSports.get(selectedSport), selectedSport, null, null));
            }
            personDTO = new PersonDTO(id, firstName.getText(), lastName.getText(), dateOfBirth.getValue(), addressDTO, contactDTO, sports, null, null);
        } else {
            PersonDTO entryDetails = null;
            try {
                entryDetails = personController.getEntryDetails(dataProvider.getSession(), id);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            addressDTO = new AddressDTO(entryDetails.getAddress().getId(), street.getText(), zipCode.getText(), city.getText());
            contactDTO = new ContactDTO(entryDetails.getContact().getId(), phoneNumber.getText(), emailAddress.getText());

            List<String> selectedSports = getSelectedSports();
            for (SportDTO sport : entryDetails.getSports()) {
                for (String selectedSport : selectedSports) {
                    if (selectedSport.equals(sport.getName())) {
                        sports.add(sport);
                    }
                }
            }
            for (String selectedSport : selectedSports) {
                boolean newSportDTO = true;
                for (SportDTO sport : entryDetails.getSports()) {
                    if (selectedSport.equals(sport.getName())) {
                        newSportDTO = false;
                    }
                }
                if (newSportDTO == true) {
                    sports.add(new SportDTO(allSports.get(selectedSport), selectedSport, null, null));
                }
            }
            personDTO = new PersonDTO(entryDetails.getId(), firstName.getText(), lastName.getText(), dateOfBirth.getValue(), addressDTO, contactDTO, sports, null, null);
        }

        try {
            response = personController.saveOrUpdateEntry(dataProvider.getSession(), personDTO);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        PageProvider.getPageProvider().switchMember();
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
