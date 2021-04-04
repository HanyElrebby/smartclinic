package com.smartinn.smartclinic.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A DetailsOfVisit.
 */
@Entity
@Table(name = "details_of_visit")
public class DetailsOfVisit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "description_ailments", length = 50, nullable = false)
    private String descriptionAilments;

    @NotNull
    @Size(max = 30)
    @Column(name = "name_of_disease", length = 30, nullable = false)
    private String nameOfDisease;

    @Size(max = 30)
    @Column(name = "recommendations", length = 30)
    private String recommendations;

    @Size(max = 30)
    @Column(name = "medicines", length = 30)
    private String medicines;

    @Size(max = 30)
    @Column(name = "dosage", length = 30)
    private String dosage;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = { "clinic", "patient", "detailsOfVisits" }, allowSetters = true)
    private Visit visit;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DetailsOfVisit id(Long id) {
        this.id = id;
        return this;
    }

    public String getDescriptionAilments() {
        return this.descriptionAilments;
    }

    public DetailsOfVisit descriptionAilments(String descriptionAilments) {
        this.descriptionAilments = descriptionAilments;
        return this;
    }

    public void setDescriptionAilments(String descriptionAilments) {
        this.descriptionAilments = descriptionAilments;
    }

    public String getNameOfDisease() {
        return this.nameOfDisease;
    }

    public DetailsOfVisit nameOfDisease(String nameOfDisease) {
        this.nameOfDisease = nameOfDisease;
        return this;
    }

    public void setNameOfDisease(String nameOfDisease) {
        this.nameOfDisease = nameOfDisease;
    }

    public String getRecommendations() {
        return this.recommendations;
    }

    public DetailsOfVisit recommendations(String recommendations) {
        this.recommendations = recommendations;
        return this;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    public String getMedicines() {
        return this.medicines;
    }

    public DetailsOfVisit medicines(String medicines) {
        this.medicines = medicines;
        return this;
    }

    public void setMedicines(String medicines) {
        this.medicines = medicines;
    }

    public String getDosage() {
        return this.dosage;
    }

    public DetailsOfVisit dosage(String dosage) {
        this.dosage = dosage;
        return this;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Visit getVisit() {
        return this.visit;
    }

    public DetailsOfVisit visit(Visit visit) {
        this.setVisit(visit);
        return this;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetailsOfVisit)) {
            return false;
        }
        return id != null && id.equals(((DetailsOfVisit) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DetailsOfVisit{" +
            "id=" + getId() +
            ", createdBy='" +
            createdBy +
            "', updatedBy='" +
            updatedBy +
            "', descriptionAilments='" + getDescriptionAilments() + "'" +
            ", nameOfDisease='" + getNameOfDisease() + "'" +
            ", recommendations='" + getRecommendations() + "'" +
            ", medicines='" + getMedicines() + "'" +
            ", dosage='" + getDosage() + "'" +
            "}";
    }
}
