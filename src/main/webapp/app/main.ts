// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.common with an alias.
import Vue from 'vue';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import App from './app.vue';
import Vue2Filters from 'vue2-filters';
import { ToastPlugin } from 'bootstrap-vue';
import router from './router';
import * as config from './shared/config/config';
import * as bootstrapVueConfig from './shared/config/config-bootstrap-vue';
import JhiItemCountComponent from './shared/jhi-item-count.vue';
import JhiSortIndicatorComponent from './shared/sort/jhi-sort-indicator.vue';
import InfiniteLoading from 'vue-infinite-loading';
import HealthService from './admin/health/health.service';
import MetricsService from './admin/metrics/metrics.service';
import LogsService from './admin/logs/logs.service';
import ConfigurationService from '@/admin/configuration/configuration.service';
import ActivateService from './account/activate/activate.service';
import RegisterService from './account/register/register.service';
import UserManagementService from '@/admin/user-management/user-management.service';
import LoginService from './account/login.service';
import AccountService from './account/account.service';

//import '../content/scss/vendor.scss';
import '../content/scss/argon.scss';

import UserOAuth2Service from '@/entities/user/user.oauth2.service';
import BaseInput from '@/components/Inputs/BaseInput.vue';
import BaseDropdown from '@/components/BaseDropdown.vue';

/* tslint:disable */

import { BootstrapVue, IconsPlugin } from 'bootstrap-vue';

import DoctorService from '@/entities/doctor/doctor.service';
import ClinicService from '@/entities/clinic/clinic.service';
import PatientService from '@/entities/patient/patient.service';
import VisitService from '@/entities/visit/visit.service';
import DetailsOfVisitService from '@/entities/details-of-visit/details-of-visit.service';
import GlobalComponents from './globalComponents';
import ElTable from 'element-ui';
import GlobalDirectives from './globalDirectives';

import DatePicker from 'vue2-datepicker';
import 'vue2-datepicker/index.css';
import 'vue2-datepicker/locale/ar';

import { Datetime } from 'vue-datetime';
// You need a specific loader for CSS files
import 'vue-datetime/dist/vue-datetime.css';

import { Settings } from 'luxon';
import MedicineService from './entities/medicine/medicine.service';
import FileService from './entities/file/file.service';
import SafeService from '@/entities/safe/safe.service';
import ExpenseService from '@/entities/expense/expense.service';
import TrackerService from './core/SidebarPlugin/tracker.service';
import TranslationService from './locale/translation.service';

Settings.defaultLocale = 'ar';

import Highchart from 'highcharts/highcharts';
import HighchartsVue from 'highcharts-vue';
import stockInit from 'highcharts/modules/stock';
import ChartValuesService from './entities/chart-values/chart-values.service';

stockInit(Highchart);
Vue.use(HighchartsVue);

// jhipster-needle-add-entity-service-to-main-import - JHipster will import entities services here

/* tslint:enable */
Vue.config.productionTip = false;
config.initVueApp(Vue);
config.initFortAwesome(Vue);
bootstrapVueConfig.initBootstrapVue(Vue);
Vue.use(Vue2Filters);
Vue.use(ToastPlugin);
Vue.use(ElTable);
Vue.component('font-awesome-icon', FontAwesomeIcon);
Vue.component('jhi-item-count', JhiItemCountComponent);
Vue.component('jhi-sort-indicator', JhiSortIndicatorComponent);
Vue.component('infinite-loading', InfiniteLoading);
Vue.use(GlobalComponents);
Vue.use(GlobalDirectives);
Vue.use(BootstrapVue);
Vue.use(IconsPlugin);

const trackerService = new TrackerService(router);

const store = config.initVueXStore(Vue);

const loginService = new LoginService();

const i18n = config.initI18N(Vue);

const translationService = new TranslationService(store, i18n);

const accountService = new AccountService(store, translationService, trackerService, router);

router.beforeEach((to, from, next) => {
  if (!to.matched.length) {
    next('/not-found');
  }

  if (to.meta && to.meta.authorities && to.meta.authorities.length > 0) {
    accountService.hasAnyAuthorityAndCheckAuth(to.meta.authorities).then(value => {
      if (!value) {
        sessionStorage.setItem('requested-url', to.fullPath);
        next('/forbidden');
      } else {
        next();
      }
    });
  } else {
    // no authorities, so just proceed
    next();
  }
});

/* tslint:disable */
new Vue({
  el: '#app',
  components: { App, datetime: Datetime },
  template: '<App/>',
  router,
  i18n,
  provide: {
    loginService: () => loginService,
    activateService: () => new ActivateService(),
    registerService: () => new RegisterService(),
    userService: () => new UserManagementService(),
    healthService: () => new HealthService(),
    configurationService: () => new ConfigurationService(),
    logsService: () => new LogsService(),
    metricsService: () => new MetricsService(),
    trackerService: () => trackerService,
    userOAuth2Service: () => new UserOAuth2Service(),
    doctorService: () => new DoctorService(),
    clinicService: () => new ClinicService(),
    patientService: () => new PatientService(),
    visitService: () => new VisitService(store),
    detailsOfVisitService: () => new DetailsOfVisitService(),
    medicineService: () => new MedicineService(),
    fileService: () => new FileService(),
    safeService: () => new SafeService(),
    expenseService: () => new ExpenseService(),
    chartValuesService: () => new ChartValuesService(),
    translationService: () => translationService,
    // jhipster-needle-add-entity-service-to-main - JHipster will import entities services here
    accountService: () => accountService,
  },
  store,
});
