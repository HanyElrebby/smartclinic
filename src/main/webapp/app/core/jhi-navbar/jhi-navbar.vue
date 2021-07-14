<template>
  <b-navbar data-cy="navbar" toggleable="md" type="dark" class="container bg-info jh-navbar" v-if="authenticated">
    <b-collapse is-nav id="header-tabs">
      <b-navbar-nav class="mx-auto">
        <b-nav-item v-if="checkAction('com.smartclinic.doctor.tab')" :to="{ name: 'PatientView', params: { patientId: -1 } }" exact>
          <span>
            <font-awesome-icon icon="home" />
            <span class="account-font">الطبيب</span>
          </span>
        </b-nav-item>

        <b-nav-item v-if="checkAction('com.smartclinic.visit.tab')" to="/visit" exact>
          <span>
            <font-awesome-icon icon="home" />
            <span class="account-font">الحجوزات</span>
          </span>
        </b-nav-item>

        <b-nav-item v-if="checkAction('com.smartclinic.patient.tab')" to="/patient" exact>
          <span>
            <font-awesome-icon icon="home" />
            <span class="account-font">المرضى</span>
          </span>
        </b-nav-item>

        <b-nav-item-dropdown
          right
          href="javascript:void(0);"
          id="reports-menu"
          style="text-align: center"
          active-class="active"
          class="pointer"
          data-cy="reportsMenu"
          v-if="checkAction('com.smartclinic.reports.tab')"
        >
          <span class="account-font" slot="button-content">
            <font-awesome-icon icon="home" />
            <span class="account-font">التقارير</span>
          </span>
          <b-dropdown-item
            data-cy="settings"
            v-on:click="openDialogPatientReport()"
            tag="b-dropdown-item"
            v-if="authenticated"
            active-class="active"
          >
            <font-awesome-icon icon="wrench" />
            <span>تقرير المرضى</span>
          </b-dropdown-item>
          <b-dropdown-item
            data-cy="settings"
            v-on:click="openDialogPatientVisitReport()"
            tag="b-dropdown-item"
            v-if="authenticated"
            active-class="active"
          >
            <font-awesome-icon icon="wrench" />
            <span>تقرير زيارة مريض</span>
          </b-dropdown-item>
        </b-nav-item-dropdown>

        <b-nav-item-dropdown
          id="languagesnavBarDropdown"
          right
          v-if="languages && Object.keys(languages).length > 1 && checkAction('com.smartclinic.lang.tab')"
        >
          <span slot="button-content">
            <font-awesome-icon icon="flag" />
            <span class="no-bold" v-text="$t('global.menu.language')">Language</span>
          </span>
          <b-dropdown-item
            v-for="(value, key) in languages"
            :key="`lang-${key}`"
            v-on:click="changeLanguage(key)"
            :class="{ active: isActiveLanguage(key) }"
          >
            {{ value.name }}
          </b-dropdown-item>
        </b-nav-item-dropdown>

        <b-nav-item-dropdown
          right
          href="javascript:void(0);"
          id="account-menu"
          style="text-align: center"
          :class="{ 'router-link-active': subIsActive('/account') }"
          active-class="active"
          class="pointer"
          data-cy="accountMenu"
        >
          <span class="account-font" slot="button-content">
            <font-awesome-icon icon="user" />
            <span> الحساب </span>
          </span>
          <b-dropdown-item
            data-cy="settings"
            to="/medicine"
            tag="b-dropdown-item"
            v-if="authenticated && checkAction('com.smartclinic.medicine.tab')"
            active-class="active"
          >
            <font-awesome-icon icon="wrench" />
            <span>الادوية</span>
          </b-dropdown-item>
          <b-dropdown-item
            data-cy="settings"
            to="/account/settings"
            tag="b-dropdown-item"
            v-if="authenticated && checkAction('com.smartclinic.settings.tab')"
            active-class="active"
          >
            <font-awesome-icon icon="wrench" />
            <span>الإعدادات</span>
          </b-dropdown-item>
          <b-dropdown-item data-cy="passwordItem" to="/account/password" tag="b-dropdown-item" v-if="authenticated" active-class="active">
            <font-awesome-icon icon="lock" />
            <span>كلمة المرور</span>
          </b-dropdown-item>
          <b-dropdown-item data-cy="logout" v-if="authenticated" v-on:click="logout()" id="logout" active-class="active">
            <font-awesome-icon icon="sign-out-alt" />
            <span>تسجيل الخروج</span>
          </b-dropdown-item>
          <b-dropdown-item data-cy="login" v-if="!authenticated" tag="b-dropdown-item" to="/login" id="login" active-class="active">
            <font-awesome-icon icon="sign-in-alt" />
            <span>تسجيل الدخول</span>
          </b-dropdown-item>
        </b-nav-item-dropdown>
      </b-navbar-nav>
    </b-collapse>
    <img src="/../../../content/images/logo.png" />

    <b-modal ref="patientReport" id="patientReport" style="width: 1000px">
      <div class="modal-body">
        <p>هل تريد تحميل نسخة من تقرير المرضى</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialogPatientReport()">إلغاء</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-visit"
          data-cy="entityConfirmDeleteButton"
          v-on:click="
            downloadPatientsReport();
            closeDialogPatientReport();
          "
        >
          تحميل
        </button>
      </div>
    </b-modal>

    <b-modal ref="patientVisitReport" id="patientVisitReport" style="width: 1000px">
      <div class="modal-body">
        <form name="editForm" role="form" novalidate>
          <div class="form-group row">
            <label class="col-md-2 col-form-label form-control-label" for="visit-patient">المريض</label>
            <div class="col-md-10">
              <select class="form-control" id="visit-patient" data-cy="patient" name="patient" v-model="selectedpatient">
                <option v-bind:value="null"></option>
                <option
                  v-bind:value="selectedpatient && patientOption.id === selectedpatient.id ? selectedpatient : patientOption"
                  v-for="patientOption in patients"
                  :key="patientOption.id"
                >
                  {{ patientOption.name }}
                </option>
              </select>
            </div>
          </div>
          <p>هل تريد تحميل نسخة من تقرير المريض</p>
        </form>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialogPatientVisitReport()">إلغاء</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-visit"
          data-cy="entityConfirmDeleteButton"
          v-on:click="
            downloadPatientsVisitReport();
            closeDialogPatientVisitReport();
          "
        >
          تحميل
        </button>
      </div>
    </b-modal>
  </b-navbar>
</template>

<script lang="ts" src="./jhi-navbar.component.ts"></script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
/* ==========================================================================
    Navbar
    ========================================================================== */
.navbar-version {
  font-size: 10px;
  color: #ccc;
}

.jh-navbar {
  padding: 0.2em 1em;
}

.jh-navbar .profile-image {
  margin: -10px 0px;
  height: 40px;
  width: 40px;
  border-radius: 50%;
}

.jh-navbar .dropdown-item.active,
.jh-navbar .dropdown-item.active:focus,
.jh-navbar .dropdown-item.active:hover {
  background-color: #353d47;
}

.jh-navbar .dropdown-toggle::after {
  margin-left: 0.15em;
}

.jh-navbar ul.navbar-nav {
  padding: 0.5em;
}

.jh-navbar .navbar-nav .nav-item {
  margin-left: 1.5rem;
}

.jh-navbar a.nav-link,
.jh-navbar .no-bold {
  font-weight: 400;
}

.jh-navbar .jh-navbar-toggler {
  color: #ccc;
  font-size: 1.5em;
  padding: 10px;
}

.jh-navbar .jh-navbar-toggler:hover {
  color: #fff;
}

@media screen and (min-width: 768px) {
  .jh-navbar-toggler {
    display: none;
  }
}

@media screen and (min-width: 768px) and (max-width: 1150px) {
  span span {
    display: none;
  }
}

.navbar-title {
  display: inline-block;
  vertical-align: middle;
  color: white;
}

/* ==========================================================================
    Logo styles
    ========================================================================== */
.navbar-brand.logo {
  padding: 5px 15px;
}

.logo .logo-img {
  height: 45px;
  display: inline-block;
  vertical-align: middle;
  width: 70px;
}

.account-font {
  color: #ffffff;
}
.logo-img {
  height: 100%;
  background: url('../../../content/images/logo-jhipster.png') no-repeat center center;
  background-size: contain;
  width: 100%;
  filter: drop-shadow(0 0 0.05rem white);
}
</style>
