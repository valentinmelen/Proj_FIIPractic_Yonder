package fii.practic.health.entity.model;

import javax.persistence.*;
/**
 * The person object contains main details about a Person
 */
@MappedSuperclass
public class Person {
    /**
     * Unique identifier of the appointment
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * First name of the person
     */
    @Column(name = "first_name")
    private String firstName;
    /**
     * Last name of the person
     */
    private String lastName;
    /**
     * Email of the person
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "email_id")
    private Email email;
    /**
     * Address of the person
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;
    /**
     * Phone number of the person
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "phone_number_id")
    private PhoneNumber phoneNumber;

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

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email=" + email +
                ", address=" + address +
                ", phoneNumber=" + phoneNumber +
                '}';
    }
}
