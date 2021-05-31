<template>
  <div>
    <h2 id="page-heading" data-cy="ChartValuesHeading">
      <span id="chart-values-heading">Chart Values</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'ChartValuesCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-chart-values"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Chart Values </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && chartValues && chartValues.length === 0">
      <span>No chartValues found</span>
    </div>
    <div class="table-responsive" v-if="chartValues && chartValues.length > 0">
      <table class="table table-striped" aria-describedby="chartValues">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('xValue')">
              <span>X Value</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'xValue'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('yValue')">
              <span>Y Value</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'yValue'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('type')">
              <span>Type</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'type'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="chartValues in chartValues" :key="chartValues.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ChartValuesView', params: { chartValuesId: chartValues.id } }">{{ chartValues.id }}</router-link>
            </td>
            <td>{{ chartValues.xValue }}</td>
            <td>{{ chartValues.yValue }}</td>
            <td>{{ chartValues.type }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ChartValuesView', params: { chartValuesId: chartValues.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ChartValuesEdit', params: { chartValuesId: chartValues.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(chartValues)"
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
        ><span id="smartclinicApp.chartValues.delete.question" data-cy="chartValuesDeleteDialogHeading"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-chartValues-heading">Are you sure you want to delete this Chart Values?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-chartValues"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeChartValues()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="chartValues && chartValues.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./chart-values.component.ts"></script>
