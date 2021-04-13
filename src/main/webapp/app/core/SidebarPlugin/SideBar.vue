<template>
  <nav class="navbar-vertical fixed-right navbar-expand-md bg-white" style="right: 100px; margin-top: 75px" id="sidenav-main">
    <div class="container-fluid" style="min-height: 50px" v-for="visit in visits" :key="visit.id">
      <router-link v-if="visit.patient" :to="{ name: 'PatientView', params: { patientId: visit.patient.id } }" custom v-slot="{ navigate }">
        <button
          v-if="visit.patient.id + '' !== patientId"
          @click="navigate"
          style="width: 100%; margin-top: 10px"
          class="btn btn-success ml-2"
        >
          <span> {{ visit.patient.name }}</span>
        </button>
        <button
          v-if="visit.patient.id + '' === patientId"
          @click="navigate"
          style="width: 100%; margin-top: 10px"
          class="btn btn-info ml-2"
        >
          <span> {{ visit.patient.name }}</span>
        </button>
      </router-link>
    </div>
  </nav>
</template>
<script lang="ts" src="./SideBar.component.ts"></script>
