package com.smartinn.smartclinic.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Clinic.
 */
@Entity
@Table(name = "clinic")
public class Clinic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "name_of_clinic", length = 30, nullable = false)
    private String nameOfClinic;

    @NotNull
    @Size(max = 30)
    @Column(name = "city", length = 30, nullable = false)
    private String city;

    @NotNull
    @Size(max = 30)
    @Column(name = "postal_code", length = 30, nullable = false)
    private String postalCode;

    @NotNull
    @Size(max = 30)
    @Column(name = "street", length = 30, nullable = false)
    private String street;

    @ManyToOne
    @JsonIgnoreProperties(value = { "clinics" }, allowSetters = true)
    private Doctor doctor;

    @OneToMany(mappedBy = "clinic")
    @JsonIgnoreProperties(value = { "clinic", "patient", "detailsOfVisits" }, allowSetters = true)
    private Set<Visit> visits = new HashSet<>();

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Clinic id(Long id) {
        this.id = id;
        return this;
    }

    public String getNameOfClinic() {
        return this.nameOfClinic;
    }

    public Clinic nameOfClinic(String nameOfClinic) {
        this.nameOfClinic = nameOfClinic;
        return this;
    }

    public void setNameOfClinic(String nameOfClinic) {
        this.nameOfClinic = nameOfClinic;
    }

    public String getCity() {
        return this.city;
    }

    public Clinic city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public Clinic postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return this.street;
    }

    public Clinic street(String street) {
        this.street = street;
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Doctor getDoctor() {
        return this.doctor;
    }

    public Clinic doctor(Doctor doctor) {
        this.setDoctor(doctor);
        return this;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Set<Visit> getVisits() {
        return this.visits;
    }

    public Clinic visits(Set<Visit> visits) {
        this.setVisits(visits);
        return this;
    }

    public Clinic addVisit(Visit visit) {
        this.visits.add(visit);
        visit.setClinic(this);
        return this;
    }

    public Clinic removeVisit(Visit visit) {
        this.visits.remove(visit);
        visit.setClinic(null);
        return this;
    }

    public void setVisits(Set<Visit> visits) {
        if (this.visits != null) {
            this.visits.forEach(i -> i.setClinic(null));
        }
        if (visits != null) {
            visits.forEach(i -> i.setClinic(this));
        }
        this.visits = visits;
    }

    public User getUser() {
        return this.user;
    }

    public Clinic user(User user) {
        this.setUser(user);
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Clinic)) {
            return false;
        }
        return id != null && id.equals(((Clinic) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Clinic{" +
            "id=" + getId() +
            ", nameOfClinic='" + getNameOfClinic() + "'" +
            ", city='" + getCity() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", street='" + getStreet() + "'" +
            "}";
    }
}
