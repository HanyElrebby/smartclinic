package com.smartinn.smartclinic.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Expense.
 */
@Entity
@Table(name = "expense")
public class Expense implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "expense_type", nullable = false)
    private String expenseType;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "price", precision = 21, scale = 2, nullable = false)
    private BigDecimal price;

    @NotNull
    @Column(name = "statement", nullable = false)
    private String statement;

    @NotNull
    @Column(name = "detailed_statement", nullable = false)
    private String detailedStatement;

    @NotNull
    @Column(name = "expense_date", nullable = false, unique = true)
    private LocalDate expenseDate;

    @NotNull
    @Column(name = "notes", nullable = false)
    private String notes;

    @ManyToOne(optional = false)
    @NotNull
    private Safe safe;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Expense id(Long id) {
        this.id = id;
        return this;
    }

    public String getExpenseType() {
        return this.expenseType;
    }

    public Expense expenseType(String expenseType) {
        this.expenseType = expenseType;
        return this;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public Expense price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatement() {
        return this.statement;
    }

    public Expense statement(String statement) {
        this.statement = statement;
        return this;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getDetailedStatement() {
        return this.detailedStatement;
    }

    public Expense detailedStatement(String detailedStatement) {
        this.detailedStatement = detailedStatement;
        return this;
    }

    public void setDetailedStatement(String detailedStatement) {
        this.detailedStatement = detailedStatement;
    }

    public LocalDate getExpenseDate() {
        return this.expenseDate;
    }

    public Expense expenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate;
        return this;
    }

    public void setExpenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getNotes() {
        return this.notes;
    }

    public Expense notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Safe getSafe() {
        return this.safe;
    }

    public Expense safe(Safe safe) {
        this.setSafe(safe);
        return this;
    }

    public void setSafe(Safe safe) {
        this.safe = safe;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Expense)) {
            return false;
        }
        return id != null && id.equals(((Expense) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Expense{" +
            "id=" + getId() +
            ", expenseType='" + getExpenseType() + "'" +
            ", price=" + getPrice() +
            ", statement='" + getStatement() + "'" +
            ", detailedStatement='" + getDetailedStatement() + "'" +
            ", expenseDate='" + getExpenseDate() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
