package fii.practic.health.service.impl;

import fii.practic.health.service.AppointmentService;
import fii.practic.health.entity.model.Appointment;
import fii.practic.health.entity.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private AppointmentRepository appointmentRepository;
    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment getById(Long id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    @Override
    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment update(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment patch(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public void delete(Appointment appointment) {
        appointmentRepository.delete(appointment);
    }

    @Override
    public boolean isIntervalBooked(Date startTime, Date endTime, Long doctorId) {
        return appointmentRepository.isIntervalBooked(startTime, endTime, doctorId);
    }

    @Override
    public List<Appointment> findAppointmentsByDoctorId(Long doctorId) {
        return appointmentRepository.findAppointmentsByDoctorId(doctorId);
    }

    @Override
    public List<Appointment> findAppointmentsByPatientId(Long patientId) {
        return appointmentRepository.findAppointmentsByPatientId(patientId);
    }

    @Override
    public List<Appointment> findAppointmentsThatDidNotTakePlace(Long doctorId) {
        return appointmentRepository.findFutureAppointmentsByDoctor(doctorId);
    }

    @Override
    public List<Appointment> findTheFutureAppointments() {
        return appointmentRepository.findTheFutureAppointments();
    }

    @Override
    public Boolean hasAppointmentTakenPlaceInThePast(Long id) {
        return appointmentRepository.hasAppointmentTakenPlaceInThePast(id);
    }

    @Override
    public Boolean willAppointmentTakePlaceInTheNextHour(Long id) {
        return appointmentRepository.willAppointmentTakePlaceInTheNextHour(id);
    }

    @Override
    public List<Appointment> findAppointmentsThatAlreadyTookPlace(Date currentTime, boolean took_place) {
        return appointmentRepository.findAppointmentsThatAlreadyTookPlace(currentTime, took_place);
    }


}
