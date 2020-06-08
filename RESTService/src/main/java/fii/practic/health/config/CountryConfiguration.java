package fii.practic.health.config;

import fii.practic.health.service.CountryService;
import fii.practic.health.service.impl.CountryServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class CountryConfiguration {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setContextPath("com.example.wsdl");
        return marshaller;
    }

    @Bean
    public CountryService countryClient(Jaxb2Marshaller marshaller) {
        CountryServiceImpl client = new CountryServiceImpl();
        client.setDefaultUri("http://localhost:8080/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

}
