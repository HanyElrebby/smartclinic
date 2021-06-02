<style>
.highcharts-container {
  margin-right: 240px;
  margin-top: 10px;
  margin-bottom: 10px;
}
</style>

<template>
  <div class="row">
    <div class="col-10">
      <div class="row d-flex justify-content-end">
        <div class="col-50">
          <router-link v-if="visit" :to="{ name: 'VisitEdit', params: { visitId: visit.id } }" custom v-slot="{ navigate }">
            <button @click="navigate" class="btn btn-success ml-2">
              <font-awesome-icon icon="sync"></font-awesome-icon> <span> الكشف الحالى</span>
            </button>
          </router-link>
          <router-link v-if="!visit" :to="{ name: 'VisitotherCreate', params: { patientId: patient.id } }" custom v-slot="{ navigate }">
            <button @click="navigate" class="btn btn-success ml-2">
              <font-awesome-icon icon="sync"></font-awesome-icon> <span> الكشف الحالى</span>
            </button>
          </router-link>
        </div>
      </div>
    </div>
    <div class="col-10 d-flex justify-content-center">
      <div v-if="patient.fileNumber">
        <dl class="row jh-entity-details">
          <dt>
            <span>الاسم</span>
          </dt>
          <dd>
            <span>{{ patient.name }}</span>
          </dd>
          <dt>
            <span>رقم الهاتف</span>
          </dt>
          <dd>
            <span>{{ patient.phoneNumber }}</span>
          </dd>
          <dt>
            <span> العمر</span>
          </dt>
          <dd>
            <span>{{ patient.age }}</span>
          </dd>
          <dt>
            <span> النوع</span>
          </dt>
          <dd>
            <span>{{ patient.gender }}</span>
          </dd>
          <dt>
            <span>فصيلة الدم</span>
          </dt>
          <dd>
            <span>{{ patient.bloodGroup }}</span>
          </dd>
          <dt>
            <span> العنوان</span>
          </dt>
          <dd>
            <span>{{ patient.placeOfResidence }}</span>
          </dd>
          <dt>
            <span> الامراض المزمنة </span>
          </dt>
          <dd>
            <span>{{ patient.diseases }}</span>
          </dd>
          <dt>
            <span> العمليات السابقة</span>
          </dt>
          <dd>
            <span>{{ patient.surgery }}</span>
          </dd>
        </dl>

        <h1 class="text-danger" style="min-width: 800px">الزيارات السابقة:</h1>
        <div class="table-responsive" v-if="patient.visits && patient.visits.length > 0">
          <el-table class="table-responsive table" header-row-class-name="thead-light" :data="patient.visits">
            <el-table-column label="نوع الزيارة" prop="visitType">
              <template v-slot="{ row }">
                {{ visitTypeVale(row.visitType) }}
              </template>
            </el-table-column>
            <el-table-column label="تاريخ الزيارة" prop="dateOfVisit"> </el-table-column>
            <el-table-column label="العيادة" prop="clinic.nameOfClinic">
              <template v-slot="{ row }">
                <div v-if="row.clinic">
                  <router-link :to="{ name: 'ClinicView', params: { clinicId: row.clinic.id } }">{{ row.clinic.nameOfClinic }}</router-link>
                </div>
              </template>
            </el-table-column>
            <el-table-column label="إجراء" prop="id">
              <template v-slot="{ row }">
                <div class="btn-group">
                  <router-link
                    v-if="visit"
                    :to="{ name: 'VisitViewNew', params: { visitId: row.id, currentVisitId: visit.id } }"
                    custom
                    v-slot="{ navigate }"
                  >
                    <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                      <font-awesome-icon icon="eye"></font-awesome-icon>
                      <span class="d-none d-md-inline">مشاهدة</span>
                    </button>
                  </router-link>
                  <router-link v-if="!visit" :to="{ name: 'VisitView', params: { visitId: row.id } }" custom v-slot="{ navigate }">
                    <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                      <font-awesome-icon icon="eye"></font-awesome-icon>
                      <span class="d-none d-md-inline">مشاهدة</span>
                    </button>
                  </router-link>
                </div>
              </template>
            </el-table-column>
          </el-table>
        </div>

        <h1 class="text-danger">الملفات:</h1>
        <div class="table-responsive" v-if="files && files.length > 0">
          <el-table class="table-responsive table" header-row-class-name="thead-light" :data="files">
            <el-table-column label="إسم الملف" prop="fileName"> </el-table-column>
            <el-table-column label="نوغ الملف" prop="fileContentType"> </el-table-column>
            <el-table-column label="تاريخ الانشاء" prop="dateUpload">
              <template v-slot="{ row }">
                {{ formatDate(row.dateUpload) }}
              </template>
            </el-table-column>
            <el-table-column label="إجراء" prop="id">
              <template v-slot="{ row }">
                <div class="btn-group">
                  <button
                    v-on:click="openFile(row.fileContentType, row.file)"
                    class="btn btn-info btn-sm details"
                    data-cy="entityDetailsButton"
                  >
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">مشاهدة</span>
                  </button>
                </div>
              </template>
            </el-table-column>
          </el-table>
        </div>

        <h1 v-else></h1>
        <div class="row" style="padding-top: 20px">
          <div class="col-md-6">
            <h1 class="text-danger">معدل النمو:</h1>
          </div>
          <div class="col-md-6">
            <b-button
              variant="success"
              style="width: 100%; height: 35px"
              class="btn btn-sm"
              data-cy="entityDeleteButton"
              v-b-modal.addLenght
            >
              <span class="d-none d-md-inline">اضافة جديد</span>
            </b-button>
          </div>
        </div>
      </div>
    </div>

    <highcharts :options="chartLengthOptions"></highcharts>
    <highcharts :options="chartWeightOptions"></highcharts>

    <b-modal ref="addLenght" id="addLenght" style="width: 1000px">
      <div class="modal-body">
        <form name="editForm" role="form" novalidate>
          <h2 id="smartclinicApp.medicine.home.createOrEditLabel" data-cy="MedicineCreateUpdateHeading">إضافة بيانات النمو</h2>
          <hr />
          <div>
            <div class="form-group row">
              <label class="col-md-2 col-form-label form-control-label" for="medicine-name">الطول</label>
              <div class="col-md-10">
                <base-input
                  type="number"
                  name="length"
                  id="length"
                  data-cy="length"
                  alternative
                  v-model="$v.chartValues.length.$model"
                  :rules="{ required: true }"
                />
              </div>
            </div>
            <div class="form-group row">
              <label class="col-md-2 col-form-label form-control-label" for="medicine-quantity">الوزن</label>
              <div class="col-md-10">
                <base-input
                  type="number"
                  name="weight"
                  id="weight"
                  data-cy="weight"
                  alternative
                  v-model="$v.chartValues.weight.$model"
                  :rules="{ required: true }"
                />
              </div>
            </div>
            <div class="form-group row">
              <label class="col-md-2 col-form-label form-control-label" for="medicine-quantity">العمر</label>
              <div class="col-md-10">
                <base-input
                  type="number"
                  name="age"
                  id="age"
                  data-cy="age"
                  alternative
                  v-model="$v.chartValues.age.$model"
                  :rules="{ required: true }"
                />
              </div>
            </div>
          </div>
        </form>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialogLength()">إلغاء</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-visit"
          data-cy="entityConfirmDeleteButton"
          v-on:click="
            saveLength();
            closeDialogLength();
          "
        >
          حفظ
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./patient-details.component.ts"></script>
