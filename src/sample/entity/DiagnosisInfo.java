package sample.entity;

import java.sql.Date;

public class DiagnosisInfo {
    private int IdDiagnosisInfo;
    private int PatientCardId;
    private int DiagnosisId;
    private Date Date;
    private String Comment;

    public DiagnosisInfo() {}

    public DiagnosisInfo(int patientCardId, int diagnosisId, Date date, String comment) {
        this.PatientCardId = patientCardId;
        this.DiagnosisId = diagnosisId;
        this.Date = date;
        this.Comment = comment;
    }

    public int getIdDiagnosisInfo() {
        return IdDiagnosisInfo;
    }

    public int getPatientCardId() {
        return PatientCardId;
    }

    public int getDiagnosisId() {
        return DiagnosisId;
    }

    public java.sql.Date getDate() {
        return Date;
    }

    public String getComment() {
        return Comment;
    }

    public void setPatientCardId(int patientCardId) {
        PatientCardId = patientCardId;
    }

    public void setDiagnosisId(int diagnosisId) {
        DiagnosisId = diagnosisId;
    }

    public void setDate(java.sql.Date date) {
        Date = date;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}
