package sample.service;

import sample.AlertMessage;
import sample.entity.Records;

import java.sql.*;
import java.util.ArrayList;

public class RecordService {
    private Connection connection;
    private AlertMessage alertMessage;

    public RecordService(Connection connection) {
        this.connection = connection;
        this.alertMessage = new AlertMessage();
    }

    public ArrayList<Records> getRecords() {
        ArrayList<Records> records = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Records");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int patId = rs.getInt("PatientId");
                int docId = rs.getInt("DoctorId");
                Date date = rs.getDate("Date");
                Time time = rs.getTime("Time");
                int recId = rs.getInt("IdRecord");
                records.add(new Records(recId, patId, docId, date, time));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting records list", e);
        }
        return records;
    }

    public ArrayList<Records> getRecordsByPatientId(int patientId) {
        ArrayList<Records> records = new ArrayList<>();

        try {
            PreparedStatement stat = connection.prepareStatement("SELECT * FROM Records WHERE PatientId = ?");
            stat.setInt(1, patientId);
            ResultSet rs = stat.executeQuery();

            while (rs.next()) {
                int docId = rs.getInt("DoctorId");
                Date date = rs.getDate("Date");
                Time time = rs.getTime("Time");
                int recId = rs.getInt("IdRecord");
                records.add(new Records(recId, patientId, docId, date, time));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting records list by patient ID", e);
        }
        return records;
    }

    public ArrayList<Records> getRecordsByDoctorId(int doctorId) {
        ArrayList<Records> records = new ArrayList<>();

        try {
            PreparedStatement stat = connection.prepareStatement("SELECT * FROM Records WHERE DoctorId = ?");
            stat.setInt(1, doctorId);
            ResultSet rs = stat.executeQuery();

            while (rs.next()) {
                int patId = rs.getInt("PatientId");
                Date date = rs.getDate("Date");
                Time time = rs.getTime("Time");
                int recId = rs.getInt("IdRecord");
                records.add(new Records(recId, patId, doctorId, date, time));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting records list by doctor ID", e);
        }
        return records;
    }

    public void addRecord(Records records) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Records(PatientId, DoctorId, Date, Time) " +
                    "VALUES (?, ?, ?, ?)");
            ps.setInt(1, records.getPatientId());
            ps.setInt(2, records.getDoctorId());
            ps.setDate(3, records.getDate());
            ps.setTime(4, records.getTime());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding record", e);
        }
    }

    void updateRecord(Records newRecord, Records oldRecord) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE Records SET PatientId = ?, DoctorId = ?, " +
                    "Date = ?, Time = ? WHERE IdRecord = ?");
            ps.setInt(1, newRecord.getPatientId());
            ps.setInt(2, newRecord.getDoctorId());
            ps.setDate(3, newRecord.getDate());
            ps.setTime(4, newRecord.getTime());
            ps.setInt(5, oldRecord.getIdRecord());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating record", e);
        }
    }

    public void deleteRecord(Records records) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM Records WHERE IdRecord = ?");
            ps.setInt(1, records.getIdRecord());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting record", e);
        }
    }
}
