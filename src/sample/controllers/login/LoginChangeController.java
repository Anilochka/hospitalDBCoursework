package sample.controllers.login;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.controllers.Controller;
import sample.entity.Users;
import sample.Main;
import sample.service.UserService;
import java.sql.Connection;

public class LoginChangeController implements Controller {
    private Main mainApp;
    private Users user;
    private Connection connection;

    @FXML
    public TextField uLogin;

    @FXML
    public PasswordField uPassword;

    @FXML
    public PasswordField uPassword1;

    public UserService userService;

    public LoginChangeController() {}

    @FXML
    void onChange() {
        String login = uLogin.getText();
        String password = uPassword.getText();
        String password1 = uPassword1.getText();

        userService = new UserService(connection);

        userService.getUserByLogin(login);
        if (!password.equals(password1)) {
            mainApp.showWarningAlert("Введенные пароли не совпадают!");
            return;
        }

        Users user = userService.getUserByLogin(login);
        Users newUser = new Users(user.getIdUser(), login, password, user.getRole());
        userService.updateUser(newUser);

        mainApp.showInformationAlert("Пароль успешно изменен!");
        onExit();
    }

    @Override
    public void onShow() {}

    @FXML
    public void onExit() {
        mainApp.openPage("login/Authorization");
    }

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
