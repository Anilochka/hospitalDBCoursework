package sample.entity;

public class Staff {
    private int IdStaff;
    private String FirstName;
    private String LastName;
    private String FatherName;
    private int SpecializationId;
    private String Phone;
    private int UserId;

    public Staff() {}

    public Staff(String firstName, String lastName, String fatherName) {
        this.IdStaff = 0;
        this.FirstName = firstName;
        this.LastName = lastName;
        this.FatherName = fatherName;
    }

    public Staff(int id, String firstName, String lastName, String fatherName, int specializationId, String phone, int userId) {
        this.IdStaff = id;
        this.FirstName = firstName;
        this.LastName = lastName;
        this.FatherName = fatherName;
        this.Phone = phone;
        this.SpecializationId = specializationId;
        this.UserId = userId;
    }

    public Staff(String firstName, String lastName, String fatherName, int specializationId, String phone, int userId) {
        this.FirstName = firstName;
        this.LastName = lastName;
        this.FatherName = fatherName;
        this.Phone = phone;
        this.SpecializationId = specializationId;
        this.UserId = userId;
    }

    public int getIdStaff() {
        return IdStaff;
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

    public String getPhone() {
        return Phone;
    }

    public int getSpecializationId() {
        return SpecializationId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setIdStaff(int idStaff) {
        IdStaff = idStaff;
    }
}
