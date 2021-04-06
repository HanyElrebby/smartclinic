<template>
  <div>
    <div class="row">
      <div class="col-md-6">
        <button type="button" @click="navigateTo('/doctor')" class="button button1" style="background-color: #7575e0; width: 100%">
          الاطباء
        </button>
      </div>
      <div class="col-md-6">
        <button type="button" @click="navigateTo('/clinic')" class="button button1" style="background-color: #7575e0; width: 100%">
          العيادات
        </button>
      </div>
    </div>
    <div class="row">
      <div class="col-md-12">
        <button
          type="button"
          @click="navigateTo('/admin/user-management')"
          class="button button1"
          style="background-color: #ed5151; width: 100%"
        >
          المستخدمين
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./owner-dashboard.component.ts"></script>

<style scoped>
.button1 {
  box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}
.button {
  background-color: #4caf50; /* Green */
  border: none;
  color: white;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;
  -webkit-transition-duration: 0.4s; /* Safari */
  transition-duration: 0.4s;
}
</style>
