package com.smartinn.smartclinic.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Patient.
 */
@Entity
@Table(name = "patient")
public class Patient implements Serializable {

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
    @Size(max = 11)
    @Column(name = "pesel", length = 11, nullable = false)
    private String pesel;

    @NotNull
    @Size(max = 30)
    @Column(name = "first_father_name", length = 30, nullable = false)
    private String firstFatherName;

    @NotNull
    @Size(max = 11)
    @Column(name = "contact_number", length = 11, nullable = false)
    private String contactNumber;

    @NotNull
    @Size(max = 30)
    @Column(name = "place_of_residence", length = 30, nullable = false)
    private String placeOfResidence;

    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @NotNull
    @Size(max = 30)
    @Column(name = "blood_group", length = 30, nullable = false)
    private String bloodGroup;

    @OneToMany(mappedBy = "patient")
    @JsonIgnoreProperties(value = { "clinic", "patient", "detailsOfVisits" }, allowSetters = true)
    private Set<Visit> visits = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient id(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Patient firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Patient lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPesel() {
        return this.pesel;
    }

    public Patient pesel(String pesel) {
        this.pesel = pesel;
        return this;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getFirstFatherName() {
        return this.firstFatherName;
    }

    public Patient firstFatherName(String firstFatherName) {
        this.firstFatherName = firstFatherName;
        return this;
    }

    public void setFirstFatherName(String firstFatherName) {
        this.firstFatherName = firstFatherName;
    }

    public String getContactNumber() {
        return this.contactNumber;
    }

    public Patient contactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
        return this;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPlaceOfResidence() {
        return this.placeOfResidence;
    }

    public Patient placeOfResidence(String placeOfResidence) {
        this.placeOfResidence = placeOfResidence;
        return this;
    }

    public void setPlaceOfResidence(String placeOfResidence) {
        this.placeOfResidence = placeOfResidence;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public Patient dateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBloodGroup() {
        return this.bloodGroup;
    }

    public Patient bloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
        return this;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public Set<Visit> getVisits() {
        return this.visits;
    }

    public Patient visits(Set<Visit> visits) {
        this.setVisits(visits);
        return this;
    }

    public Patient addVisit(Visit visit) {
        this.visits.add(visit);
        visit.setPatient(this);
        return this;
    }

    public Patient removeVisit(Visit visit) {
        this.visits.remove(visit);
        visit.setPatient(null);
        return this;
    }

    public void setVisits(Set<Visit> visits) {
        if (this.visits != null) {
            this.visits.forEach(i -> i.setPatient(null));
        }
        if (visits != null) {
            visits.forEach(i -> i.setPatient(this));
        }
        this.visits = visits;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Patient)) {
            return false;
        }
        return id != null && id.equals(((Patient) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Patient{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", pesel='" + getPesel() + "'" +
            ", firstFatherName='" + getFirstFatherName() + "'" +
            ", contactNumber='" + getContactNumber() + "'" +
            ", placeOfResidence='" + getPlaceOfResidence() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", bloodGroup='" + getBloodGroup() + "'" +
            "}";
    }
}
