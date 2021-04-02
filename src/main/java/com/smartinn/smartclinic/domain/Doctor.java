package com.smartinn.smartclinic.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Doctor.
 */
@Entity
@Table(name = "doctor")
public class Doctor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "first_name", length = 30, nullable = false)
    private String firstName;

    @NotNull
    @Size(max = 30)
    @Column(name = "last_name", length = 30, nullable = false)
    private String lastName;

    @NotNull
    @Size(max = 30)
    @Column(name = "specialization", length = 30, nullable = false)
    private String specialization;

    @NotNull
    @Size(max = 11)
    @Column(name = "phone_number", length = 11, nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "doctor")
    @JsonIgnoreProperties(value = { "doctor", "visits", "user" }, allowSetters = true)
    private Set<Clinic> clinics = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Doctor id(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Doctor firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Doctor lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpecialization() {
        return this.specialization;
    }

    public Doctor specialization(String specialization) {
        this.specialization = specialization;
        return this;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Doctor phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<Clinic> getClinics() {
        return this.clinics;
    }

    public Doctor clinics(Set<Clinic> clinics) {
        this.setClinics(clinics);
        return this;
    }

    public Doctor addClinic(Clinic clinic) {
        this.clinics.add(clinic);
        clinic.setDoctor(this);
        return this;
    }

    public Doctor removeClinic(Clinic clinic) {
        this.clinics.remove(clinic);
        clinic.setDoctor(null);
        return this;
    }

    public void setClinics(Set<Clinic> clinics) {
        if (this.clinics != null) {
            this.clinics.forEach(i -> i.setDoctor(null));
        }
        if (clinics != null) {
            clinics.forEach(i -> i.setDoctor(this));
        }
        this.clinics = clinics;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Doctor)) {
            return false;
        }
        return id != null && id.equals(((Doctor) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Doctor{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", specialization='" + getSpecialization() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            "}";
    }
}
