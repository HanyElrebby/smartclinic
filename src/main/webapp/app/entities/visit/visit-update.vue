<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="smartclinicApp.visit.home.createOrEditLabel" data-cy="VisitCreateUpdateHeading">إنشاء او تعديل زيارة</h2>
        <div>
          <div v-if="visit.id">
            <base-input type="text" label="الكود" name="id" v-model="visit.id" readonly />
          </div>
          <div>
            <div class="d-flex">
              <base-input
                data-cy="dateOfVisit"
                type="datetime-local"
                name="تاريخ الزيارة"
                label="تاريخ الزيارة"
                placeholder="تاريخ الزيارة"
                alternative
                :value="convertDateTimeFromServer($v.visit.dateOfVisit.$model)"
                @change="updateZonedDateTimeField('dateOfVisit', $event)"
                :rules="{ required: true, max: 30 }"
              />
            </div>
          </div>
          <div>
            <div class="d-flex">
              <base-input
                data-cy="visitType"
                type="text"
                name="نوع الزيارة"
                label="نوع الزيارة"
                placeholder="نوع الزيارة"
                alternative
                v-model="$v.visit.visitType.$model"
                :rules="{ required: true, max: 30 }"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="visit-clinic">العيادة</label>
            <select class="form-control" id="visit-clinic" data-cy="clinic" name="clinic" v-model="visit.clinic">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="visit.clinic && clinicOption.id === visit.clinic.id ? visit.clinic : clinicOption"
                v-for="clinicOption in clinics"
                :key="clinicOption.id"
              >
                {{ clinicOption.nameOfClinic }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="visit-patient">المريض</label>
            <select class="form-control" id="visit-patient" data-cy="patient" name="patient" v-model="visit.patient">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="visit.patient && patientOption.id === visit.patient.id ? visit.patient : patientOption"
                v-for="patientOption in patients"
                :key="patientOption.id"
              >
                {{ patientOption.name }}
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
            :disabled="$v.visit.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>حفظ</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./visit-update.component.ts"></script>
