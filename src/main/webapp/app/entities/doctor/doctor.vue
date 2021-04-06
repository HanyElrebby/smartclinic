<template>
  <div>
    <h2 id="page-heading" data-cy="DoctorHeading">
      <div class="d-flex justify-content-center">
        <button class="btn btn-info ml-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>تحديث الجدول</span>
        </button>
        <router-link :to="{ name: 'DoctorCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-doctor"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> انشاء طبيب </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && doctors && doctors.length === 0">
      <span>لا يوجد أطباء</span>
    </div>
    <div class="table-responsive" v-if="doctors && doctors.length > 0">
      <el-table class="table-responsive table" header-row-class-name="thead-light" :data="doctors">
        <el-table-column label="الكود" prop="id" min-width="140px"> </el-table-column>
        <el-table-column label="الإسم" prop="name"> </el-table-column>
        <el-table-column label="التخصص" prop="specialization"> </el-table-column>
        <el-table-column label="رقم الهاتف" prop="phoneNumber"> </el-table-column>

        <el-table-column label="إجراء" prop="id">
          <template v-slot="{ row }">
            <div class="btn-group">
              <router-link :to="{ name: 'DoctorView', params: { doctorId: row.id } }" custom v-slot="{ navigate }">
                <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline">مشاهدة</span>
                </button>
              </router-link>
              <router-link :to="{ name: 'DoctorEdit', params: { doctorId: row.id } }" custom v-slot="{ navigate }">
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

      <!--<table class="table table-striped" aria-describedby="doctors">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span>First Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'firstName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('lastName')">
              <span>Last Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lastName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('specialization')">
              <span>Specialization</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'specialization'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('phoneNumber')">
              <span>Phone Number</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'phoneNumber'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="doctor in doctors" :key="doctor.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'DoctorView', params: { doctorId: doctor.id } }">{{ doctor.id }}</router-link>
            </td>
            <td>{{ doctor.firstName }}</td>
            <td>{{ doctor.lastName }}</td>
            <td>{{ doctor.specialization }}</td>
            <td>{{ doctor.phoneNumber }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'DoctorView', params: { doctorId: doctor.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'DoctorEdit', params: { doctorId: doctor.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(doctor)"
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
        ><span id="smartclinicApp.doctor.delete.question" data-cy="doctorDeleteDialogHeading">تأكيد عملية الحذف</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-doctor-heading">هل تريد حقا حذف هذا الطبيب؟</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">إلغاء</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-doctor"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeDoctor()"
        >
          حذف
        </button>
      </div>
    </b-modal>
    <div v-show="doctors && doctors.length > 0">
      <!--<div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>-->
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./doctor.component.ts"></script>
