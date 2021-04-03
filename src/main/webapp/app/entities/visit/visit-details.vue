<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="visit">
        <h2 class="jh-entity-heading" data-cy="visitDetailsHeading"><span>Visit</span> {{ visit.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Date Of Visit</span>
          </dt>
          <dd>
            <span>{{ visit.dateOfVisit | formatDate }}</span>
          </dd>
          <dt>
            <span>Clinic</span>
          </dt>
          <dd>
            <div v-if="visit.clinic">
              <router-link :to="{ name: 'ClinicView', params: { clinicId: visit.clinic.id } }">{{ visit.clinic.nameOfClinic }}</router-link>
            </div>
          </dd>
          <dt>
            <span>Patient</span>
          </dt>
          <dd>
            <div v-if="visit.patient">
              <router-link :to="{ name: 'PatientView', params: { patientId: visit.patient.id } }">{{ visit.patient.name }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="visit.id" :to="{ name: 'VisitEdit', params: { visitId: visit.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./visit-details.component.ts"></script>
