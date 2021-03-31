<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="smartclinicApp.doctor.home.createOrEditLabel" data-cy="DoctorCreateUpdateHeading">Create or edit a Doctor</h2>
        <div>
          <div class="form-group" v-if="doctor.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="doctor.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="doctor-firstName">First Name</label>
            <input
              type="text"
              class="form-control"
              name="firstName"
              id="doctor-firstName"
              data-cy="firstName"
              :class="{ valid: !$v.doctor.firstName.$invalid, invalid: $v.doctor.firstName.$invalid }"
              v-model="$v.doctor.firstName.$model"
              required
            />
            <div v-if="$v.doctor.firstName.$anyDirty && $v.doctor.firstName.$invalid">
              <small class="form-text text-danger" v-if="!$v.doctor.firstName.required"> This field is required. </small>
              <small class="form-text text-danger" v-if="!$v.doctor.firstName.maxLength">
                This field cannot be longer than 30 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="doctor-lastName">Last Name</label>
            <input
              type="text"
              class="form-control"
              name="lastName"
              id="doctor-lastName"
              data-cy="lastName"
              :class="{ valid: !$v.doctor.lastName.$invalid, invalid: $v.doctor.lastName.$invalid }"
              v-model="$v.doctor.lastName.$model"
              required
            />
            <div v-if="$v.doctor.lastName.$anyDirty && $v.doctor.lastName.$invalid">
              <small class="form-text text-danger" v-if="!$v.doctor.lastName.required"> This field is required. </small>
              <small class="form-text text-danger" v-if="!$v.doctor.lastName.maxLength">
                This field cannot be longer than 30 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="doctor-specialization">Specialization</label>
            <input
              type="text"
              class="form-control"
              name="specialization"
              id="doctor-specialization"
              data-cy="specialization"
              :class="{ valid: !$v.doctor.specialization.$invalid, invalid: $v.doctor.specialization.$invalid }"
              v-model="$v.doctor.specialization.$model"
              required
            />
            <div v-if="$v.doctor.specialization.$anyDirty && $v.doctor.specialization.$invalid">
              <small class="form-text text-danger" v-if="!$v.doctor.specialization.required"> This field is required. </small>
              <small class="form-text text-danger" v-if="!$v.doctor.specialization.maxLength">
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
            :disabled="$v.doctor.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./doctor-update.component.ts"></script>
