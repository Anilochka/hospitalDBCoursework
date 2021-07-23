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

public abstract class RecordsController implements Controller {
    @FXML
    private TableView<Records> sourceTable;

    @FXML
    private TableColumn<Records, Date> date;

    @FXML
    private TableColumn<Records, Time> time;

    @FXML
    private TableView<Patient> patTable;

    @FXML
    private TableColumn<Patient, String> pLastName;

    @FXML
    private TableColumn<Patient, String> pFirstName;

    @FXML
    private TableView<Staff> docTable;

    @FXML
    private TableColumn<Staff, String> dLastName;

    @FXML
    private TableColumn<Staff, String> dFirstName;

    @FXML
    public Button exit;

    protected Users user;
    protected Connection connection;
    protected Main mainApp;
    protected RecordService recordService;
    protected StaffService staffService;
    protected PatientService patientService;

    @FXML
    public void onShow() {
        date.setCellValueFactory(new PropertyValueFactory<>("Date"));
        time.setCellValueFactory(new PropertyValueFactory<>("Time"));
        pLastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        pFirstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        dLastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        dFirstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));

        connection = mainApp.getConnection();
        recordService = new RecordService(connection);
        staffService = new StaffService(connection);
        patientService = new PatientService(connection);

        sourceTable.getItems().clear();
        patTable.getItems().clear();
        docTable.getItems().clear();

        List<Records> list = recordService.getRecords();
        ArrayList<Patient> patList = new ArrayList<>();
        ArrayList<Staff> docList = new ArrayList<>();
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

            if (list.get(i) != null) {
                int docId = list.get(i).getDoctorId();

                if (docId != 0) {
                    Staff doc1 = staffService.getStaffByStaffId(docId);
                    if (doc1 != null) {
                        docList.add(doc1);
                    }
                }else if (docId == 0){
                    Staff deleted = new Staff("-", "-", "-");
                    docList.add(deleted);
                }
            }
        }

        for (Records records : list) {
            sourceTable.getItems().add(records);
        }
        for (Patient patient : patList) {
            patTable.getItems().add(patient);
        }
        for (Staff staff : docList) {
            docTable.getItems().add(staff);
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
