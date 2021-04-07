<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="smartclinicApp.file.home.createOrEditLabel" data-cy="FileCreateUpdateHeading">Create or edit a File</h2>
        <div>
          <div class="form-group" v-if="file.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="file.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-fileName">File Name</label>
            <input
              type="text"
              class="form-control"
              name="fileName"
              id="file-fileName"
              data-cy="fileName"
              :class="{ valid: !$v.file.fileName.$invalid, invalid: $v.file.fileName.$invalid }"
              v-model="$v.file.fileName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-file">File</label>
            <div>
              <div v-if="file.file" class="form-text text-danger clearfix">
                <a class="pull-left" v-on:click="openFile(file.fileContentType, file.file)">open</a><br />
                <span class="pull-left">{{ file.fileContentType }}, {{ byteSize(file.file) }}</span>
                <button
                  type="button"
                  v-on:click="
                    file.file = null;
                    file.fileContentType = null;
                  "
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <input type="file" ref="file_file" id="file_file" data-cy="file" v-on:change="setFileData($event, file, 'file', false)" />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="file"
              id="file-file"
              data-cy="file"
              :class="{ valid: !$v.file.file.$invalid, invalid: $v.file.file.$invalid }"
              v-model="$v.file.file.$model"
            />
            <input type="hidden" class="form-control" name="fileContentType" id="file-fileContentType" v-model="file.fileContentType" />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-dateUpload">Date Upload</label>
            <div class="d-flex">
              <input
                id="file-dateUpload"
                data-cy="dateUpload"
                type="datetime-local"
                class="form-control"
                name="dateUpload"
                :class="{ valid: !$v.file.dateUpload.$invalid, invalid: $v.file.dateUpload.$invalid }"
                :value="convertDateTimeFromServer($v.file.dateUpload.$model)"
                @change="updateZonedDateTimeField('dateUpload', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-note">Note</label>
            <input
              type="text"
              class="form-control"
              name="note"
              id="file-note"
              data-cy="note"
              :class="{ valid: !$v.file.note.$invalid, invalid: $v.file.note.$invalid }"
              v-model="$v.file.note.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-createdBy">Created By</label>
            <input
              type="text"
              class="form-control"
              name="createdBy"
              id="file-createdBy"
              data-cy="createdBy"
              :class="{ valid: !$v.file.createdBy.$invalid, invalid: $v.file.createdBy.$invalid }"
              v-model="$v.file.createdBy.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-updatedBy">Updated By</label>
            <input
              type="text"
              class="form-control"
              name="updatedBy"
              id="file-updatedBy"
              data-cy="updatedBy"
              :class="{ valid: !$v.file.updatedBy.$invalid, invalid: $v.file.updatedBy.$invalid }"
              v-model="$v.file.updatedBy.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-patient">Patient</label>
            <select class="form-control" id="file-patient" data-cy="patient" name="patient" v-model="file.patient">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="file.patient && patientOption.id === file.patient.id ? file.patient : patientOption"
                v-for="patientOption in patients"
                :key="patientOption.id"
              >
                {{ patientOption.name }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.file.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./file-update.component.ts"></script>
