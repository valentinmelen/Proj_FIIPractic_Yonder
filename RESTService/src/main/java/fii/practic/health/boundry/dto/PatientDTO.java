package fii.practic.health.boundry.dto;

import fii.practic.health.validations.Phone;

import javax.validation.constraints.*;

public class PatientDTO {

    private Long id;

    @NotEmpty(message = "{error.user.firstName.notEmpty}")
    @Pattern(regexp = "[A-za-z]+", message = "{error.user.firstName.regex}")
    private String firstName;

    @NotEmpty(message = "{error.user.lastName.notEmpty}")
    @Pattern(regexp = "[A-za-z]+", message = "{error.user.lastName.regex}")
    @Size(min = 2, max = 50, message = "{error.user.lastName.size}")
    private String lastName;

    @PositiveOrZero
    @Min(value = 18)
    private Integer age;

    @Email
    private EmailDTO email;

    private AddressDTO address;

    @Phone
    private PhoneNumberDTO phoneNumber;

    @NotNull(message = "{error.patient.doctorId.notNull}")
    private Long doctorId;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
}
