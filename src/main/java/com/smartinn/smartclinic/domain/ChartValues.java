package com.smartinn.smartclinic.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A ChartValues.
 */
@Entity
@Table(name = "chart_values")
public class ChartValues implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "x_value")
    private Double xValue;

    @Column(name = "y_value")
    private Double yValue;

    @Column(name = "type")
    private String type;

    @ManyToOne
    @JsonIgnoreProperties(value = { "visits", "chartValues" }, allowSetters = true)
    private Patient patient;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChartValues id(Long id) {
        this.id = id;
        return this;
    }

    public Double getxValue() {
        return this.xValue;
    }

    public ChartValues xValue(Double xValue) {
        this.xValue = xValue;
        return this;
    }

    public void setxValue(Double xValue) {
        this.xValue = xValue;
    }

    public Double getyValue() {
        return this.yValue;
    }

    public ChartValues yValue(Double yValue) {
        this.yValue = yValue;
        return this;
    }

    public void setyValue(Double yValue) {
        this.yValue = yValue;
    }

    public String getType() {
        return this.type;
    }

    public ChartValues type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public ChartValues patient(Patient patient) {
        this.setPatient(patient);
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChartValues)) {
            return false;
        }
        return id != null && id.equals(((ChartValues) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChartValues{" +
            "id=" + getId() +
            ", xValue=" + getxValue() +
            ", yValue=" + getyValue() +
            ", type='" + getType() + "'" +
            "}";
    }
}
