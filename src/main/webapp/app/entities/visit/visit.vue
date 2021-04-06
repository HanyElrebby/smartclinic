<template>
  <div>
    <div class="d-flex justify-content-start form-group">
      <div class="d-flex">
        <datetime v-model="value1" type="date" class="form-control">
          <template slot="button-cancel"> ألغاء </template>
          <template slot="button-confirm"> تاكيد </template>
        </datetime>
      </div>
      <button class="btn btn-info ml-2" v-on:click="handleSyncList" :disabled="isFetching">
        <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>تحديث الجدول</span>
      </button>
    </div>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && visits && visits.length === 0">
      <span>لا يوجد زيارات</span>
    </div>
    <div class="table-responsive" v-if="visits && visits.length > 0">
      <el-table class="table-responsive table" header-row-class-name="thead-light" :data="visits">
        <el-table-column label="اسم المريض" prop="patient.name">
          <template v-slot="{ row }">
            <div v-if="row.patient">
              <router-link :to="{ name: 'PatientView', params: { patientId: row.patient.id } }">{{ row.patient.name }}</router-link>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="نوع الزيارة" prop="visitType">
          <template v-slot="{ row }">
            {{ visitTypeVale(row.visitType) }}
          </template>
        </el-table-column>
        <el-table-column label="تاريخ الزيارة" prop="dateOfVisit">
          <template v-slot="{ row }">
            {{ formatDateView(row.dateOfVisit) }}
          </template>
        </el-table-column>
        <el-table-column label="العيادة" prop="clinic.nameOfClinic">
          <template v-slot="{ row }">
            <div v-if="row.clinic">
              <router-link :to="{ name: 'ClinicView', params: { clinicId: row.clinic.id } }">{{ row.clinic.nameOfClinic }}</router-link>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="التكلفه" prop="cost"> </el-table-column>
        <el-table-column label="إجراء" prop="id">
          <template v-slot="{ row }">
            <div class="btn-group">
              <router-link :to="{ name: 'VisitEdit', params: { visitId: row.id } }" custom v-slot="{ navigate }">
                <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline">تعديل</span>
                </button>
              </router-link>
              <b-button
                v-on:click="prepareRemove(row)"
                variant="danger"
                class="btn btn-sm"
                data-cy="entityDeleteButton"
                v-b-modal.removeEntity
              >
                <font-awesome-icon icon="times"></font-awesome-icon>
                <span class="d-none d-md-inline">حذف</span>
              </b-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="smartclinicApp.visit.delete.question" data-cy="visitDeleteDialogHeading">تأكيد عملية الحذف</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-visit-heading">هل تريد حقا حذف هذه الزيارة؟</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">إلغاء</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-visit"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeVisit()"
        >
          حذف
        </button>
      </div>
    </b-modal>
    <div v-show="visits && visits.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./visit.component.ts"></script>
