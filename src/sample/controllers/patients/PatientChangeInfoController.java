package sample.controllers.patients;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.controllers.Controller;
import sample.entity.Patient;
import sample.entity.Role;
import sample.entity.Users;
import sample.Main;
import sample.service.PatientService;
import sample.service.UserService;
import java.sql.Connection;

public class PatientChangeInfoController implements Controller {
    @FXML
    public TextField Firstname;

    @FXML
    public TextField Lastname;

    @FXML
    public TextField Fathername;

    @FXML
    public DatePicker BirthDate;

    @FXML
    public TextField Adress;

    @FXML
    public TextField Phone;

    @FXML
    public TextField PassNum;

    @FXML
    public TextField PolicyNum;

    @FXML
    public TextField Login;

    @FXML
    public PasswordField Password;

    @FXML
    public PasswordField Password1;

    private Users user;
    private Connection connection;
    private Main mainApp;
    private PatientService patientService;
    private UserService userService;

    public PatientChangeInfoController() {}

    public void onShow() {
        try {
            patientService = new PatientService(connection);
            userService = new UserService(connection);
            Patient patient = patientService.getPatientByUserId(user.getIdUser());

            Firstname.setText(patient.getFirstName());
            Lastname.setText(patient.getLastName());
            Fathername.setText(patient.getFatherName());
            BirthDate.getEditor().setText(patient.getBirthDate().toString());
            Adress.setText(patient.getAdress());
            Phone.setText(patient.getPhone());
            PassNum.setText(patient.getPassportNumber());
            PolicyNum.setText(patient.getPolicyNumber());
            Login.setText(user.getLogin());
            Password.setText(user.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onDoneClicked(ActionEvent event) {
        patientService = new PatientService(connection);
        userService = new UserService(connection);
        Patient patient = patientService.getPatientByUserId(user.getIdUser());

        String firstname = Firstname.getText();
        String lastname = Lastname.getText();
        String fathername = Fathername.getText();
        String adress = Adress.getText();
        String phone = Phone.getText();
        String passnum = PassNum.getText();
        String policynum = PolicyNum.getText();
        String login = Login.getText();
        String password = Password.getText();
        String password1 = Password1.getText();

        if (!firstname.equals(patient.getFirstName()) || !lastname.equals(patient.getLastName())
                || !fathername.equals(patient.getFatherName())
                || !adress.equals(patient.getAdress()) || !phone.equals(patient.getPhone())
                || !passnum.equals(patient.getPassportNumber()) || !policynum.equals(patient.getPolicyNumber())
                || !login.equals(user.getLogin()) || !password1.isEmpty()) {

            Patient newPatient = new Patient(firstname, lastname, fathername, patient.getBirthDate(), adress, phone, passnum, policynum);

            patientService.updatePatient(newPatient, patient);

            if (!login.equals(user.getLogin()) || !password1.isEmpty()) {
                if (!password.equals(password1)) {
                    mainApp.showWarningAlert("Введенные пароли не совпадают!");
                    return;
                }

                try {
                    Users newUser = new Users(user.getIdUser(), login, password, Role.PATIENT);
                    userService.updateUser(newUser);

                    onExit();
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
            mainApp.showInformationAlert("Изменение данных прошло успешно!");
        }
    }

    @FXML
    public void onExit() {
        mainApp.openPage("PatientPatientsCard");
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
