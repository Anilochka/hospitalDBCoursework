package sample.service;

import sample.AlertMessage;
import sample.entity.DiagnosisInfo;
import java.sql.*;
import java.util.ArrayList;

public class DiagnosisInfoService {
    private Connection connection;
    private AlertMessage alertMessage;

    DiagnosisService diagnosisService;

    public DiagnosisInfoService(Connection connection) {
        this.connection = connection;
        alertMessage = new AlertMessage();
    }

    public ArrayList<DiagnosisInfo> getDiagnosisInfo() {
        ArrayList<DiagnosisInfo> diagnosisInfos = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT dI.PatientCardId, dI.DiagnosisId, " +
                    "dI.Date, dI.Comment FROM DiagnosisInfo dI\n");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int patientCardId = rs.getInt("PatientCardId");
                int diagnosisId = rs.getInt("DiagnosisId");
                Date date = rs.getDate("Date");
                String comment = rs.getString("Comment");
                diagnosisInfos.add(new DiagnosisInfo(patientCardId, diagnosisId, date, comment));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting diagnosisInfo list!", e);
        }
        return diagnosisInfos;
    }

    public ArrayList<DiagnosisInfo> getDiagnosisInfoByPatientId(int id) {
        ArrayList<DiagnosisInfo> diagnosisInfos = new ArrayList<>();
        diagnosisService = new DiagnosisService(connection);

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM DiagnosisInfo dI INNER JOIN " +
                    "PatientsCard ON dI.PatientCardId = PatientsCard.IdCard INNER JOIN Patients P ON " +
                    "PatientsCard.IdCard = P.CardId WHERE P.IdPatient = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int patientCardId = rs.getInt("PatientCardId");
                int diagnosisId = rs.getInt("DiagnosisId");
                Date date = rs.getDate("Date");
                String comment = diagnosisService.getDiagnosisById(diagnosisId).getName();
                diagnosisInfos.add(new DiagnosisInfo(patientCardId, diagnosisId, date, comment));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting diagnosisInfo by patient ID", e);
        }
        return diagnosisInfos;
    }

    public void addDiagnosisInfo(DiagnosisInfo diagnosisInfo) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO DiagnosisInfo (PatientCardId, DiagnosisId, Date, Comment) " +
                    "VALUES(?, ?, ?, ?)");
            ps.setInt(1, diagnosisInfo.getPatientCardId());
            ps.setInt(2, diagnosisInfo.getDiagnosisId());
            ps.setDate(3, diagnosisInfo.getDate());
            ps.setString(4, diagnosisInfo.getComment());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error addind diagnosisInfo", e);
        }
    }

    public void updateDiagnosisInfo(DiagnosisInfo diagnosisInfo) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE DiagnosisInfo SET ?,?,?,?");
            ps.setInt(1, diagnosisInfo.getPatientCardId());
            ps.setInt(2, diagnosisInfo.getDiagnosisId());
            ps.setDate(3, diagnosisInfo.getDate());
            ps.setString(4, diagnosisInfo.getComment());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating diagnosisInfo", e);
        }
    }

    public void deleteDiagnosisInfo(DiagnosisInfo diagnosisInfo) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM DiagnosisInfo WHERE IdDiagnosisInfo = ?");
            ps.setInt(1, diagnosisInfo.getIdDiagnosisInfo());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating diagnosisInfo", e);
        }
    }
}
