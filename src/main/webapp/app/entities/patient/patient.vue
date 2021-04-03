<template>
  <div>
    <h2 id="page-heading" data-cy="PatientHeading">
      <span id="patient-heading">المرضى</span>
      <div class="d-flex justify-content-end">
        <b-form class="navbar-search form-inline ml-sm-3 navbar-search-light" id="navbar-search-main">
          <b-form-group class="mb-0">
            <b-input-group class="input-group-alternative input-group-merge">
              <b-form-input placeholder="إبحث" v-model="searchValue" style="margin-right: 10px" type="text"> </b-form-input>

              <div class="input-group-append" style="margin-top: 10px; margin-left: 5px">
                <font-awesome-icon v-on:click="handleSyncList" icon="search"></font-awesome-icon>
              </div>
            </b-input-group>
          </b-form-group>
        </b-form>
        <button class="btn btn-info ml-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>تحديث الجدول</span>
        </button>
        <router-link :to="{ name: 'PatientCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-patient"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> إنشاء مريض جديد </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && patients && patients.length === 0">
      <span>لا يوجد مرضى</span>
    </div>
    <div class="table-responsive" v-if="patients && patients.length > 0">
      <el-table class="table-responsive table" header-row-class-name="thead-light" :data="patients">
        <el-table-column label="الكود" prop="id" max-width="40px"> </el-table-column>
        <el-table-column label="الإسم" prop="name"> </el-table-column>
        <el-table-column label="Pesel" prop="pesel"> </el-table-column>
        <el-table-column label="الإسم الأول للأب" prop="firstFatherName"> </el-table-column>
        <el-table-column label="رقم التواصل" prop="contactNumber"> </el-table-column>
        <el-table-column label="مكان الإقامة" prop="placeOfResidence"> </el-table-column>
        <el-table-column label="تاريخ الميلاد" prop="dateOfBirth"> </el-table-column>
        <el-table-column label="فصيلة الدم" prop="bloodGroup"> </el-table-column>
        <el-table-column label="رقم الهاتف" prop="phoneNumber"> </el-table-column>

        <el-table-column label="إجراء" prop="id">
          <template v-slot="{ row }">
            <div class="btn-group">
              <router-link :to="{ name: 'PatientView', params: { patientId: row.id } }" custom v-slot="{ navigate }">
                <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline">مشاهدة</span>
                </button>
              </router-link>
              <router-link :to="{ name: 'PatientEdit', params: { patientId: row.id } }" custom v-slot="{ navigate }">
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

      <!--<table class="table table-striped" aria-describedby="patients">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('firstName')">
              <span>First Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'firstName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('lastName')">
              <span>Last Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lastName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('pesel')">
              <span>Pesel</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'pesel'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('firstFatherName')">
              <span>First Father Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'firstFatherName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('contactNumber')">
              <span>Contact Number</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'contactNumber'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('placeOfResidence')">
              <span>Place Of Residence</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'placeOfResidence'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('dateOfBirth')">
              <span>Date Of Birth</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'dateOfBirth'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('bloodGroup')">
              <span>Blood Group</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'bloodGroup'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('phoneNumber')">
              <span>Phone Number</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'phoneNumber'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="patient in patients" :key="patient.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PatientView', params: { patientId: patient.id } }">{{ patient.id }}</router-link>
            </td>
            <td>{{ patient.firstName }}</td>
            <td>{{ patient.lastName }}</td>
            <td>{{ patient.pesel }}</td>
            <td>{{ patient.firstFatherName }}</td>
            <td>{{ patient.contactNumber }}</td>
            <td>{{ patient.placeOfResidence }}</td>
            <td>{{ patient.dateOfBirth }}</td>
            <td>{{ patient.bloodGroup }}</td>
            <td>{{ patient.phoneNumber }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PatientView', params: { patientId: patient.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PatientEdit', params: { patientId: patient.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(patient)"
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
        ><span id="smartclinicApp.patient.delete.question" data-cy="patientDeleteDialogHeading">تأكيد عملية الحذف</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-patient-heading">هل تريد حقا حذف هذا المريض؟</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">إلغاء</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-patient"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removePatient()"
        >
          حذف
        </button>
      </div>
    </b-modal>
    <div v-show="patients && patients.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./patient.component.ts"></script>
