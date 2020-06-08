package fii.practic.health.job;

import fii.practic.health.service.AppointmentService;
import fii.practic.health.entity.model.Appointment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Define a spring boot job that :
 * @see {https://spring.io/guides/gs/scheduling-tasks/}
 */
@Component
public class AppointmentJob {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentJob(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /**
     * scans the database every 5 minutes and marks the appointments that already took place
     * The job will log in a separate log file the date, time and appointmentId that was marked.
     */
    @Scheduled(fixedRate = 300000)
    public void markAppointmentsThatAlreadyTookPlace() {
        Date currentTime = new Date();

        List<Appointment> appointmentsToBeMarked = appointmentService.findAppointmentsThatAlreadyTookPlace(
                currentTime, false);

        if (appointmentsToBeMarked.size() == 0) {
            log.info(String.format("At %s nothing had to be marked!", currentTime));
        } else {
            for (Appointment appointment : appointmentsToBeMarked) {
                appointment.setTookPlace(true);
                appointmentService.save(appointment);
                log.info(String.format("At %s I marked appointment with id=%d with took_place = true.", currentTime,
                        appointment.getId()));
            }
        }
    }
}
