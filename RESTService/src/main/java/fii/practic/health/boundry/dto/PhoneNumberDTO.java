package fii.practic.health.boundry.dto;

import javax.validation.constraints.Pattern;

public class PhoneNumberDTO {

    private Long id;

    @Pattern(regexp = "^(07)([0-9]{8})", message = "{error.phoneNumber.regex}")
    private String phoneNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
