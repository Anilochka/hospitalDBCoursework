package sample.controllers.menu;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.controllers.Controller;
import sample.entity.Users;
import sample.Main;
import java.sql.Connection;

public class DoctorMainViewController implements Controller {
    private Users user;
    private Connection connection;
    private Main mainApp;

    @FXML
    public Button logout;

    @FXML
    void onMyRecords() {
        mainApp.openPage("records/DoctorSchedule");
    }

    @FXML
    void onPatientCard() {
        mainApp.openPage("patientsCard/DoctorPatientsCard");
    }

    @FXML
    void onMakeDiagnosis() {
        mainApp.openPage("diagnosis/AddNewDiagnosisInfo");
    }

    @FXML
    void onMakeRecord() {
        mainApp.openPage("records/AddNewRecordDoctorView");
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
