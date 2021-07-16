<template>
  <div>
    <h2 id="page-heading" data-cy="ExpenseHeading">
      <span id="expense-heading">Expenses</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('entities.refreshTable')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ExpenseCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-expense"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Expense </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && expenses && expenses.length === 0">
      <span>No expenses found</span>
    </div>
    <div class="table-responsive" v-if="expenses && expenses.length > 0">
      <table class="table table-striped" aria-describedby="expenses">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('expenseType')">
              <span>Expense Type</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'expenseType'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('price')">
              <span>Price</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'price'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('statement')">
              <span>Statement</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'statement'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('detailedStatement')">
              <span>Detailed Statement</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'detailedStatement'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('expenseDate')">
              <span>Expense Date</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'expenseDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('notes')">
              <span>Notes</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'notes'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('safe.safeName')">
              <span>Safe</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'safe.safeName'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="expense in expenses" :key="expense.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ExpenseView', params: { expenseId: expense.id } }">{{ expense.id }}</router-link>
            </td>
            <td>{{ expense.expenseType }}</td>
            <td>{{ expense.price }}</td>
            <td>{{ expense.statement }}</td>
            <td>{{ expense.detailedStatement }}</td>
            <td>{{ expense.expenseDate }}</td>
            <td>{{ expense.notes }}</td>
            <td>
              <div v-if="expense.safe">
                <router-link :to="{ name: 'SafeView', params: { safeId: expense.safe.id } }">{{ expense.safe.safeName }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ExpenseView', params: { expenseId: expense.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ExpenseEdit', params: { expenseId: expense.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(expense)"
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
        ><span id="smartclinicApp.expense.delete.question" data-cy="expenseDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-expense-heading">Are you sure you want to delete this Expense?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-expense"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeExpense()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="expenses && expenses.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./expense.component.ts"></script>
