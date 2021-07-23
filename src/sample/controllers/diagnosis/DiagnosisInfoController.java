package sample.controllers.diagnosis;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sample.controllers.Controller;
import sample.entity.DiagnosisInfo;
import sample.entity.Users;
import sample.Main;
import sample.service.DiagnosisInfoService;
import sample.service.DiagnosisService;
import sample.service.PatientService;

import java.sql.Connection;

public class DiagnosisInfoController implements Controller {
    @FXML
    public ComboBox<String> patientsChoice;

    @FXML
    public ComboBox<String> diagnosisChoice;

    @FXML
    public TextField Comment;

    @FXML
    public Button exit;

    private Users user;
    private Connection connection;
    private Main mainApp;
    private DiagnosisInfoService diagnosisInfoService;
    private DiagnosisService diagnosisService;
    private PatientService patientService;
    private int patId;
    private int diagnId;

    @Override
    public void onShow() {
        patientService = new PatientService(connection);
        diagnosisInfoService = new DiagnosisInfoService(connection);
        diagnosisService = new DiagnosisService(connection);

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
            }
        });

        diagnosisChoice.setOnMouseClicked(event -> {
            ObservableList<String> diagnoses = FXCollections.observableArrayList(diagnosisService.getDiagnosesNames());

            ObservableList<String> newDiagn = diagnoses;
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
    }

    public void onDoneClicked(ActionEvent event) {
        String comment = Comment.getText();
        if (diagnId == 0 || patId == 0) {
            mainApp.showWarningAlert("Missing input, please check all fields!");
            return;
        }

        DiagnosisInfo diagnosisInfo = new DiagnosisInfo(patientService.getPatientByPatientId(patId).getCardId(),diagnId,
                new java.sql.Date(System.currentTimeMillis()), comment);
        diagnosisInfoService.addDiagnosisInfo(diagnosisInfo);
        onExit();
    }

    @FXML
    public void onExit() {
        mainApp.openPage("DoctorMain");
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
