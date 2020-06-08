package fii.practic.health.entity.repository;

import fii.practic.health.entity.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query(nativeQuery = true, value = "SELECT (count (*) >0)" +
            " from Appointment " +
            " Where (doctor_id = :doctorId and" +
            " (start_time <= :startTime and :startTime <= end_time or " +
            " start_time <= :endTime and :endTime <= end_time)) ")
    Boolean isIntervalBooked(@Param("startTime") Date startTime,
                             @Param("endTime") Date endTime,
                             @Param("doctorId") Long doctorId);

    List<Appointment> findAppointmentsByDoctorId(Long doctorId);

    List<Appointment> findAppointmentsByPatientId(Long patientId);

    @Query(nativeQuery = true, value = "SELECT *" +
            " from Appointment a Where (a.doctor_id = :doctorId and a.start_time >= CURRENT_TIMESTAMP) ")
    List<Appointment> findFutureAppointmentsByDoctor(@Param("doctorId") Long doctorId);

    @Query(nativeQuery = true, value = "SELECT *" +
            " from Appointment a Where a.start_time >= CURRENT_TIMESTAMP ")
    List<Appointment> findTheFutureAppointments();

    @Query(nativeQuery = true, value = "SELECT (count(*) > 0) " +
            " from Appointment a Where a.id = :id and a.end_time < CURRENT_TIMESTAMP ")
    Boolean hasAppointmentTakenPlaceInThePast(@Param("id") Long id);

    @Query(nativeQuery = true, value = "SELECT (count(*) > 0) " +
            "from Appointment a Where a.id = :id and a.start_time < (current_timestamp + interval '1 HOUR') ")
    Boolean willAppointmentTakePlaceInTheNextHour(@Param("id") Long id);

    @Query(nativeQuery= true, value="SELECT * from appointment where start_time< :currentTime and took_place= :took_place")
    List<Appointment> findAppointmentsThatAlreadyTookPlace(@Param("currentTime")Date currentTime, boolean took_place);
}
