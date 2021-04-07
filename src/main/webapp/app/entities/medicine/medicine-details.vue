<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="medicine">
        <h2 class="jh-entity-heading" data-cy="medicineDetailsHeading"><span>Medicine</span> {{ medicine.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Name</span>
          </dt>
          <dd>
            <span>{{ medicine.name }}</span>
          </dd>
          <dt>
            <span>Quantity</span>
          </dt>
          <dd>
            <span>{{ medicine.quantity }}</span>
          </dd>
          <dt>
            <span>Notes</span>
          </dt>
          <dd>
            <span>{{ medicine.notes }}</span>
          </dd>
          <dt>
            <span>Created By</span>
          </dt>
          <dd>
            <span>{{ medicine.createdBy }}</span>
          </dd>
          <dt>
            <span>Updated By</span>
          </dt>
          <dd>
            <span>{{ medicine.updatedBy }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="medicine.id" :to="{ name: 'MedicineEdit', params: { medicineId: medicine.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./medicine-details.component.ts"></script>
