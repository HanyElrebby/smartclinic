<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <b-form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="smartclinicApp.clinic.home.createOrEditLabel" v-text="$t('entities.editClinic')" data-cy="ClinicCreateUpdateHeading">
          إنشاء أو تعديل عيادة
        </h2>
        <hr />
        <div>
          <div class="form-group row" v-if="clinic.id">
            <label for="example-email-input" v-text="$t('entities.id')" class="col-md-2 col-form-label form-control-label">الكود</label>
            <div class="col-md-10">
              <base-input type="text" id="id" :name="translate('entities.id')" v-model="clinic.id" readonly />
            </div>
          </div>
          <div class="form-group row">
            <label for="example-email-input" v-text="$t('entities.clinicName')" class="col-md-2 col-form-label form-control-label"
              >إسم العيادة</label
            >
            <div class="col-md-10">
              <base-input
                type="text"
                data-cy="nameOfClinic"
                alternative
                :name="translate('entities.clinicName')"
                v-model="$v.clinic.nameOfClinic.$model"
                :rules="{ required: true, max: 30 }"
              />
            </div>
          </div>
          <div class="form-group row">
            <label for="example-email-input" v-text="$t('entities.city')" class="col-md-2 col-form-label form-control-label">المدينة</label>
            <div class="col-md-10">
              <base-input
                type="text"
                :name="translate('entities.city')"
                alternative
                data-cy="city"
                v-model="$v.clinic.city.$model"
                :rules="{ required: true, max: 30 }"
              />
            </div>
          </div>
          <div class="form-group row">
            <label for="example-email-input" v-text="$t('entities.postalCode')" class="col-md-2 col-form-label form-control-label"
              >الكود البريدى</label
            >
            <div class="col-md-10">
              <base-input
                type="text"
                :name="translate('entities.postalCode')"
                data-cy="postalCode"
                alternative
                v-model="$v.clinic.postalCode.$model"
                :rules="{ required: true, max: 30 }"
              />
            </div>
          </div>
          <div class="form-group row">
            <label for="example-email-input" v-text="$t('entities.street')" class="col-md-2 col-form-label form-control-label"
              >الشارع</label
            >
            <div class="col-md-10">
              <base-input
                type="text"
                :name="translate('entities.street')"
                data-cy="street"
                alternative
                v-model="$v.clinic.street.$model"
                :rules="{ required: true, max: 30 }"
              />
            </div>
          </div>
          <div class="form-group row">
            <label class="col-md-2 col-form-label form-control-label" v-text="$t('entities.doctor')" for="clinic-doctor">الطبيب</label>
            <div class="col-md-10">
              <select class="form-control" id="clinic-doctor" data-cy="doctor" name="doctor" v-model="clinic.doctor">
                <option v-bind:value="null"></option>
                <option
                  v-bind:value="clinic.doctor && doctorOption.id === clinic.doctor.id ? clinic.doctor : doctorOption"
                  v-for="doctorOption in doctors"
                  :key="doctorOption.id"
                >
                  {{ doctorOption.name }}
                </option>
              </select>
            </div>
          </div>
          <div class="form-group row" style="margin-top: 3rem">
            <label class="col-md-2 col-form-label form-control-label" v-text="$t('entities.user')" for="clinic-user">المستخدم</label>
            <div class="col-md-10">
              <select class="form-control" id="clinic-user" data-cy="user" name="user" v-model="clinic.user">
                <option v-bind:value="null"></option>
                <option
                  v-bind:value="clinic.user && userOption.id === clinic.user.id ? clinic.user : userOption"
                  v-for="userOption in users"
                  :key="userOption.id"
                >
                  {{ userOption.login }}
                </option>
              </select>
            </div>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entities.cancel')">إلغاء</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.clinic.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entities.save')">حفظ</span>
          </button>
        </div>
      </b-form>
    </div>
  </div>
</template>
<script lang="ts" src="./clinic-update.component.ts"></script>
