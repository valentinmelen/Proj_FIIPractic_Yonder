package fii.practic.health.boundry.dto;

import fii.practic.health.validations.Phone;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class DoctorDTO {

    private Long id;

    @NotEmpty(message = "{error.user.firstName.notEmpty}")
    @Pattern(regexp = "[A-za-z]+", message = "{error.user.firstName.regex}")
    private String firstName;

    @NotEmpty(message = "{error.user.lastName.notEmpty}")
    @Pattern(regexp = "[A-za-z]+", message = "{error.user.lastName.regex}")
    @Size(min = 2, max = 50, message = "{error.user.lastName.size}")
    private String lastName;

    private String speciality;


    @Valid
    private EmailDTO email;

    private AddressDTO address;

    @Phone
    private PhoneNumberDTO phoneNumber;

    private List<PatientDTO> patients;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public EmailDTO getEmail() {
        return email;
    }

    public void setEmail(EmailDTO email) {
        this.email = email;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public PhoneNumberDTO getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumberDTO phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<PatientDTO> getPatients() {
        return patients;
    }

    public void setPatients(List<PatientDTO> patients) {
        this.patients = patients;
    }

}
