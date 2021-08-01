package sample.controllers.patients;

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
import sample.entity.Patient;
import sample.entity.Users;
import sample.Main;
import sample.service.DiagnosisService;
import sample.service.PatientService;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class MainDoctorPatientsStatisticsController implements Controller {
    @FXML
    private TableView<Patient> sourceTable;

    @FXML
    private TableColumn<Patient, String> lastname;

    @FXML
    private TableColumn<Patient, String> firstname;

    @FXML
    private TableColumn<Patient, String> fathername;

    @FXML
    private TableColumn<Patient, String> phone;

    @FXML
    private TableColumn<Patient, Integer> id;

    @FXML
    private ComboBox<String> diagnosisChoice;

    @FXML
    public Button exit;

    @FXML
    public DatePicker Date;

    private Users user;
    private Connection connection;
    private Main mainApp;
    private PatientService patientService;
    private DiagnosisService diagnosisService;
    private int diagnId;

    @FXML
    public void onShow() {
        diagnosisService = new DiagnosisService(connection);
        patientService = new PatientService(connection);
        diagnosisChoice.setOnMouseClicked(event -> {
            ObservableList<String> dignNames = FXCollections.observableArrayList(diagnosisService.getDiagnosesNames());

            ObservableList<String> newDiagn = dignNames;
            ObservableList<String> oldDiagn = diagnosisChoice.getItems();
            if (!newDiagn.equals(oldDiagn)) {
                diagnosisChoice.setItems(newDiagn);
            }
        });

        diagnosisChoice.setOnAction(event -> {
            String diagnosis = diagnosisChoice.getValue();
            if (diagnosis != null) {
                diagnId = diagnosisService.getDiagnosisByName(diagnosis).getId();
            }
        });

        lastname.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        firstname.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        fathername.setCellValueFactory(new PropertyValueFactory<>("FatherName"));
        phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        id.setCellValueFactory(new PropertyValueFactory<>("IdPatient"));

        sourceTable.getItems().clear();
        List<Patient> list = patientService.getPatients();
        for (Patient patient : list) {
            sourceTable.getItems().add(patient);
        }
    }

    @FXML
    public void onOld() {
        lastname.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        firstname.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        fathername.setCellValueFactory(new PropertyValueFactory<>("FatherName"));
        phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        id.setCellValueFactory(new PropertyValueFactory<>("IdPatient"));

        patientService = new PatientService(connection);

        sourceTable.getItems().clear();
        List<Patient> list = patientService.getOldPatients();
        for (Patient patient : list) {
            sourceTable.getItems().add(patient);
        }
    }

    @FXML
    public void onAtDay() {
        patientService = new PatientService(connection);
        String date = String.valueOf(Date.getValue());
        DateFormat df = new SimpleDateFormat("yyy-MM-dd");

        java.sql.Date tmpDate;
        try {
            java.util.Date utilDate = df.parse(date);
            java.sql.Date sqlDate = convert(utilDate);
            tmpDate = sqlDate;

            lastname.setCellValueFactory(new PropertyValueFactory<>("LastName"));
            firstname.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
            fathername.setCellValueFactory(new PropertyValueFactory<>("FatherName"));
            phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
            id.setCellValueFactory(new PropertyValueFactory<>("IdPatient"));

            sourceTable.getItems().clear();
            List<Patient> list = patientService.getPatientsByDateOfAppointment(tmpDate);
            for (Patient patient : list) {
                sourceTable.getItems().add(patient);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onDiagnosis() {
        this.user = mainApp.getUser();

        lastname.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        firstname.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        fathername.setCellValueFactory(new PropertyValueFactory<>("FatherName"));
        phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        id.setCellValueFactory(new PropertyValueFactory<>("IdPatient"));

        connection = mainApp.getConnection();
        patientService = new PatientService(connection);

        sourceTable.getItems().clear();
        List<Patient> list = patientService.getPatientsByTheirDiagnosis(diagnId);
        for (Patient patient : list) {
            sourceTable.getItems().add(patient);
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
