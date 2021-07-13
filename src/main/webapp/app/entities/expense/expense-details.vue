<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="expense">
        <h2 class="jh-entity-heading" data-cy="expenseDetailsHeading"><span>Expense</span> {{ expense.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Expense Type</span>
          </dt>
          <dd>
            <span>{{ expense.expenseType }}</span>
          </dd>
          <dt>
            <span>Price</span>
          </dt>
          <dd>
            <span>{{ expense.price }}</span>
          </dd>
          <dt>
            <span>Statement</span>
          </dt>
          <dd>
            <span>{{ expense.statement }}</span>
          </dd>
          <dt>
            <span>Detailed Statement</span>
          </dt>
          <dd>
            <span>{{ expense.detailedStatement }}</span>
          </dd>
          <dt>
            <span>Expense Date</span>
          </dt>
          <dd>
            <span>{{ expense.expenseDate }}</span>
          </dd>
          <dt>
            <span>Notes</span>
          </dt>
          <dd>
            <span>{{ expense.notes }}</span>
          </dd>
          <dt>
            <span>Safe</span>
          </dt>
          <dd>
            <div v-if="expense.safe">
              <router-link :to="{ name: 'SafeView', params: { safeId: expense.safe.id } }">{{ expense.safe.safeName }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="expense.id" :to="{ name: 'ExpenseEdit', params: { expenseId: expense.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./expense-details.component.ts"></script>
