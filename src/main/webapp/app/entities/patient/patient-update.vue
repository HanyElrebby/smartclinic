<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="smartclinicApp.patient.home.createOrEditLabel" data-cy="PatientCreateUpdateHeading">إنشاء او تعديل مريض</h2>

        <hr />
        <div>
          <div class="form-group row" v-if="patient.id">
            <label for="example-email-input" class="col-md-2 col-form-label form-control-label">الكود</label>
            <div class="col-md-10">
              <base-input type="text" name="id" v-model="patient.id" readonly />
            </div>
          </div>

          <div class="form-group row">
            <label for="example-email-input" class="col-md-2 col-form-label form-control-label">رقم الملف</label>
            <div class="col-md-10">
              <base-input type="text" name="رقم الملف" data-cy="fileNumber" readonly alternative v-model="$v.patient.fileNumber.$model" />
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
                v-model="$v.patient.name.$model"
                :rules="{ required: true, max: 30 }"
              />
            </div>
          </div>

          <div class="form-group row" v-if="checkAction('com.smartclinic.patient.new.phone')">
            <label for="example-email-input" class="col-md-2 col-form-label form-control-label">رقم الهاتف</label>
            <div class="col-md-10">
              <base-input
                type="number"
                name="رقم الهاتف"
                data-cy="phoneNumber"
                alternative
                v-model="$v.patient.phoneNumber.$model"
                :rules="{ required: true, max: 11 }"
              />
            </div>
          </div>

          <div class="form-group row" v-if="checkAction('com.smartclinic.patient.new.age')">
            <label for="example-email-input" class="col-md-2 col-form-label form-control-label">العمر</label>
            <div class="col-md-10">
              <base-input
                type="text"
                name="العمر"
                data-cy="age"
                alternative
                v-model="$v.patient.age.$model"
                :rules="{ required: true, max: 3 }"
              />
            </div>
          </div>

          <div class="form-group row" v-if="checkAction('com.smartclinic.patient.new.gender')">
            <label for="example-email-input" class="col-md-2 col-form-label form-control-label">النوع</label>
            <div class="col-md-10">
              <select class="form-control" id="gender" data-cy="clinic" name="clinic" v-model="patient.gender">
                <option v-bind:value="null"></option>
                <option v-bind:value="patient.gender && 'Male' === patient.gender ? patient.gender : 'Male'" :key="'Male'">ذكر</option>
                <option v-bind:value="patient.gender && 'Female' === patient.gender ? patient.gender : 'Female'" :key="'Female'">
                  أنثى
                </option>
              </select>
            </div>
            <div v-if="$v.patient.gender.$anyDirty && $v.patient.gender.$invalid">
              <small class="form-text text-danger" v-if="!$v.patient.gender.required"> النوع مطلوب </small>
            </div>
          </div>

          <div class="form-group row" v-if="checkAction('com.smartclinic.patient.new.address')">
            <label for="example-email-input" class="col-md-2 col-form-label form-control-label">العنوان</label>
            <div class="col-md-10">
              <base-input
                type="text"
                name="العنوان "
                data-cy="placeOfResidence"
                alternative
                v-model="$v.patient.placeOfResidence.$model"
                :rules="{ required: true, max: 30 }"
              />
            </div>
          </div>

          <div class="form-group row" v-if="checkAction('com.smartclinic.patient.new.disease')">
            <label for="example-email-input" class="col-md-2 col-form-label form-control-label">الامراض المزمنة</label>
            <div class="col-md-10">
              <textarea
                class="form-control"
                rows="2"
                name="الامراض المزمنة "
                data-cy="diseases"
                alternative
                v-model="$v.patient.diseases.$model"
                :rules="{ max: 1000 }"
              ></textarea>
            </div>
          </div>

          <div class="form-group row" v-if="checkAction('com.smartclinic.patient.new.operations')">
            <label for="example-email-input" class="col-md-2 col-form-label form-control-label">العمليات السابقة </label>
            <div class="col-md-10">
              <textarea
                class="form-control"
                type="text"
                rows="2"
                name="العمليات السابقة "
                data-cy="surgery"
                alternative
                v-model="$v.patient.surgery.$model"
                :rules="{ max: 1000 }"
              ></textarea>
            </div>
          </div>

          <div class="form-group row" v-if="checkAction('com.smartclinic.patient.new.bloodType')">
            <label for="example-email-input" class="col-md-2 col-form-label form-control-label">فصيلة الدم</label>
            <div class="col-md-10">
              <select class="form-control" id="bloodGroup" data-cy="bloodGroup" name="bloodGroup" v-model="patient.bloodGroup">
                <option v-bind:value="null"></option>
                <option v-bind:value="patient.bloodGroup && '-O' === patient.bloodGroup ? patient.bloodGroup : '-O'" :key="'-O'">-O</option>
                <option v-bind:value="patient.bloodGroup && '+O' === patient.bloodGroup ? patient.bloodGroup : '+O'" :key="'+O'">+O</option>
                <option v-bind:value="patient.bloodGroup && '-A' === patient.bloodGroup ? patient.bloodGroup : '-A'" :key="'-A'">-A</option>
                <option v-bind:value="patient.bloodGroup && '+A' === patient.bloodGroup ? patient.bloodGroup : '+A'" :key="'+A'">+A</option>
                <option v-bind:value="patient.bloodGroup && '-B' === patient.bloodGroup ? patient.bloodGroup : '-B'" :key="'-B'">-B</option>
                <option v-bind:value="patient.bloodGroup && '+B' === patient.bloodGroup ? patient.bloodGroup : '+B'" :key="'+B'">+B</option>
                <option v-bind:value="patient.bloodGroup && '-AB' === patient.bloodGroup ? patient.bloodGroup : '-AB'" :key="'-AB'">
                  -AB
                </option>
                <option v-bind:value="patient.bloodGroup && '+AB' === patient.bloodGroup ? patient.bloodGroup : '+AB'" :key="'+AB'">
                  +AB
                </option>
              </select>
            </div>
            <div v-if="$v.patient.bloodGroup.$anyDirty && $v.patient.bloodGroup.$invalid">
              <small class="form-text text-danger" v-if="!$v.patient.bloodGroup.required"> فصيلة الدم مطلوبة </small>
            </div>
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
