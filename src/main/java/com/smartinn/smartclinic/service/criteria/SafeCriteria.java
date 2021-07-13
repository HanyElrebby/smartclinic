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
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.smartinn.smartclinic.domain.Safe} entity. This class is used
 * in {@link com.smartinn.smartclinic.web.rest.SafeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /safes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SafeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter safeName;

    private StringFilter safeSecretary;

    private BigDecimalFilter safeValue;

    private StringFilter notes;

    public SafeCriteria() {}

    public SafeCriteria(SafeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.safeName = other.safeName == null ? null : other.safeName.copy();
        this.safeSecretary = other.safeSecretary == null ? null : other.safeSecretary.copy();
        this.safeValue = other.safeValue == null ? null : other.safeValue.copy();
        this.notes = other.notes == null ? null : other.notes.copy();
    }

    @Override
    public SafeCriteria copy() {
        return new SafeCriteria(this);
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

    public StringFilter getSafeName() {
        return safeName;
    }

    public StringFilter safeName() {
        if (safeName == null) {
            safeName = new StringFilter();
        }
        return safeName;
    }

    public void setSafeName(StringFilter safeName) {
        this.safeName = safeName;
    }

    public StringFilter getSafeSecretary() {
        return safeSecretary;
    }

    public StringFilter safeSecretary() {
        if (safeSecretary == null) {
            safeSecretary = new StringFilter();
        }
        return safeSecretary;
    }

    public void setSafeSecretary(StringFilter safeSecretary) {
        this.safeSecretary = safeSecretary;
    }

    public BigDecimalFilter getSafeValue() {
        return safeValue;
    }

    public BigDecimalFilter safeValue() {
        if (safeValue == null) {
            safeValue = new BigDecimalFilter();
        }
        return safeValue;
    }

    public void setSafeValue(BigDecimalFilter safeValue) {
        this.safeValue = safeValue;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SafeCriteria that = (SafeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(safeName, that.safeName) &&
            Objects.equals(safeSecretary, that.safeSecretary) &&
            Objects.equals(safeValue, that.safeValue) &&
            Objects.equals(notes, that.notes)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, safeName, safeSecretary, safeValue, notes);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SafeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (safeName != null ? "safeName=" + safeName + ", " : "") +
            (safeSecretary != null ? "safeSecretary=" + safeSecretary + ", " : "") +
            (safeValue != null ? "safeValue=" + safeValue + ", " : "") +
            (notes != null ? "notes=" + notes + ", " : "") +
            "}";
    }
}
