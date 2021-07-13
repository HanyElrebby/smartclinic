<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="smartclinicApp.safe.home.createOrEditLabel" data-cy="SafeCreateUpdateHeading">Create or edit a Safe</h2>
        <div>
          <div class="form-group" v-if="safe.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="safe.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="safe-safeName">Safe Name</label>
            <input
              type="text"
              class="form-control"
              name="safeName"
              id="safe-safeName"
              data-cy="safeName"
              :class="{ valid: !$v.safe.safeName.$invalid, invalid: $v.safe.safeName.$invalid }"
              v-model="$v.safe.safeName.$model"
              required
            />
            <div v-if="$v.safe.safeName.$anyDirty && $v.safe.safeName.$invalid">
              <small class="form-text text-danger" v-if="!$v.safe.safeName.required"> This field is required. </small>
            </div>
          </div>

          <!-- <div class="form-group">
            <label class="form-control-label" for="safe-safeSecretary">Safe Secretary</label>
            <input
              type="text"
              class="form-control"
              name="safeSecretary"
              id="safe-safeSecretary"
              data-cy="safeSecretary"
              :class="{ valid: !$v.safe.safeSecretary.$invalid, invalid: $v.safe.safeSecretary.$invalid }"
              v-model="$v.safe.safeSecretary.$model"
              required
            />
            <div v-if="$v.safe.safeSecretary.$anyDirty && $v.safe.safeSecretary.$invalid">
              <small class="form-text text-danger" v-if="!$v.safe.safeSecretary.required"> This field is required. </small>
            </div>
          </div>-->

          <div class="form-group">
            <label class="form-control-label" for="safe-safeValue">Safe Value</label>
            <input
              type="number"
              class="form-control"
              name="safeValue"
              id="safe-safeValue"
              data-cy="safeValue"
              :class="{ valid: !$v.safe.safeValue.$invalid, invalid: $v.safe.safeValue.$invalid }"
              v-model.number="$v.safe.safeValue.$model"
              required
            />
            <div v-if="$v.safe.safeValue.$anyDirty && $v.safe.safeValue.$invalid">
              <small class="form-text text-danger" v-if="!$v.safe.safeValue.required"> This field is required. </small>
              <small class="form-text text-danger" v-if="!$v.safe.safeValue.min"> This field should be at least 0. </small>
              <small class="form-text text-danger" v-if="!$v.safe.safeValue.numeric"> This field should be a number. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="safe-notes">Notes</label>
            <input
              type="text"
              class="form-control"
              name="notes"
              id="safe-notes"
              data-cy="notes"
              :class="{ valid: !$v.safe.notes.$invalid, invalid: $v.safe.notes.$invalid }"
              v-model="$v.safe.notes.$model"
              required
            />
            <div v-if="$v.safe.notes.$anyDirty && $v.safe.notes.$invalid">
              <small class="form-text text-danger" v-if="!$v.safe.notes.required"> This field is required. </small>
            </div>
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
            :disabled="$v.safe.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./safe-update.component.ts"></script>
