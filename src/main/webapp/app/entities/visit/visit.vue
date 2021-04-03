<template>
  <div>
    <h2 id="page-heading" data-cy="VisitHeading">
      <span id="visit-heading">Visits</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'VisitCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-visit"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Visit </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && visits && visits.length === 0">
      <span>No visits found</span>
    </div>
    <div class="table-responsive" v-if="visits && visits.length > 0">
      <el-table class="table-responsive table" header-row-class-name="thead-light" :data="visits">
        <el-table-column label="ID" prop="id" min-width="140px"> </el-table-column>
        <el-table-column label="Date Of Visit" prop="dateOfVisit"> </el-table-column>
        <el-table-column label="Clinic" prop="clinic.nameOfClinic">
          <template v-slot="{ row }">
            <div v-if="row.clinic">
              <router-link :to="{ name: 'ClinicView', params: { clinicId: row.clinic.id } }">{{ row.clinic.nameOfClinic }}</router-link>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="Patient" prop="patient.name">
          <template v-slot="{ row }">
            <div v-if="row.patient">
              <router-link :to="{ name: 'PatientView', params: { patientId: row.patient.id } }">{{ row.patient.name }}</router-link>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="Action" prop="id">
          <template v-slot="{ row }">
            <div class="btn-group">
              <router-link :to="{ name: 'VisitView', params: { visitId: row.id } }" custom v-slot="{ navigate }">
                <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline">View</span>
                </button>
              </router-link>
              <router-link :to="{ name: 'VisitEdit', params: { visitId: row.id } }" custom v-slot="{ navigate }">
                <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline">Edit</span>
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
                <span class="d-none d-md-inline">Delete</span>
              </b-button>
            </div>
          </template>
        </el-table-column>
      </el-table>

      <!--<table class="table table-striped" aria-describedby="visits">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('dateOfVisit')">
              <span>Date Of Visit</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'dateOfVisit'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('clinic.nameOfClinic')">
              <span>Clinic</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'clinic.nameOfClinic'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('patient.firstName')">
              <span>Patient</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'patient.firstName'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="visit in visits" :key="visit.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'VisitView', params: { visitId: visit.id } }">{{ visit.id }}</router-link>
            </td>
            <td>{{ visit.dateOfVisit | formatDate }}</td>
            <td>
              <div v-if="visit.clinic">
                <router-link :to="{ name: 'ClinicView', params: { clinicId: visit.clinic.id } }">{{
                  visit.clinic.nameOfClinic
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="visit.patient">
                <router-link :to="{ name: 'PatientView', params: { patientId: visit.patient.id } }">{{
                  visit.patient.firstName
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'VisitView', params: { visitId: visit.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'VisitEdit', params: { visitId: visit.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(visit)"
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
        ><span id="smartclinicApp.visit.delete.question" data-cy="visitDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-visit-heading">Are you sure you want to delete this Visit?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-visit"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeVisit()"
        >
          Delete
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
