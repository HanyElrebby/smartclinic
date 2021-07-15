<template>
  <div>
    <h2 id="page-heading" data-cy="ClinicHeading">
      <div class="d-flex justify-content-center">
        <button class="btn btn-info ml-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('entities.refreshTable')">تحديث الجدول</span>
        </button>
        <router-link :to="{ name: 'ClinicCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-clinic"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('entities.createClinic')"> أنشاء عيادة </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && clinics && clinics.length === 0">
      <span v-text="$t('entities.noClinicsFound')">لا يوجد عيادات</span>
    </div>
    <div class="table-responsive" v-if="clinics && clinics.length > 0">
      <el-table class="table-responsive table" header-row-class-name="thead-light" :data="clinics">
        <el-table-column :label="translate('entities.id')" prop="id" min-width="140px"> </el-table-column>
        <el-table-column :label="translate('entities.clinicName')" prop="nameOfClinic"> </el-table-column>
        <el-table-column :label="translate('entities.city')" prop="City"> </el-table-column>
        <el-table-column :label="translate('entities.postalCode')" prop="postalCode"> </el-table-column>
        <el-table-column :label="translate('entities.street')" prop="street"> </el-table-column>
        <el-table-column :label="translate('entities.doctor')" prop="doctor.name">
          <template v-slot="{ row }">
            <div v-if="row.doctor">
              <router-link :to="{ name: 'DoctorView', params: { doctorId: row.doctor.id } }">{{ row.doctor.name }}</router-link>
            </div>
          </template>
        </el-table-column>
        <el-table-column :label="translate('entities.user')" prop="user.login"> </el-table-column>
        <el-table-column :label="translate('entities.action')" prop="id">
          <template v-slot="{ row }">
            <div class="btn-group">
              <router-link :to="{ name: 'ClinicView', params: { clinicId: row.id } }" custom v-slot="{ navigate }">
                <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entities.view')">مشاهدة</span>
                </button>
              </router-link>
              <router-link :to="{ name: 'ClinicEdit', params: { clinicId: row.id } }" custom v-slot="{ navigate }">
                <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entities.edit')">تعديل</span>
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
                <span class="d-none d-md-inline" v-text="$t('entities.delete')">حذف</span>
              </b-button>
            </div>
          </template>
        </el-table-column>
      </el-table>

      <!-- <table class="table tablesorter" aria-describedby="clinics">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('nameOfClinic')">
              <span>Name Of Clinic</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'nameOfClinic'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('city')">
              <span>City</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'City'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('postalCode')">
              <span>Postal Code</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'postalCode'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('street')">
              <span>Street</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'street'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('doctor.name')">
              <span>Doctor</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'doctor.name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('user.login')">
              <span>User</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'user.login'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody >
          <tr v-for="clinic in clinics" :key="clinic.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ClinicView', params: { clinicId: clinic.id } }">{{ clinic.id }}</router-link>
            </td>
            <td>{{ clinic.nameOfClinic }}</td>
            <td>{{ clinic.city }}</td>
            <td>{{ clinic.postalCode }}</td>
            <td>{{ clinic.street }}</td>
            <td>
              <div v-if="clinic.doctor">
                <router-link :to="{ name: 'DoctorView', params: { doctorId: clinic.doctor.id } }">{{
                  clinic.doctor.name
                }}</router-link>
              </div>
            </td>
            <td>
              {{ clinic.user ? clinic.user.login : '' }}
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ClinicView', params: { clinicId: clinic.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ClinicEdit', params: { clinicId: clinic.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(clinic)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>-->
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="smartclinicApp.clinic.delete.question" data-cy="clinicDeleteDialogHeading" v-text="$t('entities.confirmDeleteOperation')"
          >تأكيد عملية الحذف</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-clinic-heading" v-text="$t('entities.confirmDeleteClinic')">هل تريد حقا حذف هذه العيادة؟</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()" v-text="$t('entities.cancel')">إلغاء</button>
        <button
          v-text="$t('entities.delete')"
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-clinic"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeClinic()"
        >
          حذف
        </button>
      </div>
    </b-modal>
    <div v-show="clinics && clinics.length > 0">
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./clinic.component.ts"></script>
