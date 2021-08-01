package sample.controllers.specializations;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.controllers.Controller;
import sample.entity.Specializations;
import sample.entity.Users;
import sample.Main;
import sample.service.SpecializationsService;

import java.sql.Connection;
import java.util.List;

public class MainDoctorSpecializationsStatisticsController implements Controller {
    @FXML
    private TableView<Specializations> sourceTable;

    @FXML
    private TableColumn<Specializations, String> name;

    @FXML
    public Button exit;

    private Users user;
    private Connection connection;
    private Main mainApp;
    private SpecializationsService specializationsService;

    @FXML
    public void onShow() {
        name.setCellValueFactory(new PropertyValueFactory<>("Name"));

        specializationsService = new SpecializationsService(connection);

        sourceTable.getItems().clear();
        List<Specializations> list = specializationsService.getSpecializations();
        for (Specializations specializations : list) {
            if ((!specializations.getName().equals("ГлавВрач")) && (!specializations.getName().equals("СотрудникРегистратуры"))) {
                sourceTable.getItems().add(specializations);
            }
        }
    }

    @FXML
    public void onMostWanted() {
        specializationsService = new SpecializationsService(connection);

        name.setCellValueFactory(new PropertyValueFactory<>("Name"));

        sourceTable.getItems().clear();
        List<Specializations> list = specializationsService.getSpecializationsSortedByRating();
        for (Specializations specializations : list) {
            sourceTable.getItems().add(specializations);
        }
    }

    @FXML
    public void onExit() {
        mainApp.openPage("menu/MainDoctorStatisticsMain");
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
