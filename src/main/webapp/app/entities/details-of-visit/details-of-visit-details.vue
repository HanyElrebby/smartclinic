<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="detailsOfVisit">
        <h2 class="jh-entity-heading" data-cy="detailsOfVisitDetailsHeading"><span>DetailsOfVisit</span> {{ detailsOfVisit.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Description Ailments</span>
          </dt>
          <dd>
            <span>{{ detailsOfVisit.descriptionAilments }}</span>
          </dd>
          <dt>
            <span>Name Of Disease</span>
          </dt>
          <dd>
            <span>{{ detailsOfVisit.nameOfDisease }}</span>
          </dd>
          <dt>
            <span>Recommendations</span>
          </dt>
          <dd>
            <span>{{ detailsOfVisit.recommendations }}</span>
          </dd>
          <dt>
            <span>Medicines</span>
          </dt>
          <dd>
            <span>{{ detailsOfVisit.medicines }}</span>
          </dd>
          <dt>
            <span>Dosage</span>
          </dt>
          <dd>
            <span>{{ detailsOfVisit.dosage }}</span>
          </dd>
          <dt>
            <span>Visit</span>
          </dt>
          <dd>
            <div v-if="detailsOfVisit.visit">
              <router-link :to="{ name: 'VisitView', params: { visitId: detailsOfVisit.visit.id } }">{{
                detailsOfVisit.visit.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="detailsOfVisit.id"
          :to="{ name: 'DetailsOfVisitEdit', params: { detailsOfVisitId: detailsOfVisit.id } }"
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

<script lang="ts" src="./details-of-visit-details.component.ts"></script>
