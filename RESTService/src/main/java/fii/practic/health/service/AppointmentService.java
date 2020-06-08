package fii.practic.health.service;

import fii.practic.health.entity.model.Appointment;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface AppointmentService {
    List<Appointment> getAll();

    Appointment getById(Long id);

    Appointment save(Appointment appointment);

    Appointment update(Appointment appointment);

    Appointment patch(Appointment appointment);

    void delete(Appointment appointment);

    boolean isIntervalBooked(Date startTime, Date endTime, Long doctorId);

    List<Appointment> findAppointmentsByDoctorId(Long doctorId);

    List<Appointment> findAppointmentsByPatientId(Long patientId);

    List<Appointment> findAppointmentsThatDidNotTakePlace(Long doctorId);

    List<Appointment> findTheFutureAppointments();

    Boolean hasAppointmentTakenPlaceInThePast(Long id);

    Boolean willAppointmentTakePlaceInTheNextHour(Long id);

    List<Appointment> findAppointmentsThatAlreadyTookPlace(Date currentTime, boolean took_place);
}
