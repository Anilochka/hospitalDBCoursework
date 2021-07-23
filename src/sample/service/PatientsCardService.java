package sample.service;

import sample.AlertMessage;
import sample.entity.PatientsCard;

import java.sql.*;
import java.util.ArrayList;

public class PatientsCardService {
    private Connection connection;
    private AlertMessage alertMessage;

    public PatientsCardService(Connection connection) {
        this.connection = connection;
        this.alertMessage = new AlertMessage();
    }

    public ArrayList<PatientsCard> getPatientsCards() {
        ArrayList<PatientsCard> patientsCards = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM PatientsCard c");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String comment = rs.getString("Comment");
                int id = rs.getInt("IdCard");
                patientsCards.add(new PatientsCard(id, comment));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting patients cards list", e);
        }
        return patientsCards;
    }

    public void addPatientsCard(PatientsCard patientsCard) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO PatientsCard (Comment) VALUES(?)");
            ps.setString(1, patientsCard.getComment());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding patients card", e);
        }
    }

    public void updatePatientsCard(PatientsCard patientsCard) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE PatientsCard SET Comment = ?");
            ps.setString(1, patientsCard.getComment());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating patients card", e);
        }
    }

    public void deletePatientsCard(PatientsCard patientsCard) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM PatientsCard WHERE IdCard = ?");
            ps.setInt(1, patientsCard.getIdCard());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating patients card", e);
        }
    }
}
