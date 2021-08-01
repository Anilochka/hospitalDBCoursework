package sample.controllers.records;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.controllers.Controller;
import sample.entity.Patient;
import sample.entity.Records;
import sample.entity.Staff;
import sample.entity.Users;
import sample.Main;
import sample.service.PatientService;
import sample.service.RecordService;
import sample.service.StaffService;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class RecordsDoctorViewController implements Controller {
    @FXML
    private TableView<Records> sourceTable;

    @FXML
    private TableColumn<Records, Date> date;

    @FXML
    private TableColumn<Records, Time> time;

    @FXML
    private TableView<Patient> patTable;

    @FXML
    private TableColumn<Patient, String> LastName;

    @FXML
    private TableColumn<Patient, String> FirstName;

    @FXML
    private TableColumn<Patient, String> FatherName;

    @FXML
    public Button exit;

    private Users user;
    private Connection connection;
    private Main mainApp;
    private RecordService recordService;
    private StaffService staffService;
    private PatientService patientService;

    @FXML
    public void onShow() {
        LastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        FirstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        FatherName.setCellValueFactory(new PropertyValueFactory<>("FatherName"));
        date.setCellValueFactory(new PropertyValueFactory<>("Date"));
        time.setCellValueFactory(new PropertyValueFactory<>("Time"));

        recordService = new RecordService(connection);
        staffService = new StaffService(connection);
        patientService = new PatientService(connection);

        Staff staff = new Staff();
        try {
            int id = user.getIdUser();
            staff = staffService.getStaffByUserId(id);
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        sourceTable.getItems().clear();
        patTable.getItems().clear();
        List<Records> list = recordService.getRecordsByDoctorId(staff.getIdStaff());
        ArrayList<Patient> patList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != null) {
                int patId = list.get(i).getPatientId();

                if (patId != 0) {
                    Patient pat1 = patientService.getPatientByPatientId(patId);
                    if (pat1 != null) {
                        patList.add(pat1);
                    }
                }
            }
        }

        for (Records records : list) {
            sourceTable.getItems().add(records);
        }
        for (Patient patient : patList) {
            patTable.getItems().add(patient);
        }
    }

    @FXML
    public void onExit() {
        mainApp.openPage("menu/DoctorMain");
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
