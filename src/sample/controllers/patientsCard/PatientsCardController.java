package sample.controllers.patientsCard;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import sample.controllers.Controller;
import sample.entity.DiagnosisInfo;
import sample.entity.Patient;
import sample.entity.Users;
import sample.Main;
import sample.service.DiagnosisInfoService;
import sample.service.PatientService;
import java.sql.Connection;
import java.util.List;

public abstract class PatientsCardController implements Controller {
    @FXML
    public TableView<DiagnosisInfo> sourceTable;

    @FXML
    public TableColumn<DiagnosisInfo, String> diagnosisDate;

    @FXML
    public TableColumn<DiagnosisInfo, String> diagnosisComment;

    @FXML
    public Text firstname;

    @FXML
    public Text lastname;

    @FXML
    public Text fathername;

    @FXML
    public Text birthdate;

    @FXML
    public Text adress;

    @FXML
    public Text phone;

    @FXML
    public Text passport;

    @FXML
    public Text policy;

    @FXML
    public Button exit;

    @FXML
    public ComboBox<String> patientsChoice;

    protected Users user;
    protected Connection connection;
    protected Main mainApp;
    protected PatientService patientService;
    protected DiagnosisInfoService diagnosisInfoService;
    private int patId;

    @FXML
    public void onShow() {
        patientService = new PatientService(connection);
        patientsChoice.setOnMouseClicked(event -> {
            ObservableList<String> patientsFIO = FXCollections.observableArrayList(patientService.getPatientsFIO());

            ObservableList<String> newPat = patientsFIO;
            ObservableList<String> oldPat = patientsChoice.getItems();
            if (!newPat.equals(oldPat)) {
                patientsChoice.setItems(newPat);
            }
        });

        patientsChoice.setOnAction(event -> {
            String patient = patientsChoice.getValue();
            if (patient != null) {
                String[] subStr;
                String delimeter = " ";
                subStr = patient.split(delimeter);
                patId = patientService.getPatientByFIO(subStr[0], subStr[1], subStr[2]).getIdPatient();
                onChoose();
            }
        });
    }

    @FXML
    public void onChoose() {
        diagnosisDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        diagnosisComment.setCellValueFactory(new PropertyValueFactory<>("Comment"));

        Patient patient = new Patient();
        try {
            patient = patientService.getPatientByPatientId(patId);

            firstname.setText(patient.getFirstName());
            lastname.setText(patient.getLastName());
            fathername.setText(patient.getFatherName());
            birthdate.setText(patient.getBirthDate().toString());
            adress.setText(patient.getAdress());
            phone.setText(patient.getPhone());
            passport.setText(patient.getPassportNumber());
            policy.setText(patient.getPolicyNumber());
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        diagnosisInfoService = new DiagnosisInfoService(connection);

        sourceTable.getItems().clear();
        List<DiagnosisInfo> list = diagnosisInfoService.getDiagnosisInfoByPatientId(patient.getIdPatient());
        for (DiagnosisInfo diagnosisInfo : list) {
            sourceTable.getItems().add(diagnosisInfo);
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
