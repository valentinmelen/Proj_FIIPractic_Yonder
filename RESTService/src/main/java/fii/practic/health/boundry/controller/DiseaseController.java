package fii.practic.health.boundry.controller;

import fii.practic.health.aspects.Timed;
import fii.practic.health.boundry.dto.DiseaseResponseDTO;
import fii.practic.health.boundry.dto.Latest;
import fii.practic.health.service.DiseaseService;
import fii.practic.health.service.DoctorService;
import fii.practic.health.entity.model.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/api/diseases")
public class DiseaseController {

    private DiseaseService diseaseService;
    private DoctorService doctorService;

    @Autowired
    public DiseaseController(DiseaseService diseaseService, DoctorService doctorService) {
        this.diseaseService = diseaseService;
        this.doctorService = doctorService;
    }

    @GetMapping(value = "/cure")
    @Timed
    public DiseaseResponseDTO curePatients() throws InterruptedException {
        DiseaseResponseDTO response = diseaseService.findAll();
        List<Doctor> doctors = doctorService.getAll();

        Latest latest = response.getLatest();
        Runnable task = () -> {
            while (latest.getConfirmed() > 0) {
                int confirmed = latest.getConfirmed();
                int recovered = latest.getRecovered();
                int cured = confirmed >= 5 ? 5 : confirmed;

                latest.setConfirmed(confirmed - cured);
                latest.setRecovered(recovered + cured);

                try {
                    Thread.sleep(8);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(doctors.size());

        for (Doctor doctor : doctors) {
            executorService.submit(task);
        }

        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);

        return response;
    }

    @GetMapping
    public DiseaseResponseDTO getDiseases() {
        return diseaseService.findAll();
    }

}
