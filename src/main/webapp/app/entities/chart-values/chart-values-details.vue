<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="chartValues">
        <h2 class="jh-entity-heading" data-cy="chartValuesDetailsHeading"><span>ChartValues</span> {{ chartValues.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>age</span>
          </dt>
          <dd>
            <span>{{ chartValues.age }}</span>
          </dd>
          <dt>
            <span>length</span>
          </dt>
          <dd>
            <span>{{ chartValues.length }}</span>
          </dd>
          <dt>
            <span>Type</span>
          </dt>
          <dd>
            <span>{{ chartValues.type }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="chartValues.id"
          :to="{ name: 'ChartValuesEdit', params: { chartValuesId: chartValues.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./chart-values-details.component.ts"></script>
