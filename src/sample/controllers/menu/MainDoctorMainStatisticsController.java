package sample.controllers.menu;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import sample.controllers.Controller;
import sample.entity.Users;
import sample.Main;
import java.sql.Connection;

public class MainDoctorMainStatisticsController implements Controller {
    private Users user;
    private Connection connection;
    private Main mainApp;

    @FXML
    public Button logout;

    @FXML
    void onPatients() {
        mainApp.openPage("patients/MainDoctorStatisticsPatients");
    }

    @FXML
    void onStaffInfo() {
        mainApp.openPage("staff/MainDoctorStatisticsStaff");
    }

    @FXML
    void onSpecializations() {
        mainApp.openPage("specializations/MainDoctorStatisticsSpecializations");
    }

    @Override
    public void onShow() {}

    @FXML
    public void onExit() {
        mainApp.openPage("menu/MainDoctorMain");
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
