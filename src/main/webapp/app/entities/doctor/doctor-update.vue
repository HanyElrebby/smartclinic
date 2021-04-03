<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="smartclinicApp.doctor.home.createOrEditLabel" data-cy="DoctorCreateUpdateHeading">إنشاء او تعديل طبيب</h2>
        <div>
          <div v-if="doctor.id">
            <base-input type="text" label="الكود" name="id" v-model="doctor.id" readonly />
          </div>
          <div>
            <base-input
              type="text"
              name="الإسم"
              label="الإسم"
              placeholder="الإسم"
              data-cy="name"
              alternative
              v-model="$v.doctor.name.$model"
              :rules="{ required: true, max: 30 }"
            />
          </div>

          <div>
            <base-input
              type="text"
              name="التخصص"
              data-cy="specialization"
              label="التخصص"
              placeholder="التخصص"
              alternative
              v-model="$v.doctor.specialization.$model"
              :rules="{ required: true, max: 30 }"
            />
          </div>
          <div>
            <base-input
              type="number"
              name="رقم الهاتف"
              data-cy="phoneNumber"
              label="رقم الهاتف"
              placeholder="رقم الهاتف"
              alternative
              v-model="$v.doctor.phoneNumber.$model"
              :rules="{ required: true, max: 11 }"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>رجوع</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.doctor.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>حفظ</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./doctor-update.component.ts"></script>
