package com.smartinn.smartclinic.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BigDecimalFilter;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.smartinn.smartclinic.domain.Expense} entity. This class is used
 * in {@link com.smartinn.smartclinic.web.rest.ExpenseResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /expenses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ExpenseCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter expenseType;

    private BigDecimalFilter price;

    private StringFilter statement;

    private StringFilter detailedStatement;

    private LocalDateFilter expenseDate;

    private StringFilter notes;

    private LongFilter safeId;

    public ExpenseCriteria() {}

    public ExpenseCriteria(ExpenseCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.expenseType = other.expenseType == null ? null : other.expenseType.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.statement = other.statement == null ? null : other.statement.copy();
        this.detailedStatement = other.detailedStatement == null ? null : other.detailedStatement.copy();
        this.expenseDate = other.expenseDate == null ? null : other.expenseDate.copy();
        this.notes = other.notes == null ? null : other.notes.copy();
        this.safeId = other.safeId == null ? null : other.safeId.copy();
    }

    @Override
    public ExpenseCriteria copy() {
        return new ExpenseCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getExpenseType() {
        return expenseType;
    }

    public StringFilter expenseType() {
        if (expenseType == null) {
            expenseType = new StringFilter();
        }
        return expenseType;
    }

    public void setExpenseType(StringFilter expenseType) {
        this.expenseType = expenseType;
    }

    public BigDecimalFilter getPrice() {
        return price;
    }

    public BigDecimalFilter price() {
        if (price == null) {
            price = new BigDecimalFilter();
        }
        return price;
    }

    public void setPrice(BigDecimalFilter price) {
        this.price = price;
    }

    public StringFilter getStatement() {
        return statement;
    }

    public StringFilter statement() {
        if (statement == null) {
            statement = new StringFilter();
        }
        return statement;
    }

    public void setStatement(StringFilter statement) {
        this.statement = statement;
    }

    public StringFilter getDetailedStatement() {
        return detailedStatement;
    }

    public StringFilter detailedStatement() {
        if (detailedStatement == null) {
            detailedStatement = new StringFilter();
        }
        return detailedStatement;
    }

    public void setDetailedStatement(StringFilter detailedStatement) {
        this.detailedStatement = detailedStatement;
    }

    public LocalDateFilter getExpenseDate() {
        return expenseDate;
    }

    public LocalDateFilter expenseDate() {
        if (expenseDate == null) {
            expenseDate = new LocalDateFilter();
        }
        return expenseDate;
    }

    public void setExpenseDate(LocalDateFilter expenseDate) {
        this.expenseDate = expenseDate;
    }

    public StringFilter getNotes() {
        return notes;
    }

    public StringFilter notes() {
        if (notes == null) {
            notes = new StringFilter();
        }
        return notes;
    }

    public void setNotes(StringFilter notes) {
        this.notes = notes;
    }

    public LongFilter getSafeId() {
        return safeId;
    }

    public LongFilter safeId() {
        if (safeId == null) {
            safeId = new LongFilter();
        }
        return safeId;
    }

    public void setSafeId(LongFilter safeId) {
        this.safeId = safeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ExpenseCriteria that = (ExpenseCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(expenseType, that.expenseType) &&
            Objects.equals(price, that.price) &&
            Objects.equals(statement, that.statement) &&
            Objects.equals(detailedStatement, that.detailedStatement) &&
            Objects.equals(expenseDate, that.expenseDate) &&
            Objects.equals(notes, that.notes) &&
            Objects.equals(safeId, that.safeId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, expenseType, price, statement, detailedStatement, expenseDate, notes, safeId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExpenseCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (expenseType != null ? "expenseType=" + expenseType + ", " : "") +
            (price != null ? "price=" + price + ", " : "") +
            (statement != null ? "statement=" + statement + ", " : "") +
            (detailedStatement != null ? "detailedStatement=" + detailedStatement + ", " : "") +
            (expenseDate != null ? "expenseDate=" + expenseDate + ", " : "") +
            (notes != null ? "notes=" + notes + ", " : "") +
            (safeId != null ? "safeId=" + safeId + ", " : "") +
            "}";
    }
}
