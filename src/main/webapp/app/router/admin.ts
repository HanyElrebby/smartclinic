import OwnerDashboard from '@/admin/dashboard/owner-dashboard.component';
import { Authority } from '@/shared/security/authority';

const JhiUserManagementComponent = () => import('@/admin/user-management/user-management.vue');
const JhiUserManagementViewComponent = () => import('@/admin/user-management/user-management-view.vue');
const JhiUserManagementEditComponent = () => import('@/admin/user-management/user-management-edit.vue');
const JhiDocsComponent = () => import('@/admin/docs/docs.vue');
const JhiConfigurationComponent = () => import('@/admin/configuration/configuration.vue');
const JhiHealthComponent = () => import('@/admin/health/health.vue');
const JhiLogsComponent = () => import('@/admin/logs/logs.vue');
const JhiMetricsComponent = () => import('@/admin/metrics/metrics.vue');

const adminDashboard = () => import('@/admin/dashboard/ownerDashboard.vue');

const adminCharts = () => import('@/admin/charts/charts.vue');

export default [
  {
    path: '/admin/user-management',
    name: 'JhiUser',
    component: JhiUserManagementComponent,
    meta: { authorities: [Authority.OWNER] },
  },
  {
    path: '/admin/user-management/new',
    name: 'JhiUserCreate',
    component: JhiUserManagementEditComponent,
    meta: { authorities: [Authority.OWNER] },
  },
  {
    path: '/admin/user-management/:userId/edit',
    name: 'JhiUserEdit',
    component: JhiUserManagementEditComponent,
    meta: { authorities: [Authority.OWNER] },
  },
  {
    path: '/admin/user-management/:userId/view',
    name: 'JhiUserView',
    component: JhiUserManagementViewComponent,
    meta: { authorities: [Authority.OWNER] },
  },
  {
    path: '/admin/docs',
    name: 'JhiDocsComponent',
    component: JhiDocsComponent,
    meta: { authorities: [Authority.OWNER] },
  },
  {
    path: '/admin/health',
    name: 'JhiHealthComponent',
    component: JhiHealthComponent,
    meta: { authorities: [Authority.OWNER] },
  },
  {
    path: '/admin/logs',
    name: 'JhiLogsComponent',
    component: JhiLogsComponent,
    meta: { authorities: [Authority.OWNER] },
  },
  {
    path: '/admin/metrics',
    name: 'JhiMetricsComponent',
    component: JhiMetricsComponent,
    meta: { authorities: [Authority.OWNER] },
  },
  {
    path: '/admin/configuration',
    name: 'JhiConfigurationComponent',
    component: JhiConfigurationComponent,
    meta: { authorities: [Authority.OWNER] },
  },
  {
    path: '/admin/dashboard',
    name: 'OwnerDashboard',
    component: adminDashboard,
    meta: { authorities: [Authority.OWNER] },
  },
  {
    path: '/admin/dashboard',
    name: 'OwnerDashboard',
    component: adminDashboard,
    meta: { authorities: [Authority.OWNER] },
  },
  {
    path: '/admin/charts',
    name: 'OwnerCharts',
    component: adminCharts,
    meta: { authorities: [Authority.OWNER] },
  },
];
