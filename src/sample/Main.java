package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controllers.*;
import sample.entity.Users;

import java.io.IOException;
import java.sql.Connection;

public class Main extends Application {
    private Stage primaryStage;
    private Users user;
    private Connection connection;
    private AlertMessage alertMessage;

    @Override
    public void start(Stage primaryStage) {
        SourceProvider sourceProvider = new SourceProvider();
        connection = sourceProvider.getConnection();
        this.primaryStage = primaryStage;
        alertMessage = new AlertMessage();
        openPage("Authorization");
        primaryStage.show();
    }

    public Controller openPage(String path) {
        Controller controller = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxmlfiles/" + path + "Page.fxml"));
            primaryStage.setScene(new Scene(loader.load()));
            primaryStage.setTitle("Company");

            controller = loader.getController();
            controller.setMainApp(this);
            controller.setConnection(connection);
            controller.setUser(user);
            controller.onShow();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return controller;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Users getUser() {
        return this.user;
    }

    public Connection getConnection() {
        return connection;
    }

    public void showWarningAlert(String message) {
        alertMessage.showWarningAlert(message);
    }

    public void showInformationAlert(String message) {
        alertMessage.showInformationAlert(message);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
