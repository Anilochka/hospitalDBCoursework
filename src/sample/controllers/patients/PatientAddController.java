package sample.controllers.patients;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.controllers.Controller;
import sample.entity.Patient;
import sample.entity.PatientsCard;
import sample.entity.Users;
import sample.Main;
import sample.service.PatientService;
import sample.service.PatientsCardService;
import sample.service.UserService;
import java.sql.Connection;
import java.sql.Date;

public class PatientAddController implements Controller {
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
    private PatientsCardService patientsCardService;

    public PatientAddController() {}

    public void onDoneClicked(ActionEvent event) {
        patientService = new PatientService(connection);
        userService = new UserService(connection);
        patientsCardService = new PatientsCardService(connection);
        user = mainApp.getUser();

        String firstname = Firstname.getText();
        String lastname = Lastname.getText();
        String fathername = Fathername.getText();
        Date birthDate = Date.valueOf(BirthDate.getValue());
        String adress = Adress.getText();
        String phone = Phone.getText();
        String passnum = PassNum.getText();
        String policynum = PolicyNum.getText();
        String login = Login.getText();
        String password = Password.getText();
        String password1 = Password1.getText();

        if (!firstname.isEmpty() && !lastname.isEmpty() && BirthDate != null && !adress.isEmpty() && !phone.isEmpty()
                && !passnum.isEmpty() && !policynum.isEmpty() && !login.isEmpty() && !password.isEmpty() && !password1.isEmpty()) {
            if (!password.equals(password1)) {
                mainApp.showWarningAlert("Введенные пароли не совпадают!");
                return;
            }

            userService.addUser(login, password);
            patientsCardService.addPatientsCard(new PatientsCard());
            int cardId = patientsCardService.getPatientsCards().get(patientsCardService.getPatientsCards().size() - 1).getIdCard();
            Patient newPatient = new Patient(firstname, lastname, fathername, birthDate, adress,
                    phone, passnum, policynum, userService.getUserByLogin(login).getIdUser(), cardId);

            patientService.addPatient(newPatient);
            mainApp.showInformationAlert("Регистрация прошла успешно!");
            onExit();
        } else {
            mainApp.showWarningAlert("Введите все обязательные поля");
        }
    }

    @Override
    public void onShow() {}

    @FXML
    public void onExit() {
        mainApp.openPage("menu/RegistratureMain");
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

