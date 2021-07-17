<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" v-if="userAccount">
        <h2 id="myUserLabel">إنشاء او تعديل مستخدم</h2>
        <hr />
        <div>
          <div class="form-group row" :hidden="!userAccount.id">
            <label for="example-email-input" class="col-md-2 col-form-label form-control-label">الكود</label>
            <div class="col-md-10">
              <base-input type="text" name="id" v-model="userAccount.id" readonly />
            </div>
          </div>

          <div class="form-group row">
            <label for="example-email-input" class="col-md-2 col-form-label form-control-label">إسم المستخدم</label>
            <div class="col-md-10">
              <base-input
                type="text"
                name="إسم المستخدم"
                alternative
                v-model="$v.userAccount.login.$model"
                :rules="{
                  required: true,
                  max: 50,
                  min: 1,
                }"
              />
            </div>
            <div v-if="$v.userAccount.login.$anyDirty && $v.userAccount.login.$invalid">
              <small class="form-text text-danger" v-if="!$v.userAccount.login.pattern"> invalid user name </small>
            </div>
          </div>
          <div class="form-group row">
            <label for="example-email-input" class="col-md-2 col-form-label form-control-label">الإسم الأول</label>
            <div class="col-md-10">
              <base-input
                type="text"
                name="الإسم الأول"
                data-cy="firstName"
                alternative
                v-model="$v.userAccount.firstName.$model"
                :rules="{ max: 30 }"
              />
            </div>
          </div>
          <div class="form-group row">
            <label for="example-email-input" class="col-md-2 col-form-label form-control-label">الإسم الأخير</label>
            <div class="col-md-10">
              <base-input type="text" name="الإسم الأخير" alternative v-model="$v.userAccount.lastName.$model" :rules="{ max: 30 }" />
            </div>
          </div>
          <div class="form-group row">
            <label for="example-email-input" class="col-md-2 col-form-label form-control-label">البريد الألكترونى</label>
            <div class="col-md-10">
              <base-input
                type="email"
                id="email"
                name="البريد الألكترونى"
                alternative
                v-model="$v.userAccount.email.$model"
                :rules="{ required: true, email: true, min: 5, max: 200 }"
              />
            </div>
          </div>
          <div class="form-group row" v-if="!userAccount.id">
            <label for="example-email-input" class="col-md-2 col-form-label form-control-label">كلمة المرور</label>
            <div class="col-md-10">
              <base-input
                type="password"
                name="كلمة المرور"
                id="password"
                alternative
                v-model="$v.userAccount.password.$model"
                :rules="{ max: 50, required: true }"
              />
            </div>
          </div>
          <div class="form-check" style="margin-bottom: 3rem">
            <label class="form-check-label row" for="activated">
              <span class="col-md-2 col-form-label form-control-label">نشط</span>
              <base-checkbox
                :disabled="userAccount.id === null"
                type="checkbox"
                id="activated"
                class="col-md-10"
                name="نشط"
                v-model="userAccount.activated"
                :checked="userAccount.activated"
              />
            </label>
          </div>

          <div class="form-group row">
            <label for="example-email-input" class="col-md-2 col-form-label form-control-label">الأذونات</label>
            <div class="col-md-10">
              <select class="form-control" multiple name="authority" v-model="userAccount.authorities">
                <option v-for="authority of authorities" :value="authority" :key="authority">{{ authority }}</option>
              </select>
            </div>
          </div>
          <div class="form-group row">
            <label for="example-email-input" class="col-md-2 col-form-label form-control-label">الصلحيات</label>

            <div class="form-check" v-for="action of actions" style="width: 100%">
              <label class="form-check-label row" :for="action.id">
                <span class="col-md-10 col-form-label form-control-label" style="padding-top: 0px">{{ action.name }}</span>
                <input
                  type="checkbox"
                  :id="action.id"
                  class="col-md-2"
                  :name="action.name"
                  :checked="userAccount.actions !== undefined && userAccount.actions !== null && userAccount.actions.includes(action.id)"
                  @change="changeAction($event, action.id)"
                />
              </label>
            </div>
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
