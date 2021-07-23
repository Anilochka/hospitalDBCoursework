package sample.entity;

public class Diagnosis {
    private int IdDiagnosis;
    private String Name;
    private String Comment;

    public Diagnosis() {}

    public Diagnosis(int id, String name, String comment) {
        this.IdDiagnosis = id;
        this.Name = name;
        this.Comment = comment;
    }

    public Diagnosis(String name, String comment) {
        this.Name = name;
        this.Comment = comment;
    }

    public int getId() {
        return IdDiagnosis;
    }

    public void setId(int id) {
        this.IdDiagnosis = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}
