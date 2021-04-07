<template>
  <div>
    <h2 id="page-heading" data-cy="FileHeading">
      <span id="file-heading">Files</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'FileCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-file">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new File </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && files && files.length === 0">
      <span>No files found</span>
    </div>
    <div class="table-responsive" v-if="files && files.length > 0">
      <table class="table table-striped" aria-describedby="files">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fileName')">
              <span>File Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fileName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('file')">
              <span>File</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'file'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('dateUpload')">
              <span>Date Upload</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'dateUpload'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('note')">
              <span>Note</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'note'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdBy')">
              <span>Created By</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('updatedBy')">
              <span>Updated By</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'updatedBy'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('patient.name')">
              <span>Patient</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'patient.name'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="file in files" :key="file.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'FileView', params: { fileId: file.id } }">{{ file.id }}</router-link>
            </td>
            <td>{{ file.fileName }}</td>
            <td>
              <a v-if="file.file" v-on:click="openFile(file.fileContentType, file.file)">open</a>
              <span v-if="file.file">{{ file.fileContentType }}, {{ byteSize(file.file) }}</span>
            </td>
            <td>{{ file.dateUpload | formatDate }}</td>
            <td>{{ file.note }}</td>
            <td>{{ file.createdBy }}</td>
            <td>{{ file.updatedBy }}</td>
            <td>
              <div v-if="file.patient">
                <router-link :to="{ name: 'PatientView', params: { patientId: file.patient.id } }">{{ file.patient.name }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'FileView', params: { fileId: file.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'FileEdit', params: { fileId: file.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(file)"
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
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="smartclinicApp.file.delete.question" data-cy="fileDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-file-heading">Are you sure you want to delete this File?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-file"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeFile()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="files && files.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./file.component.ts"></script>
