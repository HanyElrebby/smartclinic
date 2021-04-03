<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="smartclinicApp.detailsOfVisit.home.createOrEditLabel" data-cy="DetailsOfVisitCreateUpdateHeading">
          إنشاء او تعديل تفاصيل الزيارة
        </h2>
        <div>
          <div v-if="detailsOfVisit.id">
            <base-input type="text" label="الكود" name="id" v-model="detailsOfVisit.id" readonly />
          </div>
          <div>
            <base-input
              type="text"
              name="وصف الأمراض"
              data-cy="descriptionAilments"
              label="وصف الأمراض"
              placeholder="وصف الأمراض"
              alternative
              v-model="$v.detailsOfVisit.descriptionAilments.$model"
              :rules="{ required: true, max: 50 }"
            />
          </div>
          <div>
            <base-input
              type="text"
              name="إسم المرض"
              data-cy="nameOfDisease"
              label="إسم المرض"
              placeholder="إسم المرض"
              alternative
              v-model="$v.detailsOfVisit.nameOfDisease.$model"
              :rules="{ required: true, max: 30 }"
            />
          </div>
          <div>
            <base-input
              type="text"
              name="التوصيات"
              label="التوصيات"
              placeholder="التوصيات"
              alternative
              data-cy="recommendations"
              v-model="$v.detailsOfVisit.recommendations.$model"
              :rules="{ max: 30 }"
            />
          </div>
          <div>
            <base-input
              type="text"
              name="الأدوية"
              label="الأدوية"
              placeholder="الأدوية"
              alternative
              data-cy="medicines"
              v-model="$v.detailsOfVisit.medicines.$model"
              :rules="{ max: 30 }"
            />
          </div>
          <div>
            <base-input
              type="text"
              name="الجرعة"
              label="الجرعة"
              placeholder="الجرعة"
              alternative
              data-cy="dosage"
              v-model="$v.detailsOfVisit.dosage.$model"
              :rules="{ max: 30 }"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="details-of-visit-visit">الزيارة</label>
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
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>إلغاء</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.detailsOfVisit.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>حفظ</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./details-of-visit-update.component.ts"></script>
