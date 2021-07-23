package sample.controllers.patientsCard;

import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.entity.DiagnosisInfo;
import sample.entity.Patient;
import sample.service.DiagnosisInfoService;
import sample.service.PatientService;
import java.util.List;

public class PatientCardPatientViewController extends PatientsCardController {
    @Override
    public void onShow() {
        diagnosisDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        diagnosisComment.setCellValueFactory(new PropertyValueFactory<>("Comment"));

        patientService = new PatientService(connection);
        diagnosisInfoService = new DiagnosisInfoService(connection);

        Patient patient = new Patient();
        try {
            int id = user.getIdUser();
            patient = patientService.getPatientByUserId(id);

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

        sourceTable.getItems().clear();
        List<DiagnosisInfo> list = diagnosisInfoService.getDiagnosisInfoByPatientId(patient.getIdPatient());
        for (DiagnosisInfo diagnosisInfo : list) {
            sourceTable.getItems().add(diagnosisInfo);
        }
    }

    @FXML
    public void onChange(){
        mainApp.openPage("PatientPatientsInfoChange");
    }

    @FXML
    public void onExit() {
        mainApp.openPage("PatientMain");
    }
}
