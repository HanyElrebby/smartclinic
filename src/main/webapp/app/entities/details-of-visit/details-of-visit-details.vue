<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="detailsOfVisit">
        <h2 class="jh-entity-heading" data-cy="detailsOfVisitDetailsHeading"><span>تفاصيل الزيارات</span> {{ detailsOfVisit.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>وصف الأمراض</span>
          </dt>
          <dd>
            <span>{{ detailsOfVisit.descriptionAilments }}</span>
          </dd>
          <dt>
            <span>إسم المرض</span>
          </dt>
          <dd>
            <span>{{ detailsOfVisit.nameOfDisease }}</span>
          </dd>
          <dt>
            <span>التوصيات</span>
          </dt>
          <dd>
            <span>{{ detailsOfVisit.recommendations }}</span>
          </dd>
          <dt>
            <span>الأدوية</span>
          </dt>
          <dd>
            <span>{{ detailsOfVisit.medicines }}</span>
          </dd>
          <dt>
            <span>الجرعة</span>
          </dt>
          <dd>
            <span>{{ detailsOfVisit.dosage }}</span>
          </dd>
          <dt>
            <span>الزيارة</span>
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
          <font-awesome-icon icon="arrow-right"></font-awesome-icon>&nbsp;<span> رجوع</span>
        </button>
        <router-link
          v-if="detailsOfVisit.id"
          :to="{ name: 'DetailsOfVisitEdit', params: { detailsOfVisitId: detailsOfVisit.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> تعديل</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./details-of-visit-details.component.ts"></script>
