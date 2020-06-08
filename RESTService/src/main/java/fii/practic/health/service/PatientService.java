package fii.practic.health.service;

import fii.practic.health.entity.model.Patient;

import java.util.List;

public interface PatientService {

    List<Patient> getAll();

    Patient getById(Long id);

    Patient save(Patient patient);

    Patient update(Patient patient);

    Patient patch(Patient patient);

    void delete(Patient patient);

}

