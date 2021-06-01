<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="smartclinicApp.chartValues.home.createOrEditLabel" data-cy="ChartValuesCreateUpdateHeading">
          Create or edit a ChartValues
        </h2>
        <div>
          <div class="form-group" v-if="chartValues.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="chartValues.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="chart-values-xValue">X Value</label>
            <input
              type="number"
              class="form-control"
              name="xValue"
              id="chart-values-xValue"
              data-cy="age"
              :class="{ valid: !$v.chartValues.age.$invalid, invalid: $v.chartValues.age.$invalid }"
              v-model.number="$v.chartValues.age.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="chart-values-yValue">Y Value</label>
            <input
              type="number"
              class="form-control"
              name="yValue"
              id="chart-values-yValue"
              data-cy="length"
              :class="{ valid: !$v.chartValues.length.$invalid, invalid: $v.chartValues.length.$invalid }"
              v-model.number="$v.chartValues.length.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="chart-values-type">Type</label>
            <input
              type="text"
              class="form-control"
              name="type"
              id="chart-values-type"
              data-cy="type"
              :class="{ valid: !$v.chartValues.type.$invalid, invalid: $v.chartValues.type.$invalid }"
              v-model="$v.chartValues.type.$model"
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
            :disabled="$v.chartValues.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./chart-values-update.component.ts"></script>
