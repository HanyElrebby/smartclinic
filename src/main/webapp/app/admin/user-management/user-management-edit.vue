<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" v-if="userAccount">
        <h2 id="myUserLabel">إنشاء او تعديل مستخدم</h2>
        <div>
          <div :hidden="!userAccount.id">
            <base-input type="text" label="الكود" name="id" v-model="userAccount.id" readonly />
          </div>

          <div>
            <base-input
              type="text"
              name="إسم المستخدم"
              label="إسم المستخدم"
              placeholder="إسم المستخدم"
              alternative
              v-model="$v.userAccount.login.$model"
              :rules="{ required: true, max: 50, min: 1, pattern: '^[a-zA-Z0-9!#$&\'*+=?^_`{|}~.-]+@?[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$' }"
            />
          </div>
          <div>
            <base-input
              type="text"
              name="الإسم الأول"
              data-cy="firstName"
              label="الإسم الأول"
              placeholder="الإسم الأول"
              alternative
              v-model="$v.userAccount.firstName.$model"
              :rules="{ max: 30 }"
            />
          </div>
          <div>
            <base-input
              type="text"
              name="الإسم الأخير"
              label="الإسم الأخير"
              placeholder="الإسم الأخير"
              alternative
              v-model="$v.userAccount.lastName.$model"
              :rules="{ max: 30 }"
            />
          </div>
          <div>
            <base-input
              type="email"
              id="email"
              name="البريد الألكترونى"
              label="البريد الألكترونى"
              placeholder="البريد الألكترونى"
              alternative
              v-model="$v.userAccount.email.$model"
              :rules="{ required: true, email: true, min: 5, max: 200 }"
            />
          </div>
          <div class="form-check">
            <label class="form-check-label" for="activated">
              <base-checkbox
                :disabled="userAccount.id === null"
                type="checkbox"
                id="activated"
                name="نشط"
                v-model="userAccount.activated"
                :checked="userAccount.activated"
              />
              <span>نشط</span>
            </label>
          </div>

          <div class="form-group">
            <label>الأذونات</label>
            <select class="form-control" multiple name="authority" v-model="userAccount.authorities">
              <option v-for="authority of authorities" :value="authority" :key="authority">{{ authority }}</option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>إلغاء</span>
          </button>
          <button type="submit" :disabled="$v.userAccount.$invalid || isSaving" class="btn btn-primary">
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>حفظ</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script lang="ts" src="./user-management-edit.component.ts"></script>
