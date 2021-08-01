package sample.service;

import sample.AlertMessage;
import sample.entity.Staff;

import java.sql.*;
import java.util.ArrayList;

public class StaffService {
    private Connection connection;
    private AlertMessage alertMessage;

    public StaffService(Connection connection) {
        this.connection = connection;
    }

    public ArrayList<Staff> getStaff() {
        ArrayList<Staff> staff = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Staff");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String fatherName = rs.getString("FatherName");
                int specializationId = rs.getInt("SpecializationId");
                String phone = rs.getString("Phone");
                int userId = rs.getInt("UserId");
                int id = rs.getInt("IdStaff");
                staff.add(new Staff(id, firstName, lastName, fatherName, specializationId, phone, userId));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting staff list", e);
        }
        return staff;
    }

    public ArrayList<Staff> getStaffSortedByRating() {
        ArrayList<Staff> staffs = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT IdStaff, COUNT(*) FROM Records JOIN Staff ON " +
                    "Staff.IdStaff=Records.DoctorId JOIN Specializations ON " +
                    "Specializations.IdSpecialization=Staff.SpecializationId GROUP BY Staff.IdStaff " +
                    "ORDER BY COUNT(*) DESC");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("IdStaff");
                Staff staff = getStaffByStaffId(id);
                staffs.add(staff);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting sorted by rating staff list", e);
        }
        return staffs;
    }

    public ArrayList<Staff> getStaffByWorkExperience() {
        ArrayList<Staff> staff = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT IdStaff, Users.RegistrationDate FROM Staff " +
                    "JOIN Users ON Staff.UserId=Users.IdUser GROUP BY IdStaff, RegistrationDate " +
                    "HAVING RegistrationDate<DATEADD(YEAR, -20, GETDATE())");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("IdStaff");
                staff.add(getStaffByStaffId(id));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting sorted by working experience staff list", e);
        }
        return staff;
    }

    public ArrayList<String> getStaffsFIO() {
        ArrayList<String> staffFIO = new ArrayList<>();
        ArrayList<Staff> staffs = getStaff();

        for (Staff staff : staffs) {
            staffFIO.add(staff.getLastName() + " " + staff.getFirstName() + " " + staff.getFatherName());
        }

        return staffFIO;
    }

    public Staff getStaffByUserId(int idUser) {
        Staff staff = new Staff();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Staff p WHERE p.UserId = ?");
            ps.setInt(1, idUser);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String fatherName = rs.getString("FatherName");
                int specId = rs.getInt("SpecializationId");
                String phone = rs.getString("Phone");
                int id = rs.getInt("IdStaff");
                return new Staff(id, firstName, lastName, fatherName, specId, phone, idUser);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting staff by user ID", e);
        }
        return staff;
    }

    public Staff getStaffByFIO(String lastName, String firstName, String fatherName) {
        Staff staff = new Staff();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Staff WHERE LastName = ? " +
                    "AND FirstName = ? AND FatherName = ?");
            ps.setString(1, lastName);
            ps.setString(2, firstName);
            ps.setString(3, fatherName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int specId = rs.getInt("SpecializationId");
                String phone = rs.getString("Phone");
                int userId = rs.getInt("UserId");
                int id = rs.getInt("IdStaff");
                return new Staff(id, firstName, lastName, fatherName, specId, phone, userId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting staff by FIO", e);
        }
        return staff;
    }

    public Staff getStaffByStaffId(int idStaff) {
        Staff staff = new Staff();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Staff WHERE IdStaff = ?");
            ps.setInt(1, idStaff);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String fatherName = rs.getString("FatherName");
                int specId = rs.getInt("SpecializationId");
                String phone = rs.getString("Phone");
                int userId = rs.getInt("UserId");
                return new Staff(idStaff, firstName, lastName, fatherName, specId, phone, userId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting staff by staff ID", e);
        }
        return staff;
    }

    public ArrayList<Staff> getStaffBySpecializationId(int idSpec) {
        ArrayList<Staff> staff = new ArrayList<>();
        try {
            String query = "SELECT * FROM Staff JOIN Specializations ON " +
                    "Staff.SpecializationId = Specializations.IdSpecialization WHERE Staff.SpecializationId=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, idSpec);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String fatherName = rs.getString("FatherName");
                String phone = rs.getString("Phone");
                int userId = rs.getInt("UserId");
                int id = rs.getInt("IdStaff");
                staff.add(new Staff(id, firstName, lastName, fatherName, idSpec, phone, userId));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting staff list by specialization ID", e);
        }
        return staff;
    }

    public ArrayList<Staff> getStaffByDateOfAppointment(Date date) {
        ArrayList<Staff> staff = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT DISTINCT * FROM Staff JOIN Records ON " +
                    "Staff.IdStaff=Records.DoctorId WHERE Date=?");
            ps.setDate(1, date);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String fatherName = rs.getString("FatherName");
                int specId = rs.getInt("SpecializationId");
                String phone = rs.getString("Phone");
                int userId = rs.getInt("UserId");
                int id = rs.getInt("IdStaff");
                staff.add(new Staff(id, firstName, lastName, fatherName, specId, phone, userId));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting staff list by date of appointment", e);
        }
        return staff;
    }

    public void addStaff(Staff staff) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Staff (FirstName, LastName, FatherName, " +
                    "SpecializationId, Phone, UserId) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, staff.getFirstName());
            ps.setString(2, staff.getLastName());
            ps.setString(3, staff.getFatherName());
            ps.setInt(4, staff.getSpecializationId());
            ps.setString(5, staff.getPhone());
            ps.setInt(6, staff.getUserId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding staff", e);
        }
    }

    void updateStaff(Staff newStaff, Staff oldStaff) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE Staff SET FirstName = ?, LastName = ?, " +
                    "FatherName = ?, SpecializationId = ?, Phone = ?, UserId = ? WHERE IdStaff = ?");
            ps.setString(1, newStaff.getFirstName());
            ps.setString(2, newStaff.getLastName());
            ps.setString(3, newStaff.getFatherName());
            ps.setInt(4, newStaff.getSpecializationId());
            ps.setString(5, newStaff.getPhone());
            ps.setInt(6, newStaff.getUserId());
            ps.setInt(7, oldStaff.getIdStaff());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating staff", e);
        }
    }

    public void deleteStaff(int idStaff) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM Staff WHERE IdStaff = ?");
            ps.setDouble(1, idStaff);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting staff", e);
        }
    }
}
