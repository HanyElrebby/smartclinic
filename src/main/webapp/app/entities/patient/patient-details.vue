<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="patient">
        <h2 class="jh-entity-heading" data-cy="patientDetailsHeading"><span>المريض</span> {{ patient.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>الاسم</span>
          </dt>
          <dd>
            <span>{{ patient.name }}</span>
          </dd>
          <dt>
            <span>Pesel</span>
          </dt>
          <dd>
            <span>{{ patient.pesel }}</span>
          </dd>
          <dt>
            <span>الإسم الأول للأب</span>
          </dt>
          <dd>
            <span>{{ patient.firstFatherName }}</span>
          </dd>
          <dt>
            <span>رقم التواصل</span>
          </dt>
          <dd>
            <span>{{ patient.contactNumber }}</span>
          </dd>
          <dt>
            <span>مكان الإقامة</span>
          </dt>
          <dd>
            <span>{{ patient.placeOfResidence }}</span>
          </dd>
          <dt>
            <span>تاريخ الميلاد</span>
          </dt>
          <dd>
            <span>{{ patient.dateOfBirth }}</span>
          </dd>
          <dt>
            <span>فصيلة الدم</span>
          </dt>
          <dd>
            <span>{{ patient.bloodGroup }}</span>
          </dd>
          <dt>
            <span>رقم الهاتف</span>
          </dt>
          <dd>
            <span>{{ patient.phoneNumber }}</span>
          </dd>
        </dl>
        <div class="table-responsive" v-if="patient.visits && patient.visits.length > 0">
          <el-table class="table-responsive table" header-row-class-name="thead-light" :data="patient.visits">
            <el-table-column label="الكود" prop="id" min-width="140px"> </el-table-column>
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
          </el-table>
        </div>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-right"></font-awesome-icon>&nbsp;<span> رجوع</span>
        </button>
        <router-link v-if="patient.id" :to="{ name: 'PatientEdit', params: { patientId: patient.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> تعديل</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./patient-details.component.ts"></script>
