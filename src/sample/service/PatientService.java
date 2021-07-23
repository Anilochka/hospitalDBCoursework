package sample.service;

import sample.AlertMessage;
import sample.entity.*;

import java.sql.*;
import java.util.ArrayList;

public class PatientService {
    private Connection connection;
    private AlertMessage alertMessage;

    public PatientService(Connection connection) {
        this.connection = connection;
        this.alertMessage = new AlertMessage();
    }

    public ArrayList<Patient> getPatients() {
        ArrayList<Patient> patients = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Patients");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String fatherName = rs.getString("FatherName");
                Date birthDate = rs.getDate("BirthDate");
                String address = rs.getString("Adress");
                String phone = rs.getString("Phone");
                String passNum = rs.getString("PassportNumber");
                String policyNum = rs.getString("PolicyNumber");
                int userId = rs.getInt("UserId");
                int cardId = rs.getInt("CardId");
                int patId = rs.getInt("IdPatient");
                patients.add(new Patient(patId, firstName, lastName, fatherName, birthDate, address, phone, passNum,
                        policyNum, userId, cardId));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting patients list", e);
        }
        return patients;
    }

    public ArrayList<String> getPatientsFIO() {
        ArrayList<String> patientsFIO = new ArrayList<>();
        ArrayList<Patient> patients = getPatients();

        for (Patient patient : patients) {
            patientsFIO.add(patient.getLastName() + " " + patient.getFirstName() + " " +  patient.getFatherName());
        }

        return patientsFIO;
    }

    public ArrayList<Patient> getOldPatients() {
        ArrayList<Patient> patients = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT IdPatient, BirthDate FROM Patients " +
                    "GROUP BY  IdPatient, BirthDate HAVING DATEDIFF(YEAR, BirthDate, GETDATE())>=60");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("IdPatient");
                patients.add(getPatientByPatientId(id));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting patients older 60 list", e);
        }
        return patients;
    }

    public ArrayList<Patient> getPatientsByTheirDiagnosis(int idDiagn) {
        ArrayList<Patient> patients = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT DISTINCT * FROM DiagnosisInfo JOIN Patients ON" +
                    " Patients.CardId=DiagnosisInfo.PatientCardId WHERE DiagnosisId = ?");
            ps.setInt(1, idDiagn);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("IdPatient");
                patients.add(getPatientByPatientId(id));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting patients list by diagnosis", e);
        }
        return patients;
    }

    public ArrayList<Patient> getPatientsByDateOfAppointment(Date date) {
        ArrayList<Patient> patients = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT DISTINCT IdPatient FROM Patients JOIN " +
                    "Records ON Patients.IdPatient=Records.PatientId WHERE Date=?");
            ps.setDate(1, date);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("IdPatient");
                patients.add(getPatientByPatientId(id));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting patients list by date of appointment", e);
        }
        return patients;
    }

    public Patient getPatientByUserId(int idUser) {
        Patient patient = new Patient();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Patients WHERE UserId = ?");
            ps.setInt(1, idUser);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String fatherName = rs.getString("FatherName");
                Date birthDate = rs.getDate("BirthDate");
                String address = rs.getString("Adress");
                String phone = rs.getString("Phone");
                String passNum = rs.getString("PassportNumber");
                String policyNum = rs.getString("PolicyNumber");
                int cardId = rs.getInt("CardId");
                int patId = rs.getInt("IdPatient");
                return new Patient(patId, firstName, lastName, fatherName, birthDate, address, phone, passNum,
                        policyNum, idUser, cardId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting patient by user ID", e);
        }
        return patient;
    }

    public Patient getPatientByPatientId(int idPat) {
        Patient patient = new Patient();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Patients WHERE IdPatient = ?");
            ps.setInt(1, idPat);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String fatherName = rs.getString("FatherName");
                Date birthDate = rs.getDate("BirthDate");
                String adress = rs.getString("Adress");
                String phone = rs.getString("Phone");
                String passNum = rs.getString("PassportNumber");
                String policyNum = rs.getString("PolicyNumber");
                int userId = rs.getInt("UserId");
                int cardId = rs.getInt("CardId");
                return new Patient(idPat, firstName, lastName, fatherName, birthDate, adress, phone, passNum,
                        policyNum, userId, cardId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting patient by patient ID", e);
        }
        return patient;
    }

    public Patient getPatientByFIO(String lastName, String firstName,String fatherName) {
        Patient patient = new Patient();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Patients WHERE LastName = ? AND " +
                    "FirstName = ? AND FatherName = ?");
            ps.setString(1, lastName);
            ps.setString(2, firstName);
            ps.setString(3, fatherName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Date birthDate = rs.getDate("BirthDate");
                String adress = rs.getString("Adress");
                String phone = rs.getString("Phone");
                String passNum = rs.getString("PassportNumber");
                String policyNum = rs.getString("PolicyNumber");
                int userId = rs.getInt("UserId");
                int cardId = rs.getInt("CardId");
                int patId = rs.getInt("IdPatient");
                return new Patient(patId, firstName, lastName, fatherName, birthDate, adress, phone, passNum,
                        policyNum, userId, cardId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting patient by FIO", e);
        }
        return patient;
    }

    public void addPatient(Patient patient) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Patients(FirstName, LastName, FatherName, " +
                    "BirthDate, Adress, Phone, PassportNumber, PolicyNumber, UserId, CardId) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, patient.getFirstName());
            ps.setString(2, patient.getLastName());
            ps.setString(3, patient.getFatherName());
            ps.setDate(4, patient.getBirthDate());
            ps.setString(5, patient.getAdress());
            ps.setString(6, patient.getPhone());
            ps.setString(7, patient.getPassportNumber());
            ps.setString(8, patient.getPolicyNumber());
            ps.setInt(9, patient.getUserId());
            ps.setInt(10, patient.getCardId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding patient", e);
        }
    }

     public void updatePatient(Patient newPat, Patient oldPat) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE Patients SET FirstName = ?, LastName = ?, " +
                    "FatherName = ?, Adress = ?, Phone = ?, BirthDate = ?, PassportNumber = ?, PolicyNumber = ? " +
                    "WHERE IdPatient = ?");
            ps.setString(1, newPat.getFirstName());
            ps.setString(2, newPat.getLastName());
            ps.setString(3, newPat.getFatherName());
            ps.setString(4, newPat.getAdress());
            ps.setString(5, newPat.getPhone());
            ps.setDate(6, newPat.getBirthDate());
            ps.setString(7, newPat.getPassportNumber());
            ps.setString(8, newPat.getPolicyNumber());
            ps.setInt(9, oldPat.getIdPatient());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating patient", e);
        }
    }

    public void deletePatient(Patient patient) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM Patients WHERE IdPatient = ?");
            ps.setInt(1, patient.getIdPatient());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting patient", e);
        }
    }
}
