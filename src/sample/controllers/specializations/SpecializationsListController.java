package sample.controllers.specializations;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.controllers.Controller;
import sample.entity.Specializations;
import sample.entity.Users;
import sample.Main;
import sample.service.SpecializationsService;
import java.sql.Connection;
import java.util.List;

public abstract class SpecializationsListController implements Controller {
    @FXML
    protected TableView<Specializations> sourceTable;

    @FXML
    protected TableColumn<Specializations, String> specializationName;

    @FXML
    public Button exit;

    protected Users user;
    protected Connection connection;
    protected Main mainApp;
    protected SpecializationsService specializationsService;

    @FXML
    public void onShow() {
        specializationsService = new SpecializationsService(connection);
        specializationName.setCellValueFactory(new PropertyValueFactory<>("Name"));

        sourceTable.getItems().clear();
        List<Specializations> list = specializationsService.getSpecializations();
        for (Specializations specializations : list) {
            if ((!specializations.getName().equals("ГлавВрач")) && (!specializations.getName().equals("СотрудникРегистратуры"))) {
                sourceTable.getItems().add(specializations);
            }
        }
    }

    @FXML
    public abstract void onExit();

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
