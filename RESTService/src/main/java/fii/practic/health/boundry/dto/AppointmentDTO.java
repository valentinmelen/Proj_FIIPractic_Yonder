package fii.practic.health.boundry.dto;

import javax.validation.constraints.*;
import java.util.Date;

public class AppointmentDTO {

    private Long id;
    @NotNull(message = "{error.appointment.startTime.notNull}")
    @FutureOrPresent(message = "{error.appointment.startTime.futureOrPresent}")
    private Date startTime;

    @NotNull(message = "{error.appointment.endTime.notNull}")
    @Future(message = "{error.appointment.endTime.future}")
    private Date endTime;

    @NotEmpty(message = "{error.appointment.cause.notEmpty}")
    @Size(max = 256, message = "{error.appointment.cause.size}")
    private String cause;

    @NotNull(message = "{error.appointment.doctorId.notNull}")
    private Long doctorId;

    @NotNull(message = "{error.appointment.patientId.notNull}")
    private Long patientId;

    @AssertFalse(message = "{error.appointment.isCancelled.assertFalse}")
    private boolean isCancelled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
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
}
