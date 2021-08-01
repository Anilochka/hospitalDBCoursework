package sample.controllers.specializations;

import javafx.fxml.FXML;
import sample.entity.Specializations;
import sample.entity.Users;
import sample.Main;
import java.sql.Connection;

public class SpecializationsListMainDoctorViewController extends SpecializationsListController {
    @FXML
    public void onAdd() {
        mainApp.openPage("specializations/AddNewSpecialization");
    }

    @FXML
    public void onDelete() {
        Specializations specializations = sourceTable.getSelectionModel().getSelectedItem();

        if (specializations != null) {
            specializationsService.deleteSpecialization(specializations.getIdSpecialization());
            onShow();
        }
    }

    @Override
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
