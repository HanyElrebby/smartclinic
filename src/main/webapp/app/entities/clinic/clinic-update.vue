<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <b-form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="smartclinicApp.clinic.home.createOrEditLabel" data-cy="ClinicCreateUpdateHeading">إنشاء أو تعديل عيادة</h2>
        <div>
          <div v-if="clinic.id">
            <base-input type="text" label="الكود" id="id" name="الكود" v-model="clinic.id" readonly />
          </div>
          <div>
            <base-input
              type="text"
              label="إسم العيادة"
              placeholder="إسم العيادة"
              data-cy="nameOfClinic"
              alternative
              name="إسم العيادة"
              v-model="$v.clinic.nameOfClinic.$model"
              :rules="{ required: true, max: 30 }"
            />
          </div>
          <div>
            <base-input
              type="text"
              name="المدينة"
              label="المدينة"
              placeholder="المدينة"
              alternative
              data-cy="city"
              v-model="$v.clinic.city.$model"
              :rules="{ required: true, max: 30 }"
            />
          </div>
          <div>
            <base-input
              type="text"
              name="الكود البريدى"
              data-cy="postalCode"
              label="الكود البريدى"
              alternative
              placeholder="الكود البريدى"
              v-model="$v.clinic.postalCode.$model"
              :rules="{ required: true, max: 30 }"
            />
          </div>
          <div>
            <base-input
              type="text"
              name="الشارع"
              label="الشارع"
              placeholder="الشارع"
              data-cy="street"
              alternative
              v-model="$v.clinic.street.$model"
              :rules="{ required: true, max: 30 }"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="clinic-doctor">الطبيب</label>
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
          <div class="form-group">
            <label class="form-control-label" for="clinic-user">المستخدم</label>
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
        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>إلغاء</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.clinic.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>حفظ</span>
          </button>
        </div>
      </b-form>
    </div>
  </div>
</template>
<script lang="ts" src="./clinic-update.component.ts"></script>
