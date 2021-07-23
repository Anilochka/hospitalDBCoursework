package sample.entity;

public class PatientsCard {
    private int IdCard;
    private String Comment;

    public PatientsCard(int id, String comment) {
        this.IdCard = id;
        this.Comment = comment;
    }

    public PatientsCard() {
        this.Comment = " ";
    }

    public PatientsCard(String comment) {
        this.Comment = comment;
    }

    public int getIdCard() {
        return IdCard;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}
