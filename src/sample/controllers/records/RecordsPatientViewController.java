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

public class RecordsPatientViewController implements Controller {
    @FXML
    private TableView<Records> sourceTable;

    @FXML
    private TableColumn<Records, Date> date;

    @FXML
    private TableColumn<Records, Time> time;

    @FXML
    private TableView<Staff> docTable;

    @FXML
    private TableColumn<Staff, String> doctorLastname;

    @FXML
    private TableColumn<Staff, String> doctorFirstname;

    @FXML
    private TableColumn<Staff, String> doctorFathername;

    @FXML
    public Button exit;

    private Users user;
    private Connection connection;
    private Main mainApp;
    private RecordService recordService;
    private PatientService patientService;
    private StaffService staffService;

    @FXML
    public void onShow() {
        patientService = new PatientService(connection);
        recordService = new RecordService(connection);
        staffService = new StaffService(connection);

        doctorLastname.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        doctorFirstname.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        doctorFathername.setCellValueFactory(new PropertyValueFactory<>("FatherName"));
        date.setCellValueFactory(new PropertyValueFactory<>("Date"));
        time.setCellValueFactory(new PropertyValueFactory<>("Time"));

        Patient patient = new Patient();
        try {
            int id = user.getIdUser();
            patient = patientService.getPatientByUserId(id);
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        sourceTable.getItems().clear();
        docTable.getItems().clear();
        ArrayList<Records> list = recordService.getRecordsByPatientId(patient.getIdPatient());
        ArrayList<Staff> docList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
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
        for (Staff staff : docList) {
            docTable.getItems().add(staff);
        }
    }

    @FXML
    public void onExit() {
        mainApp.openPage("menu/PatientMain");
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
