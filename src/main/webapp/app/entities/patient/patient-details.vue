<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="patient">
        <h2 class="jh-entity-heading" data-cy="patientDetailsHeading"><span>Patient</span> {{ patient.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Name</span>
          </dt>
          <dd>
            <span>{{ patient.name }}</span>
          </dd>
          <dt>
            <span>Pesel</span>
          </dt>
          <dd>
            <span>{{ patient.pesel }}</span>
          </dd>
          <dt>
            <span>First Father Name</span>
          </dt>
          <dd>
            <span>{{ patient.firstFatherName }}</span>
          </dd>
          <dt>
            <span>Contact Number</span>
          </dt>
          <dd>
            <span>{{ patient.contactNumber }}</span>
          </dd>
          <dt>
            <span>Place Of Residence</span>
          </dt>
          <dd>
            <span>{{ patient.placeOfResidence }}</span>
          </dd>
          <dt>
            <span>Date Of Birth</span>
          </dt>
          <dd>
            <span>{{ patient.dateOfBirth }}</span>
          </dd>
          <dt>
            <span>Blood Group</span>
          </dt>
          <dd>
            <span>{{ patient.bloodGroup }}</span>
          </dd>
          <dt>
            <span>Phone Number</span>
          </dt>
          <dd>
            <span>{{ patient.phoneNumber }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="patient.id" :to="{ name: 'PatientEdit', params: { patientId: patient.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./patient-details.component.ts"></script>
