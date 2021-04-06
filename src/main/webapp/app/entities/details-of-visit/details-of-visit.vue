<template>
  <div>
    <h2 id="page-heading" data-cy="DetailsOfVisitHeading">
      <div class="d-flex justify-content-center">
        <button class="btn btn-info ml-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>تحديث الجدول</span>
        </button>
        <router-link :to="{ name: 'DetailsOfVisitCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-details-of-visit"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> إنشاء تفاصيل زيارة جديدة </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && detailsOfVisits && detailsOfVisits.length === 0">
      <span>لا يوجد تفاصيل زيارات</span>
    </div>
    <div class="table-responsive" v-if="detailsOfVisits && detailsOfVisits.length > 0">
      <el-table class="table-responsive table" header-row-class-name="thead-light" :data="detailsOfVisits">
        <el-table-column label="الكود" prop="id" min-width="140px"> </el-table-column>
        <el-table-column label="وصف الأمراض" prop="descriptionAilments"> </el-table-column>
        <el-table-column label="إسم المرض" prop="nameOfDisease"> </el-table-column>
        <el-table-column label="التوصيات" prop="recommendations"> </el-table-column>
        <el-table-column label="الأدوية" prop="medicines"> </el-table-column>
        <el-table-column label="الجرعة" prop="dosage"> </el-table-column>
        <el-table-column label="الزيارة" prop="visit.id">
          <template v-slot="{ row }">
            <div v-if="row.visit">
              <router-link :to="{ name: 'VisitView', params: { visitId: row.visit.id } }">{{ row.visit.id }}</router-link>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="إجراء" prop="id">
          <template v-slot="{ row }">
            <div class="btn-group">
              <router-link :to="{ name: 'DetailsOfVisitView', params: { detailsOfVisitId: row.id } }" custom v-slot="{ navigate }">
                <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline">مشاهدة</span>
                </button>
              </router-link>
              <router-link :to="{ name: 'DetailsOfVisitEdit', params: { detailsOfVisitId: row.id } }" custom v-slot="{ navigate }">
                <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline">تعديل</span>
                </button>
              </router-link>
              <b-button
                v-on:click="prepareRemove(row)"
                variant="danger"
                class="btn btn-sm"
                data-cy="entityDeleteButton"
                v-b-modal.removeEntity
              >
                <font-awesome-icon icon="times"></font-awesome-icon>
                <span class="d-none d-md-inline">حذف</span>
              </b-button>
            </div>
          </template>
        </el-table-column>
      </el-table>

      <!-- <table class="table table-striped" aria-describedby="detailsOfVisits">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('descriptionAilments')">
              <span>Description Ailments</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'descriptionAilments'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('nameOfDisease')">
              <span>Name Of Disease</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'nameOfDisease'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('recommendations')">
              <span>Recommendations</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'recommendations'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('medicines')">
              <span>Medicines</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'medicines'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('dosage')">
              <span>Dosage</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'dosage'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('visit.id')">
              <span>Visit</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'visit.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="detailsOfVisit in detailsOfVisits" :key="detailsOfVisit.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'DetailsOfVisitView', params: { detailsOfVisitId: detailsOfVisit.id } }">{{
                detailsOfVisit.id
              }}</router-link>
            </td>
            <td>{{ detailsOfVisit.descriptionAilments }}</td>
            <td>{{ detailsOfVisit.nameOfDisease }}</td>
            <td>{{ detailsOfVisit.recommendations }}</td>
            <td>{{ detailsOfVisit.medicines }}</td>
            <td>{{ detailsOfVisit.dosage }}</td>
            <td>
              <div v-if="detailsOfVisit.visit">
                <router-link :to="{ name: 'VisitView', params: { visitId: detailsOfVisit.visit.id } }">{{
                  detailsOfVisit.visit.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'DetailsOfVisitView', params: { detailsOfVisitId: detailsOfVisit.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'DetailsOfVisitEdit', params: { detailsOfVisitId: detailsOfVisit.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(detailsOfVisit)"
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
      </table>-->
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="smartclinicApp.detailsOfVisit.delete.question" data-cy="detailsOfVisitDeleteDialogHeading">تأكيد عملية الحذف</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-detailsOfVisit-heading">هل تريد حقا حذف هذه التفاصيل؟</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">إلغاء</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-detailsOfVisit"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeDetailsOfVisit()"
        >
          حذف
        </button>
      </div>
    </b-modal>
    <div v-show="detailsOfVisits && detailsOfVisits.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./details-of-visit.component.ts"></script>
