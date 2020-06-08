package fii.practic.health.entity.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;
/**
 * The doctor object contains main details about a doctor
 */
@Entity
public class Doctor extends Person{

    private String speciality;

    @OneToMany(mappedBy = "doctor" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Patient> patients;

    public Doctor(){
        super();
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "speciality='" + speciality +
                ", patients=" + patients +
                 super.toString() +
                "} ";
    }
}
