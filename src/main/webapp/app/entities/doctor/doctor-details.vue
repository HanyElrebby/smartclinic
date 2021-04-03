<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="doctor">
        <h2 class="jh-entity-heading" data-cy="doctorDetailsHeading"><span>الطبيب</span> {{ doctor.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>الإسم</span>
          </dt>
          <dd>
            <span>{{ doctor.name }}</span>
          </dd>
          <dt>
            <span>التخصص</span>
          </dt>
          <dd>
            <span>{{ doctor.specialization }}</span>
          </dd>
          <dt>
            <span>رقم الهاتف</span>
          </dt>
          <dd>
            <span>{{ doctor.phoneNumber }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-right"></font-awesome-icon>&nbsp;<span> رجوع</span>
        </button>
        <router-link v-if="doctor.id" :to="{ name: 'DoctorEdit', params: { doctorId: doctor.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> تعديل</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./doctor-details.component.ts"></script>
