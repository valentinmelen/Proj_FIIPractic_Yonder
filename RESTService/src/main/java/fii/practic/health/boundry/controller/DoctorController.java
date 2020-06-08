package fii.practic.health.boundry.controller;

import fii.practic.health.boundry.dto.DoctorDTO;
import fii.practic.health.boundry.exceptions.BadRequestException;
import fii.practic.health.boundry.exceptions.NotFoundException;
import fii.practic.health.service.MailDoctorService;
import fii.practic.health.service.PatientService;
import fii.practic.health.entity.model.Doctor;
import fii.practic.health.service.DoctorService;
import fii.practic.health.entity.model.Patient;
import fii.practic.health.entity.model.Person;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/doctors")
@CrossOrigin
public class DoctorController {

    private PatientService patientService;
    private DoctorService doctorService;
    private ModelMapper modelMapper;
    private MailDoctorService mailService;

    @Autowired
    public DoctorController(PatientService patientService, DoctorService doctorService, ModelMapper modelMapper, MailDoctorService mailService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.modelMapper = modelMapper;
        this.mailService = mailService;
    }

    @GetMapping
    public ResponseEntity<List<DoctorDTO>> getDoctors() {
        List<Doctor> doctors = doctorService.getAll();

        return new ResponseEntity<>((List<DoctorDTO>) modelMapper.map(doctors, new TypeToken<List<DoctorDTO>>() {
        }.getType()), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DoctorDTO> getById(@PathVariable("id") Long id) throws NotFoundException {
        Doctor doctor = doctorService.getById(id);

        if (doctor == null) {
            throw new NotFoundException(String.format("Doctor with id %d was not found", id));
        }

        return new ResponseEntity<>(modelMapper.map(doctor, DoctorDTO.class), HttpStatus.OK);
    }

    @GetMapping(value = "/filter")
    public ResponseEntity<List<DoctorDTO>> getDoctorsByPatientsFirstName(
            @RequestParam(value = "firstName") String firstName) {
        List<Doctor> doctors = doctorService.findDoctorsByPatientsFirstName(firstName);

        return new ResponseEntity<>((List<DoctorDTO>) modelMapper.map(doctors, new TypeToken<List<DoctorDTO>>() {
        }.getType()), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<DoctorDTO> save(@Valid @RequestBody DoctorDTO doctorDTO) {
        Person newDoctor = doctorService.save(modelMapper.map(doctorDTO, Doctor.class));
        mailService.sendEmail(newDoctor);
        return new ResponseEntity<>(modelMapper.map(newDoctor, DoctorDTO.class), HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<DoctorDTO> patch(@PathVariable Long id, @RequestBody DoctorDTO doctorDTO) {
        Doctor dbDoctor = doctorService.getById(id);

        if (dbDoctor != null) {
            modelMapper.map(doctorDTO, dbDoctor);

            return new ResponseEntity<>(modelMapper.map(doctorService.patch(dbDoctor), DoctorDTO.class), HttpStatus.OK);
        }

        return null;
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<DoctorDTO> update(@PathVariable Long id, @RequestBody DoctorDTO doctorDTO)
            throws NotFoundException, BadRequestException {
        if (!id.equals(doctorDTO.getId())) {
            throw new BadRequestException(String.format("Id from PathVariable %d is different from id" +
                    " in Request body %d", id, doctorDTO.getId()));
        }

        Doctor dbDoctor = doctorService.getById(id);

        if (dbDoctor == null) {
            throw new NotFoundException(String.format("Doctor with id %d was not found", id));
        }

        modelMapper.getConfiguration().setSkipNullEnabled(false);
        modelMapper.map(doctorDTO, dbDoctor);
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        return new ResponseEntity<>(modelMapper.map(doctorService.update(dbDoctor), DoctorDTO.class), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Doctor dbDoctor = doctorService.getById(id);

        if (dbDoctor != null) {
            List<Patient> patients = dbDoctor.getPatients();

            for (Patient patient : patients) {
                patient.setDoctor(null);
                patientService.save(patient);
            }
            dbDoctor.setPatients(null);
            doctorService.save(dbDoctor);
            doctorService.delete(dbDoctor);
        }
        return ResponseEntity.noContent().build();
    }
}
