package sample.service;

import sample.AlertMessage;
import sample.entity.Specializations;

import java.sql.*;
import java.util.ArrayList;

public class SpecializationsService {
    private Connection connection;
    private AlertMessage alertMessage;

    public SpecializationsService(Connection connection) {
        this.connection = connection;
        this.alertMessage = new AlertMessage();
    }

    public ArrayList<Specializations> getSpecializations() {
        ArrayList<Specializations> list = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Specializations");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("IdSpecialization");
                String name = rs.getString("Name");
                list.add(new Specializations(id, name));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting specializations list", e);
        }
        return list;
    }

    public ArrayList<String> getSpecializationsNames() {
        ArrayList<String> list = new ArrayList<>();
        ArrayList<Specializations> listS = getSpecializations();

        for (Specializations specialization : listS) {
            list.add(specialization.getName());
        }

        return list;
    }

    public Specializations getSpecializationById(int id) {
        Specializations specializations = new Specializations();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Specializations WHERE IdSpecialization = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String name = rs.getString("Name");
                return new Specializations(id, name);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting specialization by ID", e);
        }
        return specializations;
    }

    public Specializations getSpecializationByName(String name) {
        Specializations specializations = new Specializations();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Specializations WHERE Name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int idSpecialization = rs.getInt("IdSpecialization");;
                return new Specializations(idSpecialization, name);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting specialization by name", e);
        }
        return specializations;
    }

    public ArrayList<Specializations> getSpecializationsSortedByRating() {
        ArrayList<Specializations> specializations = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT Specializations.IdSpecialization, " +
                    "COUNT(*) FROM Records JOIN Staff ON Staff.IdStaff=Records.DoctorId JOIN Specializations ON" +
                    " Specializations.IdSpecialization=Staff.SpecializationId GROUP BY Specializations.IdSpecialization " +
                    "ORDER BY COUNT(*) DESC");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("IdSpecialization");
                specializations.add(getSpecializationById(id));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting sorted by rating specializations list", e);
        }
        return specializations;
    }

    public void addSpecialization(Specializations specializations) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Specializations(Name) VALUES (?)");
            ps.setString(1, specializations.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding specialization", e);
        }
    }

    public void updateSpecialization(Specializations newSpecialization, Specializations oldSpecialization) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE Specializations SET Name = ? " +
                    "WHERE IdSpecialization = ?");
            ps.setString(1, newSpecialization.getName());
            ps.setInt(2, oldSpecialization.getIdSpecialization());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating specialization", e);
        }
    }

    public void deleteSpecialization(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM Specializations WHERE IdSpecialization = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting specialization", e);
        }
    }
}
