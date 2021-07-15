<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="smartclinicApp.file.home.createOrEditLabel" data-cy="FileCreateUpdateHeading" v-text="$t('entities.createOrEditFile')">
          Create or edit a File
        </h2>
        <hr />
        <div>
          <div class="form-group row" v-if="file.id">
            <label class="col-md-2 col-form-label form-control-label" v-text="$t('entities.id')" for="id">ID</label>
            <div class="col-md-10">
              <base-input type="text" alternative id="id" :name="translate('entities.id')" v-model="file.id" readonly />
            </div>
          </div>
          <div class="form-group row">
            <label class="col-md-2 col-form-label form-control-label" v-text="$t('entities.patient')" for="file-patient">Patient</label>
            <div class="col-md-10">
              <select class="form-control" id="file-patient" data-cy="patient" disabled name="patient" v-model="file.patient">
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
          <div class="form-group row">
            <label class="col-md-2 col-form-label form-control-label" v-text="$t('entities.file')" for="file-file">File</label>
            <div class="col-md-10">
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
              <input
                type="file"
                ref="file_file"
                id="file_file"
                data-cy="file"
                v-on:change="
                  setFileData($event, file, 'file', false);
                  getFileName(file, $event);
                "
              />
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
          <div class="form-group row">
            <label class="col-md-2 col-form-label form-control-label" v-text="$t('entities.fileName')" for="file-fileName">File Name</label>
            <div class="col-md-10">
              <base-input
                type="text"
                :name="translate('entities.fileName')"
                id="file-fileName"
                alternative
                :rules="{ required: true }"
                data-cy="fileName"
                v-model="$v.file.fileName.$model"
              />
            </div>
          </div>
          <div class="form-group row">
            <label class="col-md-2 col-form-label form-control-label" v-text="$t('entities.notes')" for="file-note">Note</label>
            <div class="col-md-10">
              <input type="text" class="form-control" name="note" alternative id="file-note" data-cy="note" v-model="$v.file.note.$model" />
            </div>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entities.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.file.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entities.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./file-update.component.ts"></script>
