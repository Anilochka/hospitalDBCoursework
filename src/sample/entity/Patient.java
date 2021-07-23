package sample.entity;

import java.sql.Date;

public class Patient {
    private int IdPatient;
    private String FirstName;
    private String LastName;
    private String FatherName;
    private Date BirthDate;
    private String Adress;
    private String Phone;
    private String PassportNumber;
    private String PolicyNumber;
    private int UserId;
    private int CardId;

    public Patient() {}

    public Patient(String firstName, String lastName, String fatherName, Date birthDate, String adress, String phone,
                   String passportNumber, String policyNumber) {
        this.FirstName = firstName;
        this.LastName = lastName;
        this.FatherName = fatherName;
        this.BirthDate = birthDate;
        this.Adress = adress;
        this.Phone = phone;
        this.PassportNumber = passportNumber;
        this.PolicyNumber = policyNumber;
    }

    public Patient(int id, String firstName, String lastName, String fatherName, Date birthDate, String adress, String phone,
                   String passportNumber, String policyNumber, int userId, int cardId) {
        this.IdPatient = id;
        this.FirstName = firstName;
        this.LastName = lastName;
        this.FatherName = fatherName;
        this.BirthDate = birthDate;
        this.Adress = adress;
        this.Phone = phone;
        this.PassportNumber = passportNumber;
        this.PolicyNumber = policyNumber;
        this.UserId = userId;
        this.CardId = cardId;
    }

    public Patient(String firstName, String lastName, String fatherName, Date birthDate, String adress, String phone,
                   String passportNumber, String policyNumber, int userId, int cardId) {
        this.FirstName = firstName;
        this.LastName = lastName;
        this.FatherName = fatherName;
        this.BirthDate = birthDate;
        this.Adress = adress;
        this.Phone = phone;
        this.PassportNumber = passportNumber;
        this.PolicyNumber = policyNumber;
        this.UserId = userId;
        this.CardId = cardId;
    }

    public int getIdPatient() {
        return IdPatient;
    }

    public void setId(int id) {
        this.IdPatient = id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getFatherName() {
        return FatherName;
    }

    public Date getBirthDate() {
        return BirthDate;
    }

    public String getAdress() {
        return Adress;
    }

    public String getPhone() {
        return Phone;
    }

    public String getPassportNumber() {
        return PassportNumber;
    }

    public String getPolicyNumber() {
        return PolicyNumber;
    }

    public int getUserId() {
        return UserId;
    }

    public int getCardId() {
        return CardId;
    }
}
