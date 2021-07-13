package com.smartinn.smartclinic.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Safe.
 */
@Entity
@Table(name = "safe")
public class Safe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "safe_name", nullable = false)
    private String safeName;

    @NotNull
    @Column(name = "safe_secretary", nullable = false)
    private String safeSecretary;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "safe_value", precision = 21, scale = 2, nullable = false)
    private BigDecimal safeValue;

    @NotNull
    @Column(name = "notes", nullable = false)
    private String notes;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Safe id(Long id) {
        this.id = id;
        return this;
    }

    public String getSafeName() {
        return this.safeName;
    }

    public Safe safeName(String safeName) {
        this.safeName = safeName;
        return this;
    }

    public void setSafeName(String safeName) {
        this.safeName = safeName;
    }

    public String getSafeSecretary() {
        return this.safeSecretary;
    }

    public Safe safeSecretary(String safeSecretary) {
        this.safeSecretary = safeSecretary;
        return this;
    }

    public void setSafeSecretary(String safeSecretary) {
        this.safeSecretary = safeSecretary;
    }

    public BigDecimal getSafeValue() {
        return this.safeValue;
    }

    public Safe safeValue(BigDecimal safeValue) {
        this.safeValue = safeValue;
        return this;
    }

    public void setSafeValue(BigDecimal safeValue) {
        this.safeValue = safeValue;
    }

    public String getNotes() {
        return this.notes;
    }

    public Safe notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Safe)) {
            return false;
        }
        return id != null && id.equals(((Safe) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Safe{" +
            "id=" + getId() +
            ", safeName='" + getSafeName() + "'" +
            ", safeSecretary='" + getSafeSecretary() + "'" +
            ", safeValue=" + getSafeValue() +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
