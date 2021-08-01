package sample.controllers.staff;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.controllers.Controller;
import sample.entity.Role;
import sample.entity.Specializations;
import sample.entity.Staff;
import sample.entity.Users;
import sample.Main;
import sample.service.SpecializationsService;
import sample.service.StaffService;
import sample.service.UserService;
import java.beans.PropertyChangeSupport;
import java.sql.Connection;

public class StaffRegistrationController implements Controller {
    @FXML
    public TextField Firstname;

    @FXML
    public TextField Lastname;

    @FXML
    public TextField Fathername;

    @FXML
    public TextField Phone;

    @FXML
    public ComboBox<String> specializationsChoice;

    @FXML
    public TextField Login;

    @FXML
    public PasswordField Password;

    private Users user;
    private Connection connection;
    private Main mainApp;
    private StaffService staffService;
    private UserService userService;
    private SpecializationsService specializationsService;
    private int specId;

    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public StaffRegistrationController() {}

    @FXML
    public void onShow() {
        specializationsService = new SpecializationsService(connection);
        specializationsChoice.setOnMouseClicked(event -> {
            ObservableList<String> specNames = FXCollections.observableArrayList(specializationsService.getSpecializationsNames());

            ObservableList<String> newSpec = specNames;
            ObservableList<String> oldSpec = specializationsChoice.getItems();
            if (!newSpec.equals(oldSpec)) {
                specializationsChoice.setItems(newSpec);
            }
        });

        specializationsChoice.setOnAction(event -> {
            String specialization = specializationsChoice.getValue();
            if (specialization != null) {
                specId = specializationsService.getSpecializationByName(specialization).getIdSpecialization();
                System.out.println(specId);
            }
        });
    }

    @FXML
    public void onAdd() {
        staffService = new StaffService(connection);
        userService = new UserService(connection);

        String firstname = Firstname.getText();
        String lastname = Lastname.getText();
        String fathername = Fathername.getText();
        String phone = Phone.getText();
        Specializations specializations = specializationsService.getSpecializationById(specId);
        String login = Login.getText();
        String password = Password.getText();

        if (firstname.isEmpty() || lastname.isEmpty() || fathername.isEmpty() || phone.isEmpty()
                || specializations == null || login.isEmpty() || password.isEmpty()) {
            mainApp.showWarningAlert("Введите все обязательные поля");
            return;
        }
        
        userService.addUser(login, password);
        int userID = userService.getUserByLogin(login).getIdUser();
        staffService.addStaff(new Staff(firstname, lastname, fathername, specId, phone, userID));

        onExit();
    }

    @FXML
    public void onExit() {
        mainApp.openPage("staff/MainDoctorStaff");
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
