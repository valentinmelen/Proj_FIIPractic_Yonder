package fii.practic.health.entity.model;

import javax.persistence.*;
/**
 * The patient object contains main details about a Patient
 */
@Entity
public class Patient extends Person {
    /**
     * Age in years of the patient
     */
    private Integer age;

    @ManyToOne
    @JoinColumn(nullable = true)
    private Doctor doctor;

    public Patient() {
        super();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "age=" + age +
                ", doctor=" + doctor +
                super.toString() +
                "} ";
    }
}
