<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="smartclinicApp.expense.home.createOrEditLabel"
          data-cy="ExpenseCreateUpdateHeading"
          v-text="$t('entities.createOrEditExpense')"
        >
          Create or edit a Expense
        </h2>
        <div>
          <div class="form-group" v-if="expense.id">
            <label for="id" v-text="$t('entities.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="expense.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('entities.expenseType')" for="expense-expenseType">Expense Type</label>
            <input
              type="text"
              class="form-control"
              name="expenseType"
              id="expense-expenseType"
              data-cy="expenseType"
              :class="{ valid: !$v.expense.expenseType.$invalid, invalid: $v.expense.expenseType.$invalid }"
              v-model="$v.expense.expenseType.$model"
              required
            />
            <div v-if="$v.expense.expenseType.$anyDirty && $v.expense.expenseType.$invalid">
              <small class="form-text text-danger" v-if="!$v.expense.expenseType.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('entities.price')" for="expense-price">Price</label>
            <input
              type="number"
              class="form-control"
              name="price"
              id="expense-price"
              data-cy="price"
              :class="{ valid: !$v.expense.price.$invalid, invalid: $v.expense.price.$invalid }"
              v-model.number="$v.expense.price.$model"
              required
            />
            <div v-if="$v.expense.price.$anyDirty && $v.expense.price.$invalid">
              <small class="form-text text-danger" v-if="!$v.expense.price.required"> This field is required. </small>
              <small class="form-text text-danger" v-if="!$v.expense.price.min"> This field should be at least 0. </small>
              <small class="form-text text-danger" v-if="!$v.expense.price.numeric"> This field should be a number. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('entities.statement')" for="expense-statement">Statement</label>
            <input
              type="text"
              class="form-control"
              name="statement"
              id="expense-statement"
              data-cy="statement"
              :class="{ valid: !$v.expense.statement.$invalid, invalid: $v.expense.statement.$invalid }"
              v-model="$v.expense.statement.$model"
              required
            />
            <div v-if="$v.expense.statement.$anyDirty && $v.expense.statement.$invalid">
              <small class="form-text text-danger" v-if="!$v.expense.statement.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('entities.detailedStatement')" for="expense-detailedStatement"
              >Detailed Statement</label
            >
            <input
              type="text"
              class="form-control"
              name="detailedStatement"
              id="expense-detailedStatement"
              data-cy="detailedStatement"
              :class="{ valid: !$v.expense.detailedStatement.$invalid, invalid: $v.expense.detailedStatement.$invalid }"
              v-model="$v.expense.detailedStatement.$model"
              required
            />
            <div v-if="$v.expense.detailedStatement.$anyDirty && $v.expense.detailedStatement.$invalid">
              <small class="form-text text-danger" v-if="!$v.expense.detailedStatement.required"> This field is required. </small>
            </div>
          </div>

          <!--<div class="form-group">
            <label class="form-control-label" v-text="$t('entities.expenseDate')" for="expense-expenseDate">Expense Date</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="expense-expenseDate"
                  v-model="$v.expense.expenseDate.$model"
                  name="expenseDate"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="expense-expenseDate"
                data-cy="expenseDate"
                type="text"
                class="form-control"
                name="expenseDate"
                :class="{ valid: !$v.expense.expenseDate.$invalid, invalid: $v.expense.expenseDate.$invalid }"
                v-model="$v.expense.expenseDate.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.expense.expenseDate.$anyDirty && $v.expense.expenseDate.$invalid">
              <small class="form-text text-danger" v-if="!$v.expense.expenseDate.required"> This field is required. </small>
            </div>
          </div>-->

          <div class="form-group">
            <label class="form-control-label" v-text="$t('entities.notes')" for="expense-notes">Notes</label>
            <input
              type="text"
              class="form-control"
              name="notes"
              id="expense-notes"
              data-cy="notes"
              :class="{ valid: !$v.expense.notes.$invalid, invalid: $v.expense.notes.$invalid }"
              v-model="$v.expense.notes.$model"
              required
            />
            <div v-if="$v.expense.notes.$anyDirty && $v.expense.notes.$invalid">
              <small class="form-text text-danger" v-if="!$v.expense.notes.required"> This field is required. </small>
            </div>
          </div>

          <!-- <div class="form-group">
            <label class="form-control-label" v-text="$t('entities.safe')" for="expense-safe">Safe</label>
            <select class="form-control" id="expense-safe" data-cy="safe" name="safe" v-model="expense.safe" required>
              <option v-if="!expense.safe" v-bind:value="null" selected></option>
              <option
                v-bind:value="expense.safe && safeOption.id === expense.safe.id ? expense.safe : safeOption"
                v-for="safeOption in safes"
                :key="safeOption.id"
              >
                {{ safeOption.safeName }}
              </option>
            </select>
          </div>
          <div v-if="$v.expense.safe.$anyDirty && $v.expense.safe.$invalid">
            <small class="form-text text-danger" v-if="!$v.expense.safe.required"> This field is required. </small>
          </div>
        </div>-->

          <div>
            <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
              <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entities.cancel')">Cancel</span>
            </button>
            <button
              type="submit"
              id="save-entity"
              data-cy="entityCreateSaveButton"
              :disabled="$v.expense.$invalid || isSaving"
              class="btn btn-primary"
            >
              <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entities.save')">Save</span>
            </button>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./expense-update.component.ts"></script>
