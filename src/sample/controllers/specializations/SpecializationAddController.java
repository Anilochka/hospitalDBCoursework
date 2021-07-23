package sample.controllers.specializations;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sample.controllers.Controller;
import sample.entity.Specializations;
import sample.entity.Users;
import sample.Main;
import sample.service.SpecializationsService;
import java.beans.PropertyChangeSupport;
import java.sql.Connection;

public class SpecializationAddController implements Controller {
    @FXML
    public TextField Name;

    private Users user;
    private Connection connection;
    private Main mainApp;
    private SpecializationsService specializationsService;
    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public SpecializationAddController() {}

    public void onDoneClicked(ActionEvent event) {
        String name = Name.getText();
        specializationsService = new SpecializationsService(connection);

        if (name.isEmpty()) {
            mainApp.showWarningAlert("Missing input, please check all fields!");
            return;
        }

        Specializations specializations = new Specializations(name);
        specializationsService.addSpecialization(specializations);
        onExit();
    }

    @Override
    public void onShow() {}

    @FXML
    public void onExit() {
        mainApp.openPage("MainDoctorSpecializations");
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
