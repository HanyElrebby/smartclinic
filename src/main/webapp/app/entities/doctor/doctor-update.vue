<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="smartclinicApp.doctor.home.createOrEditLabel" data-cy="DoctorCreateUpdateHeading">Create or edit a Doctor</h2>
        <div>
          <div v-if="doctor.id">
            <base-input type="text" label="ID" name="id" v-model="doctor.id" readonly />
          </div>
          <div>
            <base-input
              type="text"
              name="Name"
              label="Name"
              placeholder="Name"
              data-cy="name"
              alternative
              v-model="$v.doctor.name.$model"
              :rules="{ required: true, max: 30 }"
            />
          </div>

          <div>
            <base-input
              type="text"
              name="Specialization"
              data-cy="specialization"
              label="Specialization"
              placeholder="Specialization"
              alternative
              v-model="$v.doctor.specialization.$model"
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
              v-model="$v.doctor.phoneNumber.$model"
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
