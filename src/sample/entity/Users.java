package sample.entity;

import java.sql.Date;

public class Users {
    private int IdUser;
    private String Login;
    private String Password;
    private Date RegistrationDate;
    private Role Role;

    public Users() {}

    public Users(String login, String password) {
        this.Login = login;
        this.Password = password;
        this.RegistrationDate = new java.sql.Date(System.currentTimeMillis());
    }

    public Users(String login, String password, Role role) {
        this.Login = login;
        this.Password = password;
        this.RegistrationDate = new java.sql.Date(System.currentTimeMillis());
        this.Role = role;
    }

    public Users(int id, String login, String password, Role role) {
        this.IdUser = id;
        this.Login = login;
        this.Password = password;
        this.RegistrationDate = new java.sql.Date(System.currentTimeMillis());
        this.Role = role;
    }

    public int getIdUser() {
        return IdUser;
    }

    public String getLogin() {
        return Login;
    }

    public String getPassword() {
        return Password;
    }

    public Date getRegistrationDate() {
        return RegistrationDate;
    }

    public Role getRole() {
        return Role;
    }
}
