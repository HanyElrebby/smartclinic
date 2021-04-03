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
    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @NotNull
    @Size(max = 11)
    @Column(name = "contact_number", length = 11, nullable = false)
    private String contactNumber;

    @Column(name = "file_number", length = 11, nullable = false)
    private String fileNumber;

    @NotNull
    @Size(max = 30)
    @Column(name = "place_of_residence", length = 30, nullable = false)
    private String placeOfResidence;

    @Column(name = "date_of_birth", nullable = true)
    private LocalDate dateOfBirth;

    @NotNull
    @Size(max = 3)
    @Column(name = "age", length = 3, nullable = false)
    private String age;

    @NotNull
    @Size(max = 6)
    @Column(name = "gender", length = 6, nullable = false)
    private String gender;

    @NotNull
    @Size(max = 30)
    @Column(name = "blood_group", length = 30, nullable = false)
    private String bloodGroup;

    @NotNull
    @Size(max = 11)
    @Column(name = "phone_number", length = 11, nullable = false)
    private String phoneNumber;

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

    public String getName() {
        return this.name;
    }

    public Patient name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Patient phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    @Override
    public String toString() {
        return (
            "Patient [id=" +
            id +
            ", name=" +
            name +
            ", contactNumber=" +
            contactNumber +
            ", fileNumber=" +
            fileNumber +
            ", placeOfResidence=" +
            placeOfResidence +
            ", dateOfBirth=" +
            dateOfBirth +
            ", age=" +
            age +
            ", gender=" +
            gender +
            ", bloodGroup=" +
            bloodGroup +
            ", phoneNumber=" +
            phoneNumber +
            ", visits=" +
            visits +
            "]"
        );
    }
    // prettier-ignore

}
