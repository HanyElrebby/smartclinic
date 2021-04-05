<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="smartclinicApp.visit.home.createOrEditLabel" data-cy="VisitCreateUpdateHeading">إنشاء او تعديل زيارة</h2>
        <div>
          <div class="form-group">
            <label class="form-control-label" for="visit-patient">المريض</label>
            <select class="form-control" id="visit-patient" data-cy="patient" name="patient" v-model="visit.patient" disabled>
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
          <div class="form-group">
            <label class="form-control-label" for="visit-type">نوع الزيارة</label>
            <select
              class="form-control"
              aria-placeholder="نوع الزيارة"
              id="visit-type"
              data-cy="visitType"
              name="visitType"
              v-model="visit.visitType"
            >
              <option v-bind:value="null"></option>
              <option v-bind:value="visit.visitType && 'Reveal' === visit.visitType ? visit.visitType : 'Reveal'" :key="'Reveal'">
                كشف
              </option>
              <option v-bind:value="visit.visitType && 'Repeat' === visit.visitType ? visit.visitType : 'Repeat'" :key="'Repeat'">
                أعادة
              </option>
              <option v-bind:value="visit.visitType && 'Other' === visit.visitType ? visit.visitType : 'Other'" :key="'Other'">أخرى</option>
            </select>
          </div>
          <div v-if="$v.visit.visitType.$anyDirty && $v.visit.visitType.$invalid">
            <small class="form-text text-danger" v-if="!$v.visit.visitType.required"> نوع الزيارة مطلوب </small>
          </div>
          <div>
            <base-input
              type="number"
              name="السعر"
              data-cy="cost"
              label="السعر"
              placeholder="السعر"
              alternative
              v-model="$v.visit.cost.$model"
            />
          </div>
          <div v-if="visit.id">
            <base-input type="text" label="الكود" name="id" v-model="visit.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="visit-type">تاريخ الزيارة</label>
            <div class="d-flex">
              <!-- <base-input
                data-cy="dateOfVisit"
                type="datetime-local"
                name="تاريخ الزيارة"
                label="تاريخ الزيارة"
                placeholder="تاريخ الزيارة"
                alternative
                :value="convertDateTimeFromServer($v.visit.dateOfVisit.$model)"
                @change="updateZonedDateTimeField('dateOfVisit', $event)"
                :rules="{ required: true, max: 30 }"
              /> -->

              <datetime v-model="value1" @change="updateZonedDateTimeField('dateOfVisit', $event)" type="datetime">
                <template slot="button-cancel"> ألغاء </template>
                <template slot="button-confirm"> تاكيد </template>
              </datetime>
            </div>
          </div>
          <!-- <date-picker v-model="$v.value" type="datetime"></date-picker>-->
          <div v-if="$v.value1.$anyDirty && $v.value1.$invalid">
            <small class="form-text text-danger" v-if="!$v.value1.required"> تاريخ الزيارة مطلوب </small>
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
