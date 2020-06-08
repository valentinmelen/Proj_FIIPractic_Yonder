package fii.practic.health.boundry.controller;

import fii.practic.health.boundry.dto.AppointmentDTO;
import fii.practic.health.boundry.exceptions.BadRequestException;
import fii.practic.health.boundry.exceptions.NotFoundException;
import fii.practic.health.entity.model.Appointment;
import fii.practic.health.entity.model.Doctor;
import fii.practic.health.entity.model.Patient;
import fii.practic.health.service.AppointmentService;
import fii.practic.health.service.DoctorService;
import fii.practic.health.service.MailAppointmentService;
import fii.practic.health.service.PatientService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Appointment controller
 *
 * @author Valentin Melen
 * @version 1.0
 * @since 2020-05-10
 */
@RestController
@RequestMapping(value = "/api/appointments")
public class AppointmentController {
    private AppointmentService appointmentService;
    private PatientService patientService;
    private DoctorService doctorService;
    private ModelMapper modelMapper;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private MailAppointmentService mailAppointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService, PatientService patientService,
                                 DoctorService doctorService, ModelMapper modelMapper, MailAppointmentService mailAppointmentService) {
        this.appointmentService = appointmentService;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.modelMapper = modelMapper;
        this.mailAppointmentService = mailAppointmentService;
    }

    /**
     * This getAppointments method will bring all the appointments
     * from the database
     *
     * @return list of all appointments
     * @throws NotFoundException if list of appointments is empty
     */
    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAppointments() throws NotFoundException {
        List<Appointment> appointments = appointmentService.getAll();

        if (appointments == null) {
            throw new NotFoundException("There are no appointments to display");
        }
        return new ResponseEntity<>((List<AppointmentDTO>) modelMapper.map
                (appointments, new TypeToken<List<AppointmentDTO>>() {
                }.getType()), HttpStatus.OK);
    }

    /**
     * This getById method will wait for a id long
     *
     * @param id uniquely identifies one appointment
     *           Long type
     * @return the fields of the respective appointment
     * @throws NotFoundException if the given id does not exist in the database
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<AppointmentDTO> getById(@PathVariable("id") Long id) throws NotFoundException {
        Appointment appointment = appointmentService.getById(id);

        if (appointment == null) {
            throw new NotFoundException(String.format("Appointment with id %d was not found", id));
        }

        return new ResponseEntity<>(modelMapper.map(appointment, AppointmentDTO.class), HttpStatus.OK);
    }

    /**
     * This getAppointmentsByDoctor method will wait a doctorId
     *
     * @param doctorId of long type from Doctor
     * @return a list of appointments of the given doctorId
     * @throws NotFoundException   if the doctorId's list has no appointment
     * @throws BadRequestException if the doctorId is not exist in database
     */
    @GetMapping(value = "/filter/doctor/{id}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByDoctor(
            @RequestParam(value = "doctorId") Long doctorId)
            throws NotFoundException, BadRequestException {

        if (doctorService.getById(doctorId) == null) {
            throw new BadRequestException(String.format("Doctor with id %d was not found in database.", doctorId));
        }

        List<Appointment> doctorsAppointments = appointmentService.findAppointmentsByDoctorId(doctorId);

        if (doctorsAppointments == null) {
            throw new NotFoundException(String.format("The doctor with id %d has no appointments", doctorId));
        }

        return new ResponseEntity<>((List<AppointmentDTO>) modelMapper.map
                (doctorsAppointments, new TypeToken<List<AppointmentDTO>>() {
                        }.getType()
                ), HttpStatus.OK
        );
    }

    /**
     * Get appointments by patient
     *
     * @param patientId of long type from Patient
     * @return a list of appointments of the given patientId
     * @throws NotFoundException   if the patientId's list has no appointment
     * @throws BadRequestException if the patientId is not exist in database
     */
    @GetMapping(value = "/filter/patient/{id}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByPatient(
            @RequestParam(value = "patientId") Long patientId)
            throws NotFoundException, BadRequestException {
        if (patientService.getById(patientId) == null) {
            throw new BadRequestException(String.format("Patient with id %d was not found in database.", patientId));
        }

        List<Appointment> patientsAppointments = appointmentService.findAppointmentsByPatientId(patientId);

        if (patientsAppointments == null) {
            throw new NotFoundException(String.format("The patient with id %d has no appointments", patientId));
        }
        return new ResponseEntity<>((List<AppointmentDTO>) modelMapper.map
                (patientsAppointments, new TypeToken<List<AppointmentDTO>>() {
                }.getType()), HttpStatus.OK);
    }

    /**
     * Get appointments by doctor which did not took place after the current date
     *
     * @param doctorId of long type from Doctor
     * @return a list of appointments of the given doctorId
     * @throws NotFoundException if the doctorId has no appointments in the future
     * @throws BadRequestException if the doctorId is not exist in database
     */
    @GetMapping(value = "/filter/future/doctor/{id}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByDoctorWhichDidNotTakePlace(
            @RequestParam(value = "doctorId") Long doctorId)
            throws NotFoundException, BadRequestException {

        if (doctorService.getById(doctorId) == null) {
            throw new BadRequestException(String.format("Doctor with id %d was not found in database.", doctorId));
        }

        List<Appointment> doctorSAppointmentsInTheFuture = appointmentService.
                findAppointmentsThatDidNotTakePlace(doctorId);
        if (doctorSAppointmentsInTheFuture == null) {
            throw new NotFoundException(String.format("The doctor with id %d has no appointments in the future", doctorId));
        }
        return new ResponseEntity<>((List<AppointmentDTO>) modelMapper.map(
                doctorSAppointmentsInTheFuture, new TypeToken<List<AppointmentDTO>>() {
                }.getType()), HttpStatus.OK);
    }

    /**
     * Get all future appointments after the current date
     * @return a list of future appointments
     * @throws NotFoundException if future appointments is not exist
     */
    @GetMapping(value = "/future")
    public ResponseEntity<List<AppointmentDTO>> getFutureAppointments()
            throws NotFoundException  {
        List<Appointment> appointmentsInTheFuture = appointmentService.findTheFutureAppointments();

        if (appointmentsInTheFuture == null) {
            throw new NotFoundException("Currently no appointments in the future");
        }
        return new ResponseEntity<>((List<AppointmentDTO>) modelMapper.map(
                appointmentsInTheFuture, new TypeToken<List<AppointmentDTO>>() {
                }.getType()), HttpStatus.OK);
    }

    /**
     * Creates a new appointment
     *
     * @param appointmentDTO
     * @return
     * @throws NotFoundException
     * @throws BadRequestException
     */
    @PostMapping
    public ResponseEntity<AppointmentDTO> save(@Valid @RequestBody AppointmentDTO appointmentDTO)
            throws NotFoundException, BadRequestException {
        logger.info("We are in the save method.");

        Doctor dbDoctor = doctorService.getById(appointmentDTO.getDoctorId());
        if (dbDoctor == null) {
            logger.error(String.format("Doctor with id %d was not found in db",
                    appointmentDTO.getDoctorId()));
            throw new NotFoundException(String.format("Doctor with id %d was not found in db",
                    appointmentDTO.getDoctorId()));
        }

        Patient dbPatient = patientService.getById(appointmentDTO.getPatientId());
        if (dbPatient == null) {
            logger.error(String.format("Patient with id %d was not found in db",
                    appointmentDTO.getPatientId()));
            throw new NotFoundException(String.format("Patient with id %d was not found in db",
                    appointmentDTO.getPatientId()));
        }

        Doctor doctorsPatient = doctorService.getDoctorByPatient(appointmentDTO.getPatientId());
        if (!doctorsPatient.equals(dbDoctor)) {
            logger.error(String.format("Patient with id=%d not belongs to the specified doctor",
                    appointmentDTO.getPatientId()));
            throw new BadRequestException(String.format("Patient with id=%d not belongs to the specified doctor",
                    appointmentDTO.getPatientId()));
        }
        /**
         * startTime is after endTime
         */
        if (appointmentDTO.getStartTime().compareTo(appointmentDTO.getEndTime()) > 0) {
            logger.error("StartTime is after endTime");
            throw new BadRequestException("StartTime is after endTime");
        }

        Boolean isIntervalBooked = appointmentService.isIntervalBooked(appointmentDTO.getStartTime(),
                appointmentDTO.getEndTime(),
                appointmentDTO.getDoctorId());
        /**
         * true ->The date is in interval
         * false -> The date is not in interval
         */
        if (isIntervalBooked) {
            logger.error("The interval is booked by another patient");
            throw new BadRequestException("The interval is booked by another patient");
        }

        Appointment newAppointment = appointmentService.save(modelMapper.map(appointmentDTO, Appointment.class));
        mailAppointmentService.sendEmail(newAppointment);
        return new ResponseEntity<>(modelMapper.map(newAppointment, AppointmentDTO.class), HttpStatus.CREATED);
    }

    /**
     * The patch method can be used only for updating an installed appointment
     * @param id of long type from Appointment
     * @param appointmentDTO json type with appointment fields
     * @return AppointmentDTO modified
     * @throws NotFoundException
     * @throws BadRequestException
     */
    @PatchMapping(value = "/{id}")
    public ResponseEntity<AppointmentDTO> patch(@PathVariable Long id, @RequestBody AppointmentDTO appointmentDTO)
            throws NotFoundException, BadRequestException {
        Appointment dbAppointment = appointmentService.getById(id);

        if (dbAppointment != null) {
            modelMapper.map(appointmentDTO, dbAppointment);

            Boolean hasAppointmentTakenPlaceInThePast = appointmentService.
                    hasAppointmentTakenPlaceInThePast(appointmentDTO.getId());
            if (hasAppointmentTakenPlaceInThePast) {
                logger.error("An appointment which already took place in the past cannot be canceled.");
                throw new BadRequestException(
                        "An appointment which already took place in the past cannot be canceled.");
            }
            Boolean willAppointmentTakePlaceInTheNextHour = appointmentService.
                    willAppointmentTakePlaceInTheNextHour(appointmentDTO.getId());
            if (willAppointmentTakePlaceInTheNextHour) {
                logger.error("An appointment that will take place in the next hour cannot be canceled.");
                throw new BadRequestException("An appointment that will take place in the next hour" +
                        " cannot be canceled.");
            }
            return new ResponseEntity<>(modelMapper.map(appointmentService.patch(dbAppointment), AppointmentDTO.class),
                    HttpStatus.OK);
        }
        logger.error(String.format("Appointment with id %d was not found in db!", appointmentDTO.getId()));
        throw new NotFoundException(String.format("Appointment with id %d was not found in db!",
                appointmentDTO.getId()));
    }

    /**
     * The update method will upgrade all fields for that appointment
     * @param id of long type from Appointment
     * @param appointmentDTO json type with appointment fields
     * @return AppointmentDTO modified
     * @throws NotFoundException id appointment was not found in database
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<AppointmentDTO> update(@PathVariable Long id, @RequestBody AppointmentDTO appointmentDTO)
            throws NotFoundException {
        Appointment dbAppointment = appointmentService.getById(id);

        if (dbAppointment != null) {
            modelMapper.getConfiguration().setSkipNullEnabled(false);
            modelMapper.map(appointmentDTO, dbAppointment);
            modelMapper.getConfiguration().setSkipNullEnabled(true);

            return new ResponseEntity<>(modelMapper.map(appointmentService.update(dbAppointment), AppointmentDTO.class),
                    HttpStatus.OK);
        }
        throw new NotFoundException(String.format("Appointment with id %d was not found in db!",
                appointmentDTO.getId()));
    }

    /**
     * Delete an appointment from database
     * @param id of long type from appointment
     * @return noContent
     * @throws NotFoundException if id does not exist in database
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws NotFoundException {
        Appointment dbAppointment = appointmentService.getById(id);
        if (dbAppointment != null) {
            appointmentService.delete(dbAppointment);
        } else {
            throw new NotFoundException(String.format("The id %d does not exist in the DB.", id));
        }
        return ResponseEntity.noContent().build();
    }

}
