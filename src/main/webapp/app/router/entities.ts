import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const Doctor = () => import('@/entities/doctor/doctor.vue');
// prettier-ignore
const DoctorUpdate = () => import('@/entities/doctor/doctor-update.vue');
// prettier-ignore
const DoctorDetails = () => import('@/entities/doctor/doctor-details.vue');
// prettier-ignore
const Clinic = () => import('@/entities/clinic/clinic.vue');
// prettier-ignore
const ClinicUpdate = () => import('@/entities/clinic/clinic-update.vue');
// prettier-ignore
const ClinicDetails = () => import('@/entities/clinic/clinic-details.vue');
// prettier-ignore
const Patient = () => import('@/entities/patient/patient.vue');
// prettier-ignore
const PatientUpdate = () => import('@/entities/patient/patient-update.vue');
// prettier-ignore
const PatientDetails = () => import('@/entities/patient/patient-details.vue');
// prettier-ignore
const Visit = () => import('@/entities/visit/visit.vue');
// prettier-ignore
const VisitUpdate = () => import('@/entities/visit/visit-update.vue');
// prettier-ignore
const VisitDetails = () => import('@/entities/visit/visit-details.vue');
// prettier-ignore
const DetailsOfVisit = () => import('@/entities/details-of-visit/details-of-visit.vue');
// prettier-ignore
const DetailsOfVisitUpdate = () => import('@/entities/details-of-visit/details-of-visit-update.vue');
// prettier-ignore
const DetailsOfVisitDetails = () => import('@/entities/details-of-visit/details-of-visit-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/doctor',
    name: 'Doctor',
    component: Doctor,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/doctor/new',
    name: 'DoctorCreate',
    component: DoctorUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/doctor/:doctorId/edit',
    name: 'DoctorEdit',
    component: DoctorUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/doctor/:doctorId/view',
    name: 'DoctorView',
    component: DoctorDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/clinic',
    name: 'Clinic',
    component: Clinic,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/clinic/new',
    name: 'ClinicCreate',
    component: ClinicUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/clinic/:clinicId/edit',
    name: 'ClinicEdit',
    component: ClinicUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/clinic/:clinicId/view',
    name: 'ClinicView',
    component: ClinicDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/patient',
    name: 'Patient',
    component: Patient,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/patient/new',
    name: 'PatientCreate',
    component: PatientUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/patient/:patientId/edit',
    name: 'PatientEdit',
    component: PatientUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/patient/:patientId/view',
    name: 'PatientView',
    component: PatientDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/visit',
    name: 'Visit',
    component: Visit,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/visit/new',
    name: 'VisitCreate',
    component: VisitUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/visit/:visitId/edit',
    name: 'VisitEdit',
    component: VisitUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/visit/:visitId/view',
    name: 'VisitView',
    component: VisitDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/details-of-visit',
    name: 'DetailsOfVisit',
    component: DetailsOfVisit,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/details-of-visit/new',
    name: 'DetailsOfVisitCreate',
    component: DetailsOfVisitUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/details-of-visit/:detailsOfVisitId/edit',
    name: 'DetailsOfVisitEdit',
    component: DetailsOfVisitUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/details-of-visit/:detailsOfVisitId/view',
    name: 'DetailsOfVisitView',
    component: DetailsOfVisitDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
