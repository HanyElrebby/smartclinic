<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="smartclinicApp.patient.home.createOrEditLabel" data-cy="PatientCreateUpdateHeading">إنشاء او تعديل مريض</h2>
        <div>
          <div v-if="patient.id">
            <base-input type="text" label="الكود" name="id" v-model="patient.id" readonly />
          </div>
          <div>
            <base-input
              type="text"
              name="الإسم"
              data-cy="name"
              label="الإسم"
              placeholder="الإسم"
              alternative
              v-model="$v.patient.name.$model"
              :rules="{ required: true, max: 30 }"
            />
          </div>
          <div>
            <base-input
              type="text"
              name="رقم التواصل"
              data-cy="contactNumber"
              label="رقم التواصل"
              placeholder="رقم التواصل"
              alternative
              v-model="$v.patient.contactNumber.$model"
              :rules="{ required: true, max: 11 }"
            />
          </div>

          <div>
            <base-input
              type="text"
              name="العمر"
              data-cy="age"
              label="العمر"
              placeholder="العمر"
              alternative
              v-model="$v.patient.age.$model"
              :rules="{ required: true, max: 3 }"
            />
          </div>

          <div class="form-group">
            <label class="form-control-label" for="visit-clinic">النوع</label>
            <select class="form-control" aria-placeholder="النوع" id="gender" data-cy="clinic" name="clinic" v-model="patient.gender">
              <option v-bind:value="null"></option>
              <option v-bind:value="patient.gender && 'Male' === patient.gender ? patient.gender : 'Male'" :key="'Male'">ذكر</option>
              <option v-bind:value="patient.gender && 'Female' === patient.gender ? patient.gender : 'Female'" :key="'Female'">أنثى</option>
            </select>
          </div>

          <div v-if="patient.fileNumber">
            <base-input
              type="text"
              name="رقم الملف"
              data-cy="fileNumber"
              label="رقم الملف"
              placeholder="رقم الملف"
              readonly
              alternative
              v-model="$v.patient.fileNumber.$model"
            />
          </div>

          <div>
            <base-input
              type="text"
              name="مكان الإقامة"
              data-cy="placeOfResidence"
              label="مكان الإقامة"
              placeholder="مكان الإقامة"
              alternative
              v-model="$v.patient.placeOfResidence.$model"
              :rules="{ required: true, max: 30 }"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="patient-dateOfBirth">تاريخ الميلاد</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="patient-dateOfBirth"
                  v-model="$v.patient.dateOfBirth.$model"
                  name="dateOfBirth"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="patient-dateOfBirth"
                data-cy="dateOfBirth"
                type="text"
                class="form-control"
                name="dateOfBirth"
                :class="{ valid: !$v.patient.dateOfBirth.$invalid, invalid: $v.patient.dateOfBirth.$invalid }"
                v-model="$v.patient.dateOfBirth.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.patient.dateOfBirth.$anyDirty && $v.patient.dateOfBirth.$invalid">
              <small class="form-text text-danger" v-if="!$v.patient.dateOfBirth.required"> تاريخ الميلاد مطلوب </small>
            </div>
          </div>
          <div>
            <base-input
              type="text"
              name="فصيلة الدم"
              data-cy="bloodGroup"
              label="فصيلة الدم"
              placeholder="فصيلة الدم"
              alternative
              v-model="$v.patient.bloodGroup.$model"
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
              v-model="$v.patient.phoneNumber.$model"
              :rules="{ required: true, max: 11 }"
            />
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
            :disabled="$v.patient.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>حفظ</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./patient-update.component.ts"></script>
