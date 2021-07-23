package sample.controllers;

import sample.entity.Users;
import sample.Main;
import java.sql.Connection;

public interface Controller {
    void onShow();
    void onExit();
    void setUser(Users user);
    void setConnection(Connection con);
    void setMainApp(Main mainApp);
}
