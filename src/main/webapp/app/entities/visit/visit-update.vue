<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="smartclinicApp.visit.home.createOrEditLabel" data-cy="VisitCreateUpdateHeading">Create or edit a Visit</h2>
        <div>
          <div class="form-group" v-if="visit.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="visit.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="visit-dateOfVisit">Date Of Visit</label>
            <div class="d-flex">
              <input
                id="visit-dateOfVisit"
                data-cy="dateOfVisit"
                type="datetime-local"
                class="form-control"
                name="dateOfVisit"
                :class="{ valid: !$v.visit.dateOfVisit.$invalid, invalid: $v.visit.dateOfVisit.$invalid }"
                required
                :value="convertDateTimeFromServer($v.visit.dateOfVisit.$model)"
                @change="updateZonedDateTimeField('dateOfVisit', $event)"
              />
            </div>
            <div v-if="$v.visit.dateOfVisit.$anyDirty && $v.visit.dateOfVisit.$invalid">
              <small class="form-text text-danger" v-if="!$v.visit.dateOfVisit.required"> This field is required. </small>
              <small class="form-text text-danger" v-if="!$v.visit.dateOfVisit.ZonedDateTimelocal">
                This field should be a date and time.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="visit-clinic">Clinic</label>
            <select class="form-control" id="visit-clinic" data-cy="clinic" name="clinic" v-model="visit.clinic">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="visit.clinic && clinicOption.id === visit.clinic.id ? visit.clinic : clinicOption"
                v-for="clinicOption in clinics"
                :key="clinicOption.id"
              >
                {{ clinicOption.nameOfClinic }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="visit-patient">Patient</label>
            <select class="form-control" id="visit-patient" data-cy="patient" name="patient" v-model="visit.patient">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="visit.patient && patientOption.id === visit.patient.id ? visit.patient : patientOption"
                v-for="patientOption in patients"
                :key="patientOption.id"
              >
                {{ patientOption.firstName }}
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
            :disabled="$v.visit.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./visit-update.component.ts"></script>
