<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="smartclinicApp.clinic.home.createOrEditLabel" data-cy="ClinicCreateUpdateHeading">Create or edit a Clinic</h2>
        <div>
          <div class="form-group" v-if="clinic.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="clinic.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="clinic-nameOfClinic">Name Of Clinic</label>
            <input
              type="text"
              class="form-control"
              name="nameOfClinic"
              id="clinic-nameOfClinic"
              data-cy="nameOfClinic"
              :class="{ valid: !$v.clinic.nameOfClinic.$invalid, invalid: $v.clinic.nameOfClinic.$invalid }"
              v-model="$v.clinic.nameOfClinic.$model"
              required
            />
            <div v-if="$v.clinic.nameOfClinic.$anyDirty && $v.clinic.nameOfClinic.$invalid">
              <small class="form-text text-danger" v-if="!$v.clinic.nameOfClinic.required"> This field is required. </small>
              <small class="form-text text-danger" v-if="!$v.clinic.nameOfClinic.maxLength">
                This field cannot be longer than 30 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="clinic-city">City</label>
            <input
              type="text"
              class="form-control"
              name="city"
              id="clinic-city"
              data-cy="city"
              :class="{ valid: !$v.clinic.city.$invalid, invalid: $v.clinic.city.$invalid }"
              v-model="$v.clinic.city.$model"
              required
            />
            <div v-if="$v.clinic.city.$anyDirty && $v.clinic.city.$invalid">
              <small class="form-text text-danger" v-if="!$v.clinic.city.required"> This field is required. </small>
              <small class="form-text text-danger" v-if="!$v.clinic.city.maxLength">
                This field cannot be longer than 30 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="clinic-postalCode">Postal Code</label>
            <input
              type="text"
              class="form-control"
              name="postalCode"
              id="clinic-postalCode"
              data-cy="postalCode"
              :class="{ valid: !$v.clinic.postalCode.$invalid, invalid: $v.clinic.postalCode.$invalid }"
              v-model="$v.clinic.postalCode.$model"
              required
            />
            <div v-if="$v.clinic.postalCode.$anyDirty && $v.clinic.postalCode.$invalid">
              <small class="form-text text-danger" v-if="!$v.clinic.postalCode.required"> This field is required. </small>
              <small class="form-text text-danger" v-if="!$v.clinic.postalCode.maxLength">
                This field cannot be longer than 30 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="clinic-street">Street</label>
            <input
              type="text"
              class="form-control"
              name="street"
              id="clinic-street"
              data-cy="street"
              :class="{ valid: !$v.clinic.street.$invalid, invalid: $v.clinic.street.$invalid }"
              v-model="$v.clinic.street.$model"
              required
            />
            <div v-if="$v.clinic.street.$anyDirty && $v.clinic.street.$invalid">
              <small class="form-text text-danger" v-if="!$v.clinic.street.required"> This field is required. </small>
              <small class="form-text text-danger" v-if="!$v.clinic.street.maxLength">
                This field cannot be longer than 30 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="clinic-doctor">Doctor</label>
            <select class="form-control" id="clinic-doctor" data-cy="doctor" name="doctor" v-model="clinic.doctor">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="clinic.doctor && doctorOption.id === clinic.doctor.id ? clinic.doctor : doctorOption"
                v-for="doctorOption in doctors"
                :key="doctorOption.id"
              >
                {{ doctorOption.firstName }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="clinic-user">User</label>
            <select class="form-control" id="clinic-user" data-cy="user" name="user" v-model="clinic.user">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="clinic.user && userOption.id === clinic.user.id ? clinic.user : userOption"
                v-for="userOption in users"
                :key="userOption.id"
              >
                {{ userOption.login }}
              </option>
            </select>
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
            :disabled="$v.clinic.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./clinic-update.component.ts"></script>
