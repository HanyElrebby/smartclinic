<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="smartclinicApp.patient.home.createOrEditLabel" data-cy="PatientCreateUpdateHeading">Create or edit a Patient</h2>
        <div>
          <div v-if="patient.id">
            <base-input type="text" label="ID" name="id" v-model="patient.id" readonly />
          </div>
          <div>
            <base-input
              type="text"
              name="First Name"
              data-cy="firstName"
              label="First Name"
              placeholder="First Name"
              alternative
              v-model="$v.patient.firstName.$model"
              :rules="{ required: true, max: 30 }"
            />
          </div>
          <div>
            <base-input
              type="text"
              name="Last Name"
              data-cy="lastName"
              label="Last Name"
              placeholder="Last Name"
              alternative
              v-model="$v.patient.lastName.$model"
              :rules="{ required: true, max: 30 }"
            />
          </div>
          <div>
            <base-input
              type="text"
              name="Pesel"
              data-cy="pesel"
              label="Pesel"
              placeholder="Pesel"
              alternative
              v-model="$v.patient.pesel.$model"
              :rules="{ required: true, max: 30 }"
            />
          </div>
          <div>
            <base-input
              type="text"
              name="First Father Name"
              data-cy="firstFatherName"
              label="First Father Name"
              placeholder="First Father Name"
              alternative
              v-model="$v.patient.firstFatherName.$model"
              :rules="{ required: true, max: 30 }"
            />
          </div>
          <div>
            <base-input
              type="text"
              name="Contact Number"
              data-cy="contactNumber"
              label="Contact Number"
              placeholder="Contact Number"
              alternative
              v-model="$v.patient.contactNumber.$model"
              :rules="{ required: true, max: 11 }"
            />
          </div>
          <div>
            <base-input
              type="text"
              name="Place Of Residence"
              data-cy="placeOfResidence"
              label="Place Of Residence"
              placeholder="Place Of Residence"
              alternative
              v-model="$v.patient.placeOfResidence.$model"
              :rules="{ required: true, max: 30 }"
            />
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
          <div>
            <base-input
              type="text"
              name="Blood Group"
              data-cy="bloodGroup"
              label="Blood Group"
              placeholder="Blood Group"
              alternative
              v-model="$v.patient.bloodGroup.$model"
              :rules="{ required: true, max: 30 }"
            />
          </div>
          <div>
            <base-input
              type="number"
              name="Phone Number"
              data-cy="phoneNumber"
              label="Phone Number"
              placeholder="Phone Number"
              alternative
              v-model="$v.patient.phoneNumber.$model"
              :rules="{ required: true, max: 11 }"
            />
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
