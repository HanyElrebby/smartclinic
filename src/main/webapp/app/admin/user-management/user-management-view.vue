<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="user">
        <h2 class="jh-entity-heading">
          <span>مستخدم</span> [<strong>{{ user.login }}</strong
          >]
        </h2>
        <dl class="row jh-entity-details">
          <dt><span>اسم المستخدم</span></dt>
          <dd>
            <span>{{ user.login }}</span>
            <b-badge variant="success" v-if="user.activated">نشط</b-badge>
            <b-badge variant="danger" v-if="!user.activated">غير نشط</b-badge>
          </dd>
          <dt><span>الإسم الأول</span></dt>
          <dd>{{ user.firstName }}</dd>
          <dt><span>الإسم الأخير</span></dt>
          <dd>{{ user.lastName }}</dd>
          <dt><span>البريد الإلكترونى</span></dt>
          <dd>{{ user.email }}</dd>
          <dt><span>أنشأ بواسطة</span></dt>
          <dd>{{ user.createdBy }}</dd>
          <dt><span>تاريخ الإنشاء</span></dt>
          <dd>{{ user.createdDate | formatDate }}</dd>
          <dt><span>أخر تعديل بواسطة</span></dt>
          <dd>{{ user.lastModifiedBy }}</dd>
          <dt><span>تاريخ أخر تعديل</span></dt>
          <dd>{{ user.lastModifiedDate | formatDate }}</dd>
          <dt><span>الأذونات</span></dt>
          <dd>
            <ul class="list-unstyled">
              <li v-for="authority of user.authorities" :key="authority">
                <b-badge variant="info">{{ authority }}</b-badge>
              </li>
            </ul>
          </dd>
        </dl>
        <router-link custom v-slot="{ navigate }" :to="{ name: 'JhiUser' }">
          <button @click="navigate" class="btn btn-info">
            <font-awesome-icon icon="arrow-right"></font-awesome-icon>&nbsp;<span> رجوع</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./user-management-view.component.ts"></script>
