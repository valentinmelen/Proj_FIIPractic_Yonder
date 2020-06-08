package fii.practic.health.boundry.controller;

import com.example.wsdl.GetCountryResponse;
import fii.practic.health.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public GetCountryResponse getByName(@RequestParam String name) {
        return countryService.getCountry(name);
    }
}
