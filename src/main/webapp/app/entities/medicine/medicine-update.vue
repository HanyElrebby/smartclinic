<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="smartclinicApp.medicine.home.createOrEditLabel" data-cy="MedicineCreateUpdateHeading">Create or edit a Medicine</h2>
        <hr />
        <div>
          <div class="form-group row" v-if="medicine.id">
            <label class="col-md-2 col-form-label form-control-label" for="id">ID</label>
            <div class="col-md-10">
              <base-input type="text" class="form-control" id="id" name="id" v-model="medicine.id" readonly />
            </div>
          </div>
          <div class="form-group row">
            <label class="col-md-2 col-form-label form-control-label" for="medicine-name">Name</label>
            <div class="col-md-10">
              <base-input
                type="text"
                name="name"
                id="medicine-name"
                data-cy="name"
                alternative
                v-model="$v.medicine.name.$model"
                :rules="{ required: true }"
              />
            </div>
          </div>
          <div class="form-group row">
            <label class="col-md-2 col-form-label form-control-label" for="medicine-quantity">Quantity</label>
            <div class="col-md-10">
              <base-input
                type="text"
                name="quantity"
                id="medicine-quantity"
                data-cy="quantity"
                alternative
                v-model="$v.medicine.quantity.$model"
                :rules="{ required: true }"
              />
            </div>
          </div>
          <div class="form-group row">
            <label class="col-md-2 col-form-label form-control-label" for="medicine-notes">Notes</label>
            <div class="col-md-10">
              <base-input type="text" name="notes" id="medicine-notes" data-cy="notes" v-model="$v.medicine.notes.$model" />
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
