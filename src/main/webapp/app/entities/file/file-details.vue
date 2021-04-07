<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="file">
        <h2 class="jh-entity-heading" data-cy="fileDetailsHeading"><span>File</span> {{ file.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>File Name</span>
          </dt>
          <dd>
            <span>{{ file.fileName }}</span>
          </dd>
          <dt>
            <span>File</span>
          </dt>
          <dd>
            <div v-if="file.file">
              <a v-on:click="openFile(file.fileContentType, file.file)">open</a>
              {{ file.fileContentType }}, {{ byteSize(file.file) }}
            </div>
          </dd>
          <dt>
            <span>Date Upload</span>
          </dt>
          <dd>
            <span>{{ file.dateUpload | formatDate }}</span>
          </dd>
          <dt>
            <span>Note</span>
          </dt>
          <dd>
            <span>{{ file.note }}</span>
          </dd>
          <dt>
            <span>Created By</span>
          </dt>
          <dd>
            <span>{{ file.createdBy }}</span>
          </dd>
          <dt>
            <span>Updated By</span>
          </dt>
          <dd>
            <span>{{ file.updatedBy }}</span>
          </dd>
          <dt>
            <span>Patient</span>
          </dt>
          <dd>
            <div v-if="file.patient">
              <router-link :to="{ name: 'PatientView', params: { patientId: file.patient.id } }">{{ file.patient.name }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="file.id" :to="{ name: 'FileEdit', params: { fileId: file.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./file-details.component.ts"></script>
