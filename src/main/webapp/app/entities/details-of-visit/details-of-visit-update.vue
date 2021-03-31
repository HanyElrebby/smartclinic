<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="smartclinicApp.detailsOfVisit.home.createOrEditLabel" data-cy="DetailsOfVisitCreateUpdateHeading">
          Create or edit a DetailsOfVisit
        </h2>
        <div>
          <div class="form-group" v-if="detailsOfVisit.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="detailsOfVisit.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="details-of-visit-descriptionAilments">Description Ailments</label>
            <input
              type="text"
              class="form-control"
              name="descriptionAilments"
              id="details-of-visit-descriptionAilments"
              data-cy="descriptionAilments"
              :class="{ valid: !$v.detailsOfVisit.descriptionAilments.$invalid, invalid: $v.detailsOfVisit.descriptionAilments.$invalid }"
              v-model="$v.detailsOfVisit.descriptionAilments.$model"
              required
            />
            <div v-if="$v.detailsOfVisit.descriptionAilments.$anyDirty && $v.detailsOfVisit.descriptionAilments.$invalid">
              <small class="form-text text-danger" v-if="!$v.detailsOfVisit.descriptionAilments.required"> This field is required. </small>
              <small class="form-text text-danger" v-if="!$v.detailsOfVisit.descriptionAilments.maxLength">
                This field cannot be longer than 50 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="details-of-visit-nameOfDisease">Name Of Disease</label>
            <input
              type="text"
              class="form-control"
              name="nameOfDisease"
              id="details-of-visit-nameOfDisease"
              data-cy="nameOfDisease"
              :class="{ valid: !$v.detailsOfVisit.nameOfDisease.$invalid, invalid: $v.detailsOfVisit.nameOfDisease.$invalid }"
              v-model="$v.detailsOfVisit.nameOfDisease.$model"
              required
            />
            <div v-if="$v.detailsOfVisit.nameOfDisease.$anyDirty && $v.detailsOfVisit.nameOfDisease.$invalid">
              <small class="form-text text-danger" v-if="!$v.detailsOfVisit.nameOfDisease.required"> This field is required. </small>
              <small class="form-text text-danger" v-if="!$v.detailsOfVisit.nameOfDisease.maxLength">
                This field cannot be longer than 30 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="details-of-visit-recommendations">Recommendations</label>
            <input
              type="text"
              class="form-control"
              name="recommendations"
              id="details-of-visit-recommendations"
              data-cy="recommendations"
              :class="{ valid: !$v.detailsOfVisit.recommendations.$invalid, invalid: $v.detailsOfVisit.recommendations.$invalid }"
              v-model="$v.detailsOfVisit.recommendations.$model"
            />
            <div v-if="$v.detailsOfVisit.recommendations.$anyDirty && $v.detailsOfVisit.recommendations.$invalid">
              <small class="form-text text-danger" v-if="!$v.detailsOfVisit.recommendations.maxLength">
                This field cannot be longer than 30 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="details-of-visit-medicines">Medicines</label>
            <input
              type="text"
              class="form-control"
              name="medicines"
              id="details-of-visit-medicines"
              data-cy="medicines"
              :class="{ valid: !$v.detailsOfVisit.medicines.$invalid, invalid: $v.detailsOfVisit.medicines.$invalid }"
              v-model="$v.detailsOfVisit.medicines.$model"
            />
            <div v-if="$v.detailsOfVisit.medicines.$anyDirty && $v.detailsOfVisit.medicines.$invalid">
              <small class="form-text text-danger" v-if="!$v.detailsOfVisit.medicines.maxLength">
                This field cannot be longer than 30 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="details-of-visit-dosage">Dosage</label>
            <input
              type="text"
              class="form-control"
              name="dosage"
              id="details-of-visit-dosage"
              data-cy="dosage"
              :class="{ valid: !$v.detailsOfVisit.dosage.$invalid, invalid: $v.detailsOfVisit.dosage.$invalid }"
              v-model="$v.detailsOfVisit.dosage.$model"
            />
            <div v-if="$v.detailsOfVisit.dosage.$anyDirty && $v.detailsOfVisit.dosage.$invalid">
              <small class="form-text text-danger" v-if="!$v.detailsOfVisit.dosage.maxLength">
                This field cannot be longer than 30 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="details-of-visit-visit">Visit</label>
            <select class="form-control" id="details-of-visit-visit" data-cy="visit" name="visit" v-model="detailsOfVisit.visit">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="detailsOfVisit.visit && visitOption.id === detailsOfVisit.visit.id ? detailsOfVisit.visit : visitOption"
                v-for="visitOption in visits"
                :key="visitOption.id"
              >
                {{ visitOption.id }}
              </option>
            </select>
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
            :disabled="$v.detailsOfVisit.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./details-of-visit-update.component.ts"></script>
