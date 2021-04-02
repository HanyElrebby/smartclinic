<template>
  <div id="app">
    <ribbon></ribbon>
    <div id="app-header">
      <jhi-navbar></jhi-navbar>
    </div>
    <div class="container-fluid">
      <div class="wrapper">
        <side-bar v-if="authenticated">
          <template slot="links">
            <sidebar-item
              :link="{
                name: 'الصفحه الرئيسية',
                path: '/home',
                icon: 'ni ni-tv-2 text-primary',
              }"
            >
            </sidebar-item>

            <sidebar-item
              :link="{
                name: 'الأطباء',
                path: '/doctor',
                icon: 'ni ni-planet text-blue',
              }"
            >
            </sidebar-item>

            <sidebar-item
              :link="{
                name: 'المرضى',
                path: '/patient',
                icon: 'ni ni-pin-3 text-orange',
              }"
            >
            </sidebar-item>

            <sidebar-item
              :link="{
                name: 'الزيارات',
                path: '/visit',
                icon: 'ni ni-single-02 text-yellow',
              }"
            >
            </sidebar-item>

            <sidebar-item
              :link="{
                name: 'تفاصيل الزيارات',
                path: '/details-of-visit',
                icon: 'ni ni-bullet-list-67 text-red',
              }"
            >
            </sidebar-item>

            <sidebar-item
              :link="{
                name: 'المستخدمين',
                path: '/admin/user-management',
                icon: 'ni ni-key-25 text-info',
              }"
            >
            </sidebar-item>
          </template>

          <!--<template slot="links-after">
            <hr class="my-3" />
            <h6 class="navbar-heading p-0 text-muted">Documentation</h6>

            <b-nav class="navbar-nav mb-md-3">
              <b-nav-item href="https://www.creative-tim.com/learning-lab/bootstrap-vue/quick-start/argon-dashboard">
                <i class="ni ni-spaceship"></i>
                <b-nav-text class="p-0">Getting started</b-nav-text>
              </b-nav-item>
              <b-nav-item href="https://www.creative-tim.com/learning-lab/bootstrap-vue/colors/argon-dashboard">
                <i class="ni ni-palette"></i>
                <b-nav-text class="p-0">Foundation</b-nav-text>
              </b-nav-item>
              <b-nav-item href="https://www.creative-tim.com/learning-lab/bootstrap-vue/avatar/argon-dashboard">
                <i class="ni ni-ui-04"></i>
                <b-nav-text class="p-0">Components</b-nav-text>
              </b-nav-item>
            </b-nav>
          </template> -->
        </side-bar>
        <div class="main-content">
          <div class="card jh-card">
            <router-view></router-view>
          </div>
          <b-modal id="login-page" hide-footer lazy>
            <span data-cy="loginTitle" slot="modal-title" id="login-title">Sign in</span>
            <login-form></login-form>
          </b-modal>

          <jhi-footer></jhi-footer>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./app.component.ts"></script>
