<template>
  <div class="row">
    <div class="col-10">
      <div class="row d-flex justify-content-end">
        <div class="col-50">
          <router-link :to="{ name: 'VisitotherCreate', params: { patientId: patient.id } }" custom v-slot="{ navigate }">
            <button @click="navigate" class="btn btn-success ml-2">
              <font-awesome-icon icon="sync"></font-awesome-icon> <span> الكشف الحالى</span>
            </button>
          </router-link>
        </div>
      </div>
    </div>
    <div class="col-10 d-flex justify-content-center">
      <div v-if="patient">
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

        <h1 class="text-danger">الزيارات السابقة:</h1>
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
                  <router-link :to="{ name: 'VisitView', params: { visitId: row.id } }" custom v-slot="{ navigate }">
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
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./patient-details.component.ts"></script>
