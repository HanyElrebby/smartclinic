<template>
  <div class="row">
    <div class="col-10">
      <div class="row d-flex justify-content-end">
        <div class="col-50">
          <router-link :to="{}" custom v-slot="{ navigate }">
            <button @click="navigate" class="btn btn-info ml-2">
              <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span> الكشف الحالى</span>
            </button>
          </router-link>
        </div>
      </div>
    </div>
    <div class="col-8 d-flex justify-content-center">
      <div v-if="visit">
        <dl class="row jh-entity-details">
          <dt>
            <span> اسم المريض</span>
          </dt>
          <dd>
            <div v-if="visit.patient">
              <router-link :to="{ name: 'PatientView', params: { patientId: visit.patient.id } }">{{ visit.patient.name }}</router-link>
            </div>
          </dd>
          <dt>
            <span>تاريخ الزيارة</span>
          </dt>
          <dd>
            <span>{{ visit.dateOfVisit | formatDate }}</span>
          </dd>

          <dt>
            <span>نوع الزيارة</span>
          </dt>
          <dd>
            <span>{{ visit.visitType }}</span>
          </dd>

          <dt>
            <span>الوصف </span>
          </dt>
          <dd>
            <span>{{ visit.description }}</span>
          </dd>
          <dt>
            <span>الادوية الموصوفة</span>
          </dt>
          <dd>
            <span>{{ visit.medicine }}</span>
          </dd>
          <dt>
            <span>ملاحظات </span>
          </dt>
          <dd>
            <span>{{ visit.note }}</span>
          </dd>

          <dt>
            <span>العيادة</span>
          </dt>
          <dd>
            <div v-if="visit.clinic">
              <router-link :to="{ name: 'ClinicView', params: { clinicId: visit.clinic.id } }">{{ visit.clinic.nameOfClinic }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="backward"></font-awesome-icon>&nbsp;<span> عودة</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./visit-details.component.ts"></script>
