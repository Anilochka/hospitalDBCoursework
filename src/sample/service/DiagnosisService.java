package sample.service;

import sample.AlertMessage;
import sample.entity.Diagnosis;
import java.sql.*;
import java.util.ArrayList;

public class DiagnosisService {
    private Connection connection;
    private AlertMessage alertMessage;

    public DiagnosisService(Connection connection) {
        this.connection = connection;
        this.alertMessage = new AlertMessage();
    }

    public ArrayList<Diagnosis> getDiagnoses() {
        ArrayList<Diagnosis> diagnoses = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Diagnosis");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("Name");
                String comment = rs.getString("Comment");
                int id = rs.getInt("IdDiagnosis");
                diagnoses.add(new Diagnosis(id, name, comment));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting diagnoses list", e);
        }
        return diagnoses;
    }

    public ArrayList<String> getDiagnosesNames() {
        ArrayList<String> diagnosesNames = new ArrayList<>();
        ArrayList<Diagnosis> diagnoses = getDiagnoses();

        for (Diagnosis diagnosis : diagnoses) {
            diagnosesNames.add(diagnosis.getName());
        }

        return diagnosesNames;
    }

    public Diagnosis getDiagnosisByName(String name) {
        Diagnosis diagnoses = new Diagnosis();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Diagnosis WHERE Name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String comment = rs.getString("Comment");
                int id = rs.getInt("IdDiagnosis");
                diagnoses = new Diagnosis(id, name, comment);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting diagnosis by name", e);
        }
        return diagnoses;
    }

    public Diagnosis getDiagnosisById(int id) {
        Diagnosis diagnoses = new Diagnosis();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Diagnosis WHERE IdDiagnosis = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("Name");
                String comment = rs.getString("Comment");
                diagnoses = new Diagnosis(id, name, comment);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting diagnosis by ID", e);
        }
        return diagnoses;
    }

    public void addDiagnosis(Diagnosis diagnosis) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Diagnosis(name, comment) VALUES (?, ?)");
            ps.setString(1, diagnosis.getName());
            ps.setString(2, diagnosis.getComment());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding diagnosis", e);
        }
    }

    public void updateDiagnosis(Diagnosis diagnosis, String comment) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE Diagnosis SET Comment = ? WHERE Name = ?");
            ps.setString(1, comment);
            ps.setString(2, diagnosis.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating diagnosis", e);
        }
    }

    public void deleteDiagnosis(Diagnosis diagnosis) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM Diagnosis WHERE Name = ?");
            ps.setString(1, diagnosis.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting diagnosis", e);
        }
    }
}
