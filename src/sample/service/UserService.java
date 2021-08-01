package sample.service;

import sample.AlertMessage;
import sample.entity.*;

import java.sql.*;
import java.util.ArrayList;

public class UserService {
    private Connection connection;
    private AlertMessage alertMessage;

    public UserService(Connection connection) {
        this.connection = connection;
        this.alertMessage = new AlertMessage();
    }

    public ArrayList<Users> getUsers() {
        ArrayList<Users> users = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT Login, Password FROM Users");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String login = rs.getString(1);
                String password = rs.getString(2);
                users.add(new Users(login, password));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting users list", e);
        }
        return users;
    }

    public void addUser(String login, String password) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Users (Login, Password, RegistrationDate) " +
                    "VALUES (?, ?, ?)");
            ps.setString(1, login);
            ps.setString(2, password);
            ps.setDate(3, new java.sql.Date(System.currentTimeMillis()));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding user", e);
        }
    }

    public Users getUserByLogin(String login) {
        Users user = new Users();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT IdUser, Password FROM Users WHERE Login = ?");
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String password = rs.getString("Password");
                int id = rs.getInt("IdUser");

                Role role = null;

                ps = connection.prepareStatement("SELECT * FROM Patients WHERE UserId = ?");
                ps.setInt(1, id);
                rs = ps.executeQuery();

                if (rs.next()) {
                    role = Role.PATIENT;
                } else {
                    ps = connection.prepareStatement("SELECT SpecializationId FROM Staff WHERE UserId = ?");
                    ps.setInt(1, id);
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        int specId = rs.getInt("SpecializationId");
                        if (specId == 5) {
                            role = Role.MAINDOCTOR;
                        } else if (specId == 6) {
                            role = Role.RECEPTIONIST;
                        } else {
                            role = Role.DOCTOR;
                        }
                    }
                }
                return new Users(id, login, password, role);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting user", e);
        }
        return user;
    }

    public void updateUser(Users user) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE Users SET Login = ?, Password = ? " +
                    "WHERE IdUser = ?");
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getIdUser());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating user", e);
        }
    }

    public void deleteUser(Users user) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM Users WHERE Login = ?");
            ps.setString(1, user.getLogin());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user", e);
        }
    }
}
