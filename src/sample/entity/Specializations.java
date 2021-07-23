package sample.entity;

public class Specializations {
    private int IdSpecialization;
    private String Name;

    public Specializations() {}

    public Specializations(int id, String name) {
        this.IdSpecialization = id;
        this.Name = name;
    }

    public Specializations(String name) {
        this.Name = name;
    }

    public int getIdSpecialization() {
        return IdSpecialization;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
