<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="smartclinicApp.medicine.home.createOrEditLabel" data-cy="MedicineCreateUpdateHeading">Create or edit a Medicine</h2>
        <div>
          <div class="form-group" v-if="medicine.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="medicine.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="medicine-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="medicine-name"
              data-cy="name"
              :class="{ valid: !$v.medicine.name.$invalid, invalid: $v.medicine.name.$invalid }"
              v-model="$v.medicine.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="medicine-quantity">Quantity</label>
            <input
              type="text"
              class="form-control"
              name="quantity"
              id="medicine-quantity"
              data-cy="quantity"
              :class="{ valid: !$v.medicine.quantity.$invalid, invalid: $v.medicine.quantity.$invalid }"
              v-model="$v.medicine.quantity.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="medicine-notes">Notes</label>
            <input
              type="text"
              class="form-control"
              name="notes"
              id="medicine-notes"
              data-cy="notes"
              :class="{ valid: !$v.medicine.notes.$invalid, invalid: $v.medicine.notes.$invalid }"
              v-model="$v.medicine.notes.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="medicine-createdBy">Created By</label>
            <input
              type="text"
              class="form-control"
              name="createdBy"
              id="medicine-createdBy"
              data-cy="createdBy"
              :class="{ valid: !$v.medicine.createdBy.$invalid, invalid: $v.medicine.createdBy.$invalid }"
              v-model="$v.medicine.createdBy.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="medicine-updatedBy">Updated By</label>
            <input
              type="text"
              class="form-control"
              name="updatedBy"
              id="medicine-updatedBy"
              data-cy="updatedBy"
              :class="{ valid: !$v.medicine.updatedBy.$invalid, invalid: $v.medicine.updatedBy.$invalid }"
              v-model="$v.medicine.updatedBy.$model"
            />
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
            :disabled="$v.medicine.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./medicine-update.component.ts"></script>
