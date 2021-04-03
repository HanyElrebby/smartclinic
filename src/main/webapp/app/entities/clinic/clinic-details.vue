<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="clinic">
        <h2 class="jh-entity-heading" data-cy="clinicDetailsHeading"><span>العيادة</span> {{ clinic.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>إسم العيادة</span>
          </dt>
          <dd>
            <span>{{ clinic.nameOfClinic }}</span>
          </dd>
          <dt>
            <span>المدينة</span>
          </dt>
          <dd>
            <span>{{ clinic.city }}</span>
          </dd>
          <dt>
            <span>الكود البريدى</span>
          </dt>
          <dd>
            <span>{{ clinic.postalCode }}</span>
          </dd>
          <dt>
            <span>الشارع</span>
          </dt>
          <dd>
            <span>{{ clinic.street }}</span>
          </dd>
          <dt>
            <span>الطبيب</span>
          </dt>
          <dd>
            <div v-if="clinic.doctor">
              <router-link :to="{ name: 'DoctorView', params: { doctorId: clinic.doctor.id } }">{{ clinic.doctor.firstName }}</router-link>
            </div>
          </dd>
          <dt>
            <span>المستخدم</span>
          </dt>
          <dd>
            {{ clinic.user ? clinic.user.login : '' }}
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="backward"></font-awesome-icon>&nbsp;<span> رجوع</span>
        </button>
        <router-link v-if="clinic.id" :to="{ name: 'ClinicEdit', params: { clinicId: clinic.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> تعديل</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./clinic-details.component.ts"></script>
