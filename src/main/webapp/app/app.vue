<template>
  <div id="app">
    <ribbon></ribbon>
    <div id="app-header">
      <jhi-navbar></jhi-navbar>
    </div>
    <waiting-panel v-if="authenticated"></waiting-panel>
    <div class="container">
      <div class="wrapper">
        <div class="main-content">
          <div class="card jh-card bg-secondary">
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
