package com.smartinn.smartclinic.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Visit.
 */
@Entity
@Table(name = "visit")
public class Visit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "visit_type", nullable = false)
    private String visitType;

    @NotNull
    @Column(name = "date_of_visit", nullable = false)
    private ZonedDateTime dateOfVisit;

    @ManyToOne
    @JsonIgnoreProperties(value = { "doctor", "visits", "user" }, allowSetters = true)
    private Clinic clinic;

    @ManyToOne
    @JsonIgnoreProperties(value = { "visits" }, allowSetters = true)
    private Patient patient;

    @OneToMany(mappedBy = "visit")
    @JsonIgnoreProperties(value = { "visit" }, allowSetters = true)
    private Set<DetailsOfVisit> detailsOfVisits = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Visit id(Long id) {
        this.id = id;
        return this;
    }

    public ZonedDateTime getDateOfVisit() {
        return this.dateOfVisit;
    }

    public Visit dateOfVisit(ZonedDateTime dateOfVisit) {
        this.dateOfVisit = dateOfVisit;
        return this;
    }

    public void setDateOfVisit(ZonedDateTime dateOfVisit) {
        this.dateOfVisit = dateOfVisit;
    }

    public String getVisitType() {
        return visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }

    public Clinic getClinic() {
        return this.clinic;
    }

    public Visit clinic(Clinic clinic) {
        this.setClinic(clinic);
        return this;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public Visit patient(Patient patient) {
        this.setPatient(patient);
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Set<DetailsOfVisit> getDetailsOfVisits() {
        return this.detailsOfVisits;
    }

    public Visit detailsOfVisits(Set<DetailsOfVisit> detailsOfVisits) {
        this.setDetailsOfVisits(detailsOfVisits);
        return this;
    }

    public Visit addDetailsOfVisit(DetailsOfVisit detailsOfVisit) {
        this.detailsOfVisits.add(detailsOfVisit);
        detailsOfVisit.setVisit(this);
        return this;
    }

    public Visit removeDetailsOfVisit(DetailsOfVisit detailsOfVisit) {
        this.detailsOfVisits.remove(detailsOfVisit);
        detailsOfVisit.setVisit(null);
        return this;
    }

    public void setDetailsOfVisits(Set<DetailsOfVisit> detailsOfVisits) {
        if (this.detailsOfVisits != null) {
            this.detailsOfVisits.forEach(i -> i.setVisit(null));
        }
        if (detailsOfVisits != null) {
            detailsOfVisits.forEach(i -> i.setVisit(this));
        }
        this.detailsOfVisits = detailsOfVisits;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Visit)) {
            return false;
        }
        return id != null && id.equals(((Visit) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "Visit [id=" +
            id +
            ", visitType=" +
            visitType +
            ", dateOfVisit=" +
            dateOfVisit +
            ", clinic=" +
            clinic +
            ", patient=" +
            patient +
            ", detailsOfVisits=" +
            detailsOfVisits +
            "]"
        );
    }
    // prettier-ignore

}
