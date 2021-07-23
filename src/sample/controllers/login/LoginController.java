package sample.controllers.login;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.controllers.Controller;
import sample.entity.Role;
import sample.entity.Users;
import sample.Main;
import sample.service.UserService;
import java.sql.Connection;

public class LoginController implements Controller {
    private Main mainApp;
    private Users user;
    private Connection connection;

    @FXML
    public TextField uLogin;

    @FXML
    public PasswordField uPassword;

    @FXML
    void onLoginClicked() {
        String login = uLogin.getText();
        String password = uPassword.getText();

        try {
            UserService usersService = new UserService(connection);
            Users user = usersService.getUserByLogin(login);
            this.user = user;

            if (!user.getPassword().equals(password)) {
                mainApp.showWarningAlert("Неверный пароль!");
                return;
            }

            if(user.getRole() == Role.PATIENT) {
                mainApp.setUser(this.user);
                mainApp.openPage("PatientMain");
            } else if (user.getRole() == Role.DOCTOR) {
                mainApp.setUser(this.user);
                mainApp.openPage("DoctorMain");
            } else if (user.getRole() == Role.MAINDOCTOR) {
                mainApp.setUser(this.user);
                mainApp.openPage("MainDoctorMain");
            } else if (user.getRole() == Role.RECEPTIONIST) {
                mainApp.setUser(this.user);
                mainApp.openPage("RegistratureMain");
            }
        } catch (Exception e) {
            mainApp.showWarningAlert("Логин или пароль неверны!");
        }
    }

    @FXML
    void onPassChange() {
        mainApp.openPage("LoginChange");
    }

    @Override
    public void onShow() {}

    @Override
    public void onExit() {}

    @Override
    public void setUser(Users user) {
        this.user = user;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setMainApp(Main main) {
        this.mainApp = main;
    }

    public Users getUser() {
        return user;
    }
}
