<template>
  <div>
    <!-- Header -->
    <div class="header bg-gradient-success py-7 py-lg-8 pt-lg-9">
      <b-container class="container">
        <div class="header-body text-center mb-7">
          <b-row class="justify-content-center">
            <b-col xl="5" lg="6" md="8" class="px-5">
              <h1 class="text-white">إنشاء حساب</h1>
              <div class="alert alert-success" role="alert" v-if="success">
                <strong>تم إنشاء حساب</strong> يرجى فحص البريد الالكترونى لتفعيل الحساب
              </div>

              <div class="alert alert-danger" role="alert" v-if="error"><strong>لم يتم إنشاء الحساب</strong>يرجى المحاولة لاحقا</div>

              <div class="alert alert-danger" role="alert" v-if="errorUserExists">
                <strong>اسم المستخدم محجوز</strong> يرجى اختيار اسم أخر
              </div>

              <div class="alert alert-danger" role="alert" v-if="errorEmailExists">
                <strong>Email is already in use!</strong> Please choose another one.
              </div>
            </b-col>
          </b-row>
        </div>
      </b-container>
      <div class="separator separator-bottom separator-skew zindex-100">
        <svg x="0" y="0" viewBox="0 0 2560 100" preserveAspectRatio="none" version="1.1" xmlns="http://www.w3.org/2000/svg">
          <polygon class="fill-default" points="2560 0 2560 100 0 100"></polygon>
        </svg>
      </div>
    </div>
    <!-- Page content -->
    <b-container class="mt--8 pb-5">
      <!-- Table -->
      <b-row class="justify-content-center">
        <b-col lg="6" md="8">
          <b-card no-body class="bg-secondary border-0">
            <b-card-body class="px-lg-5 py-lg-5">
              <div class="text-center text-muted mb-4"></div>
              <validation-observer v-slot="{ handleSubmit }" ref="formValidator">
                <b-form role="form" @submit.prevent="handleSubmit(register)">
                  <base-input
                    alternative
                    class="mb-3"
                    prepend-icon="ni ni-hat-3"
                    placeholder="Name"
                    name="Name"
                    :rules="{ required: true, min: 1, max: 50 }"
                    v-model="$v.registerAccount.login.$model"
                  >
                  </base-input>

                  <base-input
                    alternative
                    class="mb-3"
                    prepend-icon="ni ni-email-83"
                    placeholder="Email"
                    name="Email"
                    :rules="{ required: true, email: true, min: 5, max: 200 }"
                    v-model="$v.registerAccount.email.$model"
                  >
                  </base-input>

                  <base-input
                    alternative
                    class="mb-3"
                    prepend-icon="ni ni-lock-circle-open"
                    placeholder="password"
                    type="password"
                    name="Password"
                    :rules="{ required: true, min: 4, max: 50 }"
                    v-model="$v.registerAccount.password.$model"
                  >
                  </base-input>

                  <div class="text-center">
                    <b-button type="submit" variant="primary" class="mt-4">Create account</b-button>
                  </div>
                </b-form>
              </validation-observer>
            </b-card-body>
          </b-card>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>
<script lang="ts" src="./register.component.ts"></script>
