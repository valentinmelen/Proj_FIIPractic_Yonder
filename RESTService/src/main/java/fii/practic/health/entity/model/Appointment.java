package fii.practic.health.entity.model;


import javax.persistence.*;
import java.util.Date;
/**
 * The appointment object contains main details about a Appointment
 */
@Entity
public class Appointment {
    /**
     * Unique identifier of the appointment
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date startTime;

    private Date endTime;

    private String cause;

    private boolean isCancelled;

    private boolean took_Place;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public boolean isTookPlace() {
        return took_Place;
    }

    public void setTookPlace(boolean took_Place) {
        this.took_Place = took_Place;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", cause='" + cause +
                ", isCancelled=" + isCancelled +
                ", took_Place=" + took_Place +
                ", doctor=" + doctor +
                ", patient=" + patient +
                '}';
    }
}
