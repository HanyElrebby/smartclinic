<template>
  <div>
    <div class="d-flex justify-content-center">
      <b-form
        class="navbar-search form-inline ml-sm-3 navbar-search-light"
        id="navbar-search-main"
        v-if="checkAction('com.smartclinic.patient.search')"
      >
        <b-form-group class="mb-0">
          <!-- <b-input-group class="input-group-alternative input-group-merge">
            <b-form-input placeholder="إبحث" v-model="searchValue" style="margin-right: 10px" type="text"> </b-form-input>

            <div class="input-group-append" style="margin-top: 10px; margin-left: 5px">
              <font-awesome-icon v-on:click="handleSyncList" icon="search"></font-awesome-icon>
            </div>
          </b-input-group> -->
          <vue-suggestion
            :items="items"
            v-model="item"
            :setLabel="setLabel"
            :itemTemplate="itemTemplate"
            @changed="inputChange"
            @selected="itemSelected"
          >
          </vue-suggestion>
        </b-form-group>
      </b-form>
      <router-link v-if="checkAction('com.smartclinic.patient.new')" :to="{ name: 'PatientCreate' }" custom v-slot="{ navigate }">
        <button
          @click="navigate"
          id="jh-create-entity"
          data-cy="entityCreateButton"
          class="btn btn-primary jh-create-entity create-patient"
        >
          <font-awesome-icon icon="plus"></font-awesome-icon>
          <span> مريض جديد </span>
        </button>
      </router-link>
    </div>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && patients && patients.length === 0">
      <span>لا يوجد مرضى</span>
    </div>
    <div class="table-responsive" v-if="patients && patients.length > 0">
      <el-table class="table-responsive table" header-row-class-name="thead-light" :data="patients">
        <el-table-column label="الإسم" prop="name"> </el-table-column>
        <el-table-column label="رقم الهاتف" prop="phoneNumber"> </el-table-column>
        <el-table-column label="رقم الملف" prop="fileNumber"> </el-table-column>
        <el-table-column
          label="إجراء"
          prop="id"
          v-if="
            checkAction('com.smartclinic.patient.view') ||
            checkAction('com.smartclinic.patient.edit') ||
            checkAction('com.smartclinic.patient.addfile') ||
            checkAction('com.smartclinic.patient.reserve')
          "
        >
          <template v-slot="{ row }">
            <div class="btn-group">
              <router-link
                :to="{ name: 'PatientView', params: { patientId: row.id } }"
                v-if="checkAction('com.smartclinic.patient.view')"
                custom
                v-slot="{ navigate }"
              >
                <button @click="navigate" class="btn btn-info btn-sm edit" data-cy="entityDetailsButton">
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline"> بيانات </span>
                </button>
              </router-link>
              <router-link
                v-if="checkAction('com.smartclinic.patient.edit')"
                :to="{ name: 'PatientEdit', params: { patientId: row.id } }"
                custom
                v-slot="{ navigate }"
              >
                <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline">تعديل</span>
                </button>
              </router-link>
              <router-link
                v-if="checkAction('com.smartclinic.patient.addfile')"
                :to="{ name: 'FileOtherCreate', params: { patientId: row.id } }"
                custom
                v-slot="{ navigate }"
              >
                <button @click="navigate" class="btn btn-secondary btn-sm edit" data-cy="entityEditButton">
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline">إضافة ملف</span>
                </button>
              </router-link>
              <router-link
                v-if="checkAction('com.smartclinic.patient.reserve')"
                :to="{ name: 'VisitotherCreate', params: { patientId: row.id } }"
                custom
                v-slot="{ navigate }"
              >
                <button @click="navigate" class="btn btn-success btn-sm details" data-cy="entityDetailsButton">
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline"> حجز </span>
                </button>
              </router-link>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <hr />

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

<!-- some-sample-css-as-example-for-your-dropdown-autocomplete  -->
<style>
.vue-suggestion .vs__list {
  width: 100%;
  text-align: left;
  border: none;
  border-top: none;
  max-height: 400px;
  overflow-y: auto;
  border-bottom: 1px solid #023d7b;
  position: relative;
}
.vue-suggestion .vs__list .vs__list-item {
  background-color: #fff;
  padding: 10px;
  border-left: 1px solid #023d7b;
  border-right: 1px solid #023d7b;
}
.vue-suggestion .vs__list .vs__list-item:last-child {
  border-bottom: none;
}
.vue-suggestion .vs__list .vs__list-item:hover {
  background-color: #eee !important;
}
.vue-suggestion .vs__list,
.vue-suggestion .vs__loading {
  position: absolute;
}
.vue-suggestion .vs__list .vs__list-item {
  cursor: pointer;
}
.vue-suggestion .vs__list .vs__list-item.vs__item-active {
  background-color: #f3f6fa;
}
</style>

<script lang="ts" src="./patient.component.ts"></script>
