package at.fhv.team2.member;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Uray Örnek on 11/6/2018.
 */
public class Member extends HBox implements Initializable {

    public TableView table;
    public TextField vorname;
    public TextField nachname;
    public TextField adresse;
    public TextField plz;
    public TextField wohnort;
    public TextField date;
    public TextField email;
    public TextField tel;

    public VBox vBoxSports;

    public Button saveButton;
    public Button changeButton;

    private List<CheckBox> sportChecks = new ArrayList<>();

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

        //For Testing---------------------------
        List<String> sports = new ArrayList<>();
        sports.add("Tennis");
        sports.add("Fußball");
        sports.add("Volleyball");
        //--------------------------------------

      addSport(sports);

      saveButton.setDisable(true);
      changeButton.setDisable(true);
    }

    /**
     * adding checkboxes based on the sports in the database
     * @param sports
     */
    private void addSport(List<String> sports){
        for (String sp: sports) {
            CheckBox checkBox = new CheckBox(sp);
            sportChecks.add(checkBox);
            vBoxSports.getChildren().add(checkBox);
        }
    }

    /**
     * searching for the selected sports
     * @return a list of String which are selected
     */
    private List<String> getSelectedSports(){
        List<String> selectedSports = new ArrayList<>();
        for (CheckBox cb : sportChecks){
            if(cb.isSelected()){
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
        //Something like that
        /*
        TableEntry entry = (TableEntry) table.getSelectionModel().getSelectedItem();

        vorname.setText(entry.getFirstName());
        nachname.setText(entry.getLastName());
        adresse.setText(entry.getAddress());
        wohnort.setText(entry.getWohnort());
        date.setText(entry.getDateOfBirth().toString());
        email.setText(entry.getEmail());
        tel.setText(entry.getTel());
        */
    }

    /**
     * get's triggerd if someone presses the NEW MEMBER Button
     * @param event
     */
    public void newMember(ActionEvent event){
        saveButton.setDisable(false);

    }

    /**
     * get's triggerd if someone prsses the CHANGE Button
     * @param event
     */
    public void changeData(ActionEvent event){

        changeButton.setDisable(true);
    }

    /**
     * get's trigger if some presses the SAVE Button
     * @param event
     */
    public void saveMember(ActionEvent event){

        saveButton.setDisable(true);
    }
}
