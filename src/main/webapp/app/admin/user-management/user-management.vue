<template>
  <div>
    <h2>
      <div class="d-flex justify-content-center">
        <button class="btn btn-info ml-2" v-on:click="handleSyncList" :disabled="isLoading">
          <font-awesome-icon icon="sync" :spin="isLoading"></font-awesome-icon> <span>تحديث الجدول</span>
        </button>
        <router-link custom v-slot="{ navigate }" :to="{ name: 'JhiUserCreate' }">
          <button @click="navigate" class="btn btn-primary jh-create-entity">
            <font-awesome-icon icon="plus"></font-awesome-icon> <span>إنشاء مستخدم جديد</span>
          </button>
        </router-link>
      </div>
    </h2>
    <div class="table-responsive" v-if="users">
      <el-table class="table-responsive table" header-row-class-name="thead-light" :data="users">
        <el-table-column label="الكود" prop="id" min-width="140px"> </el-table-column>
        <el-table-column label="إسم المستخدم" prop="login"> </el-table-column>
        <el-table-column label="البريد الإلكترونى" prop="email"> </el-table-column>
        <el-table-column label="الحالة" prop="activated">
          <template v-slot="{ row }">
            <button class="btn btn-danger btn-sm deactivated" v-on:click="setActive(row, true)" v-if="!row.activated">غير نشط</button>
            <button
              class="btn btn-success btn-sm"
              v-on:click="setActive(row, false)"
              v-if="row.activated"
              :disabled="username === row.login"
            >
              نشط
            </button>
          </template>
        </el-table-column>
        <el-table-column label="الأذونات" prop="authorities">
          <template v-slot="{ row }">
            <div v-for="authority of row.authorities" :key="authority">
              <span class="badge badge-info">{{ authority }}</span>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="تاريخ الأنشاء" prop="createdDate"> </el-table-column>
        <el-table-column label="تاريخ أخر تعديل" prop="lastModifiedDate"> </el-table-column>
        <el-table-column label="أخر تعديل بواسطة" prop="lastModifiedBy"> </el-table-column>

        <el-table-column label="إجراء" prop="id">
          <template v-slot="{ row }">
            <div class="btn-group">
              <router-link :to="{ name: 'JhiUserView', params: { userId: row.login } }" custom v-slot="{ navigate }">
                <button @click="navigate" class="btn btn-info btn-sm details">
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline">مشاهدة</span>
                </button>
              </router-link>
              <router-link :to="{ name: 'JhiUserEdit', params: { userId: row.login } }" custom v-slot="{ navigate }">
                <button @click="navigate" class="btn btn-primary btn-sm edit">
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline">تعديل</span>
                </button>
              </router-link>
              <b-button v-on:click="prepareRemove(row)" variant="danger" class="btn btn-sm delete" :disabled="username === row.login">
                <font-awesome-icon icon="times"></font-awesome-icon>
                <span class="d-none d-md-inline">حذف</span>
              </b-button>
            </div>
          </template>
        </el-table-column>
      </el-table>

      <!--<table class="table table-striped" aria-describedby="Users">
        <thead>
          <tr>
            <th scope="col" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="col" v-on:click="changeOrder('login')">
              <span>Login</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'login'"></jhi-sort-indicator>
            </th>
            <th scope="col" v-on:click="changeOrder('email')">
              <span>Email</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'email'"></jhi-sort-indicator>
            </th>
            <th scope="col"></th>
            <th scope="col"><span>Profiles</span></th>
            <th scope="col" v-on:click="changeOrder('createdDate')">
              <span>Created Date</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdDate'"></jhi-sort-indicator>
            </th>
            <th scope="col" v-on:click="changeOrder('lastModifiedBy')">
              <span>Last Modified By</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lastModifiedBy'"></jhi-sort-indicator>
            </th>
            <th scope="col" id="modified-date-sort" v-on:click="changeOrder('lastModifiedDate')">
              <span>Last Modified Date</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lastModifiedDate'"></jhi-sort-indicator>
            </th>
            <th scope="col"></th>
          </tr>
        </thead>
        <tbody v-if="users">
          <tr v-for="user in users" :key="user.id" :id="user.login">
            <td>
              <router-link :to="{ name: 'JhiUserView', params: { userId: user.login } }">{{ user.id }}</router-link>
            </td>
            <td>{{ user.login }}</td>
            <td class="jhi-user-email">{{ user.email }}</td>
            <td>
              <button class="btn btn-danger btn-sm deactivated" v-on:click="setActive(user, true)" v-if="!user.activated">
                Deactivated
              </button>
              <button
                class="btn btn-success btn-sm"
                v-on:click="setActive(user, false)"
                v-if="user.activated"
                :disabled="username === user.login"
              >
                Activated
              </button>
            </td>

            <td>
              <div v-for="authority of user.authorities" :key="authority">
                <span class="badge badge-info">{{ authority }}</span>
              </div>
            </td>
            <td>{{ user.createdDate | formatDate }}</td>
            <td>{{ user.lastModifiedBy }}</td>
            <td>{{ user.lastModifiedDate | formatDate }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'JhiUserView', params: { userId: user.login } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'JhiUserEdit', params: { userId: user.login } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button v-on:click="prepareRemove(user)" variant="danger" class="btn btn-sm delete" :disabled="username === user.login">
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>-->
      <b-modal ref="removeUser" id="removeUser" @ok="deleteUser()">
        <div class="modal-body">
          <p id="jhi-delete-user-heading">هل تريد حقا حذف هذا المستخدم؟</p>
        </div>
        <div slot="modal-footer">
          <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">إلغاء</button>
          <button type="button" class="btn btn-primary" id="confirm-delete-user" v-on:click="deleteUser()">حذف</button>
        </div>
      </b-modal>
    </div>
    <div v-show="users && users.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./user-management.component.ts"></script>
