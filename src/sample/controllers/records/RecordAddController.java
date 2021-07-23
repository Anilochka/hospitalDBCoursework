package sample.controllers.records;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import sample.controllers.Controller;
import sample.entity.Records;
import sample.entity.Users;
import sample.Main;
import sample.service.PatientService;
import sample.service.RecordService;
import sample.service.StaffService;
import java.sql.Connection;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public abstract class RecordAddController implements Controller {
    @FXML
    public ComboBox<String> patientsChoice;

    @FXML
    public DatePicker Date;

    @FXML
    public TextField Time;

    @FXML
    public Button exit;

    protected Users user;
    protected Connection connection;
    protected Main mainApp;
    protected RecordService recordService;
    protected PatientService patientService;
    protected StaffService staffService;
    protected int patId;
    protected int docId;

    @FXML
    public void onShow() {
        patientService = new PatientService(connection);
        staffService = new StaffService(connection);
        recordService = new RecordService(connection);

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

        getDocId();
    }

    public void onDoneClicked(ActionEvent event) {
        String date = Date.getEditor().getText();
        String time = Time.getText();

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

        java.sql.Date tmpDate;
        java.sql.Time tmpTime;
        try {
            java.util.Date utilDate = df.parse(date);
            java.sql.Date sqlDate = convert(utilDate);
            tmpDate = sqlDate;

            String[] subStr;
            String delimeter = ":";
            subStr = time.split(delimeter);

            int tiime = 0;
            tiime += (Integer.parseInt(subStr[0]) - 3) * 60 * 60 * 1000;
            tiime += Integer.parseInt(subStr[1]) * 60 * 1000;
            tmpTime = new Time(tiime);
        } catch (ParseException e) {
            e.printStackTrace();
            mainApp.showWarningAlert("Неправильный формат ввода даты!");
            return;
        }

        if (docId == 0 || patId == 0 || date.isEmpty() || time.isEmpty()) {
            mainApp.showWarningAlert("Все поля должны быть заполнены!");
            return;
        }

        Records record = new Records(patId, docId, tmpDate, tmpTime);
        recordService.addRecord(record);
        onExit();
    }

    public abstract int getDocId();

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
