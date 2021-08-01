package sample.controllers.menu;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.controllers.Controller;
import sample.entity.Users;
import sample.Main;
import java.sql.Connection;

public class PatientViewController implements Controller {
    private Users user;
    private Connection connection;
    private Main mainApp;

    @FXML
    public Button logout;

    @FXML
    void onPatientCard() {
        mainApp.openPage("patientsCard/PatientPatientsCard");
    }

    @FXML
    void onSpecializationList() {
        mainApp.openPage("specializations/PatientSpecializations");
    }

    @FXML
    void onRecordsList() {
        mainApp.openPage("records/PatientSchedule");
    }

    @Override
    public void onShow() {}

    @FXML
    public void onExit() {
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.close();
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public void setConnection(Connection con) {
        this.connection = con;
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
}
