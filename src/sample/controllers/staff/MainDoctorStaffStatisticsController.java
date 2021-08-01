package sample.controllers.staff;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.controllers.Controller;
import sample.entity.Specializations;
import sample.entity.Staff;
import sample.entity.Users;
import sample.Main;
import sample.service.*;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class MainDoctorStaffStatisticsController implements Controller {
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
    private ComboBox<String> specializationsChoice;

    @FXML
    public Button exit;

    @FXML
    public DatePicker Date;

    private Users user;
    private Connection connection;
    private Main mainApp;
    private StaffService staffService;
    private SpecializationsService specializationsService;
    private int specId;

    @FXML
    public void onShow() {
        lastname.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        firstname.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        fathername.setCellValueFactory(new PropertyValueFactory<>("FatherName"));
        specialization.setCellValueFactory(new PropertyValueFactory<>("SpecializationId"));
        phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        id.setCellValueFactory(new PropertyValueFactory<>("IdStaff"));

        staffService = new StaffService(connection);
        specializationsService = new SpecializationsService(connection);

        sourceTable.getItems().clear();
        List<Staff> list = staffService.getStaff();
        for (Staff staff : list) {
            sourceTable.getItems().add(staff);
        }

        specializationsChoice.setOnMouseClicked(event -> {
            ObservableList<String> specNames = FXCollections.observableArrayList(specializationsService.getSpecializationsNames());

            ObservableList<String> newSpec = specNames;
            ObservableList<String> oldSpec = specializationsChoice.getItems();
            if (!newSpec.equals(oldSpec)) {
                specializationsChoice.setItems(newSpec);
            }
        });

        specializationsChoice.setOnAction(event -> {
            String specialization = specializationsChoice.getValue();
            if (specialization != null) {
                specId = specializationsService.getSpecializationByName(specialization).getIdSpecialization();
            }
        });
    }

    @FXML
    public void onMostWanted() {
        sourceTable.getItems().clear();
        List<Staff> list = staffService.getStaffSortedByRating();
        for (Staff staff : list) {
            sourceTable.getItems().add(staff);
        }
    }

    @FXML
    public void onOld() {
        sourceTable.getItems().clear();
        List<Staff> list = staffService.getStaffByWorkExperience();
        for (Staff staff : list) {
            sourceTable.getItems().add(staff);
        }
    }

    @FXML
    public void onSpecialization() {
        sourceTable.getItems().clear();
        Specializations specializations = specializationsService.getSpecializationById(specId);
        if (specializations != null) {
            List<Staff> list = staffService.getStaffBySpecializationId(specId);
            for (Staff staff : list) {
                sourceTable.getItems().add(staff);
            }
        }
    }

    @FXML
    public void onWorkAtDay() {
        String date = Date.getEditor().getText();
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

        java.sql.Date tmpDate;
        try {
            java.util.Date utilDate = df.parse(date);
            java.sql.Date sqlDate = convert(utilDate);
            tmpDate = sqlDate;

            sourceTable.getItems().clear();
            List<Staff> list = staffService.getStaffByDateOfAppointment(tmpDate);
            for (Staff staff : list) {
                sourceTable.getItems().add(staff);
            }
        } catch (ParseException e) {
            e.printStackTrace();
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

    private static java.sql.Date convert(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }
}
