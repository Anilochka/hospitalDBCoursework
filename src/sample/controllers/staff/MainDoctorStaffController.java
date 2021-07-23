package sample.controllers.staff;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.controllers.Controller;
import sample.entity.Staff;
import sample.entity.Users;
import sample.Main;
import sample.service.StaffService;

import java.sql.Connection;
import java.util.List;

public class MainDoctorStaffController implements Controller {
    @FXML
    private TableView<Staff> sourceTable;

    @FXML
    private TableColumn<Staff, String> lastname;

    @FXML
    private TableColumn<Staff, String> firstname;

    @FXML
    private TableColumn<Staff, String> fathername;

    @FXML
    private TableColumn<Staff, Integer> specialization;

    @FXML
    private TableColumn<Staff, String> phone;

    @FXML
    private TableColumn<Staff, Integer> id;

    @FXML
    public Button exit;

    private Users user;
    private Connection connection;
    private Main mainApp;
    private StaffService staffService;

    @FXML
    public void onShow() {
        lastname.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        firstname.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        fathername.setCellValueFactory(new PropertyValueFactory<>("FatherName"));
        specialization.setCellValueFactory(new PropertyValueFactory<>("SpecializationId"));
        phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        id.setCellValueFactory(new PropertyValueFactory<>("IdStaff"));

        staffService = new StaffService(connection);

        sourceTable.getItems().clear();
        List<Staff> list = staffService.getStaff();
        for (Staff staff : list) {
            sourceTable.getItems().add(staff);
        }
    }

    @FXML
    public void onAdd() {
        mainApp.openPage("AddNewStaff");
    }

    @FXML
    public void onDelete() {
        Staff staff = sourceTable.getSelectionModel().getSelectedItem();
        if (staff != null) {
            staffService.deleteStaff(staff.getIdStaff());
            onShow();
        }
    }

    @FXML
    public void onExit() {
        mainApp.openPage("MainDoctorMain");
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
