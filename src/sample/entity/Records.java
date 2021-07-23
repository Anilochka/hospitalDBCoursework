package sample.entity;

import java.sql.Date;
import java.sql.Time;

public class Records {
    private int IdRecord;
    private int PatientId;
    private int DoctorId;
    private Date Date;
    private Time Time;

    public Records() {}

    public Records(int id, int patientId, int doctorId, Date date, Time time) {
        this.IdRecord = id;
        this.PatientId = patientId;
        this.DoctorId = doctorId;
        this.Date = date;
        this.Time = time;
    }

    public Records(int patientId, int doctorId, Date date, Time time) {
        this.PatientId = patientId;
        this.DoctorId = doctorId;
        this.Date = date;
        this.Time = time;
    }

    public int getIdRecord() {
        return IdRecord;
    }

    public int getPatientId() {
        return PatientId;
    }

    public int getDoctorId() {
        return DoctorId;
    }

    public java.sql.Date getDate() {
        return Date;
    }

    public java.sql.Time getTime() {
        return Time;
    }
}
