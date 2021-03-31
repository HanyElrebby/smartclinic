<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="smartclinicApp.patient.home.createOrEditLabel" data-cy="PatientCreateUpdateHeading">Create or edit a Patient</h2>
        <div>
          <div class="form-group" v-if="patient.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="patient.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="patient-firstName">First Name</label>
            <input
              type="text"
              class="form-control"
              name="firstName"
              id="patient-firstName"
              data-cy="firstName"
              :class="{ valid: !$v.patient.firstName.$invalid, invalid: $v.patient.firstName.$invalid }"
              v-model="$v.patient.firstName.$model"
              required
            />
            <div v-if="$v.patient.firstName.$anyDirty && $v.patient.firstName.$invalid">
              <small class="form-text text-danger" v-if="!$v.patient.firstName.required"> This field is required. </small>
              <small class="form-text text-danger" v-if="!$v.patient.firstName.maxLength">
                This field cannot be longer than 30 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="patient-lastName">Last Name</label>
            <input
              type="text"
              class="form-control"
              name="lastName"
              id="patient-lastName"
              data-cy="lastName"
              :class="{ valid: !$v.patient.lastName.$invalid, invalid: $v.patient.lastName.$invalid }"
              v-model="$v.patient.lastName.$model"
              required
            />
            <div v-if="$v.patient.lastName.$anyDirty && $v.patient.lastName.$invalid">
              <small class="form-text text-danger" v-if="!$v.patient.lastName.required"> This field is required. </small>
              <small class="form-text text-danger" v-if="!$v.patient.lastName.maxLength">
                This field cannot be longer than 30 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="patient-pesel">Pesel</label>
            <input
              type="text"
              class="form-control"
              name="pesel"
              id="patient-pesel"
              data-cy="pesel"
              :class="{ valid: !$v.patient.pesel.$invalid, invalid: $v.patient.pesel.$invalid }"
              v-model="$v.patient.pesel.$model"
              required
            />
            <div v-if="$v.patient.pesel.$anyDirty && $v.patient.pesel.$invalid">
              <small class="form-text text-danger" v-if="!$v.patient.pesel.required"> This field is required. </small>
              <small class="form-text text-danger" v-if="!$v.patient.pesel.maxLength">
                This field cannot be longer than 11 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="patient-firstFatherName">First Father Name</label>
            <input
              type="text"
              class="form-control"
              name="firstFatherName"
              id="patient-firstFatherName"
              data-cy="firstFatherName"
              :class="{ valid: !$v.patient.firstFatherName.$invalid, invalid: $v.patient.firstFatherName.$invalid }"
              v-model="$v.patient.firstFatherName.$model"
              required
            />
            <div v-if="$v.patient.firstFatherName.$anyDirty && $v.patient.firstFatherName.$invalid">
              <small class="form-text text-danger" v-if="!$v.patient.firstFatherName.required"> This field is required. </small>
              <small class="form-text text-danger" v-if="!$v.patient.firstFatherName.maxLength">
                This field cannot be longer than 30 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="patient-contactNumber">Contact Number</label>
            <input
              type="text"
              class="form-control"
              name="contactNumber"
              id="patient-contactNumber"
              data-cy="contactNumber"
              :class="{ valid: !$v.patient.contactNumber.$invalid, invalid: $v.patient.contactNumber.$invalid }"
              v-model="$v.patient.contactNumber.$model"
              required
            />
            <div v-if="$v.patient.contactNumber.$anyDirty && $v.patient.contactNumber.$invalid">
              <small class="form-text text-danger" v-if="!$v.patient.contactNumber.required"> This field is required. </small>
              <small class="form-text text-danger" v-if="!$v.patient.contactNumber.maxLength">
                This field cannot be longer than 11 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="patient-placeOfResidence">Place Of Residence</label>
            <input
              type="text"
              class="form-control"
              name="placeOfResidence"
              id="patient-placeOfResidence"
              data-cy="placeOfResidence"
              :class="{ valid: !$v.patient.placeOfResidence.$invalid, invalid: $v.patient.placeOfResidence.$invalid }"
              v-model="$v.patient.placeOfResidence.$model"
              required
            />
            <div v-if="$v.patient.placeOfResidence.$anyDirty && $v.patient.placeOfResidence.$invalid">
              <small class="form-text text-danger" v-if="!$v.patient.placeOfResidence.required"> This field is required. </small>
              <small class="form-text text-danger" v-if="!$v.patient.placeOfResidence.maxLength">
                This field cannot be longer than 30 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="patient-dateOfBirth">Date Of Birth</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="patient-dateOfBirth"
                  v-model="$v.patient.dateOfBirth.$model"
                  name="dateOfBirth"
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
                id="patient-dateOfBirth"
                data-cy="dateOfBirth"
                type="text"
                class="form-control"
                name="dateOfBirth"
                :class="{ valid: !$v.patient.dateOfBirth.$invalid, invalid: $v.patient.dateOfBirth.$invalid }"
                v-model="$v.patient.dateOfBirth.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.patient.dateOfBirth.$anyDirty && $v.patient.dateOfBirth.$invalid">
              <small class="form-text text-danger" v-if="!$v.patient.dateOfBirth.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="patient-bloodGroup">Blood Group</label>
            <input
              type="text"
              class="form-control"
              name="bloodGroup"
              id="patient-bloodGroup"
              data-cy="bloodGroup"
              :class="{ valid: !$v.patient.bloodGroup.$invalid, invalid: $v.patient.bloodGroup.$invalid }"
              v-model="$v.patient.bloodGroup.$model"
              required
            />
            <div v-if="$v.patient.bloodGroup.$anyDirty && $v.patient.bloodGroup.$invalid">
              <small class="form-text text-danger" v-if="!$v.patient.bloodGroup.required"> This field is required. </small>
              <small class="form-text text-danger" v-if="!$v.patient.bloodGroup.maxLength">
                This field cannot be longer than 30 characters.
              </small>
            </div>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.patient.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./patient-update.component.ts"></script>
