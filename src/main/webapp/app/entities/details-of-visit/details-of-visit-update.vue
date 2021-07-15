<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          v-text="$t('entities.createOrEditVisitDetails')"
          id="smartclinicApp.detailsOfVisit.home.createOrEditLabel"
          data-cy="DetailsOfVisitCreateUpdateHeading"
        >
          إنشاء او تعديل تفاصيل الزيارة
        </h2>
        <hr />
        <div>
          <div class="form-group row" v-if="detailsOfVisit.id">
            <label for="example-email-input" v-text="$t('entities.id')" class="col-md-2 col-form-label form-control-label">الكود</label>
            <div class="col-md-10">
              <base-input type="text" name="id" v-model="detailsOfVisit.id" readonly />
            </div>
          </div>
          <div class="form-group row">
            <label
              for="example-email-input"
              v-text="$t('entities.DescriptionOfDiseases')"
              class="col-md-2 col-form-label form-control-label"
              >وصف الأمراض</label
            >
            <div class="col-md-10">
              <base-input
                type="text"
                :name="translate('entities.DescriptionOfDiseases')"
                data-cy="descriptionAilments"
                alternative
                v-model="$v.detailsOfVisit.descriptionAilments.$model"
                :rules="{ required: true, max: 50 }"
              />
            </div>
          </div>
          <div class="form-group row">
            <label for="example-email-input" v-text="$t('entities.nameOfDesease')" class="col-md-2 col-form-label form-control-label"
              >إسم المرض</label
            >
            <div class="col-md-10">
              <base-input
                type="text"
                :name="translate('entities.nameOfDesease')"
                data-cy="nameOfDisease"
                alternative
                v-model="$v.detailsOfVisit.nameOfDisease.$model"
                :rules="{ required: true, max: 30 }"
              />
            </div>
          </div>
          <div class="form-group row">
            <label for="example-email-input" v-text="$t('entities.recommendatios')" class="col-md-2 col-form-label form-control-label"
              >التوصيات</label
            >
            <div class="col-md-10">
              <base-input
                type="text"
                :name="translate('entities.recommendatios')"
                alternative
                data-cy="recommendations"
                v-model="$v.detailsOfVisit.recommendations.$model"
                :rules="{ max: 30 }"
              />
            </div>
          </div>
          <div class="form-group row">
            <label for="example-email-input" v-text="$t('entities.medecines')" class="col-md-2 col-form-label form-control-label"
              >الأدوية</label
            >
            <div class="col-md-10">
              <base-input
                type="text"
                :name="translate('entities.medecines')"
                alternative
                data-cy="medicines"
                v-model="$v.detailsOfVisit.medicines.$model"
                :rules="{ max: 30 }"
              />
            </div>
          </div>
          <div class="form-group row">
            <label for="example-email-input" v-text="$t('entities.dosage')" class="col-md-2 col-form-label form-control-label"
              >الجرعة</label
            >
            <div class="col-md-10">
              <base-input
                type="text"
                :name="translate('entities.dosage')"
                alternative
                data-cy="dosage"
                v-model="$v.detailsOfVisit.dosage.$model"
                :rules="{ max: 30 }"
              />
            </div>
          </div>
          <div class="form-group row">
            <label for="example-email-input" v-text="$t('entities.visit')" class="col-md-2 col-form-label form-control-label"
              >الزيارة</label
            >
            <div class="col-md-10">
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
        </div>
        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entities.cancel')">إلغاء</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.detailsOfVisit.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entities.save')">حفظ</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./details-of-visit-update.component.ts"></script>
