<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="smartclinicApp.doctor.home.createOrEditLabel" data-cy="DoctorCreateUpdateHeading">إنشاء او تعديل طبيب</h2>
        <hr />
        <div>
          <div class="form-group row" v-if="doctor.id">
            <label for="example-email-input" class="col-md-2 col-form-label form-control-label">الكود</label>
            <div class="col-md-10">
              <base-input type="text" name="id" v-model="doctor.id" readonly />
            </div>
          </div>
          <div class="form-group row">
            <label for="example-email-input" class="col-md-2 col-form-label form-control-label">الإسم</label>
            <div class="col-md-10">
              <base-input
                type="text"
                name="الإسم"
                data-cy="name"
                alternative
                v-model="$v.doctor.name.$model"
                :rules="{ required: true, max: 30 }"
              />
            </div>
          </div>
          <div class="form-group row">
            <label for="example-email-input" class="col-md-2 col-form-label form-control-label">التخصص</label>
            <div class="col-md-10">
              <base-input
                type="text"
                name="التخصص"
                data-cy="specialization"
                alternative
                v-model="$v.doctor.specialization.$model"
                :rules="{ required: true, max: 30 }"
              />
            </div>
          </div>
          <div class="form-group row">
            <label for="example-email-input" class="col-md-2 col-form-label form-control-label">رقم الهاتف</label>
            <div class="col-md-10">
              <base-input
                type="number"
                name="رقم الهاتف"
                data-cy="phoneNumber"
                alternative
                v-model="$v.doctor.phoneNumber.$model"
                :rules="{ required: true, max: 11 }"
              />
            </div>
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
