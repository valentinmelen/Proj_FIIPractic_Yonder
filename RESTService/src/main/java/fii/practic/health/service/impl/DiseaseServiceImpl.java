package fii.practic.health.service.impl;

import fii.practic.health.boundry.dto.DiseaseResponseDTO;
import fii.practic.health.service.DiseaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DiseaseServiceImpl implements DiseaseService {

    private RestTemplate restTemplate;
    private static final String URL = "https://coronavirus-tracker-api.herokuapp.com/v2/locations?source=jhu&country_code=RO";


    @Autowired
    public DiseaseServiceImpl(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Override
    public DiseaseResponseDTO findAll() {
        return restTemplate.getForObject(URL, DiseaseResponseDTO.class);
    }
}
