/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import ClinicUpdateComponent from '@/entities/clinic/clinic-update.vue';
import ClinicClass from '@/entities/clinic/clinic-update.component';
import ClinicService from '@/entities/clinic/clinic.service';

import DoctorService from '@/entities/doctor/doctor.service';

import VisitService from '@/entities/visit/visit.service';

import UserService from '@/admin/user-management/user-management.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Clinic Management Update Component', () => {
    let wrapper: Wrapper<ClinicClass>;
    let comp: ClinicClass;
    let clinicServiceStub: SinonStubbedInstance<ClinicService>;

    beforeEach(() => {
      clinicServiceStub = sinon.createStubInstance<ClinicService>(ClinicService);

      wrapper = shallowMount<ClinicClass>(ClinicUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          clinicService: () => clinicServiceStub,

          doctorService: () => new DoctorService(),

          visitService: () => new VisitService(),

          userService: () => new UserService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.clinic = entity;
        clinicServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(clinicServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.clinic = entity;
        clinicServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(clinicServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundClinic = { id: 123 };
        clinicServiceStub.find.resolves(foundClinic);
        clinicServiceStub.retrieve.resolves([foundClinic]);

        // WHEN
        comp.beforeRouteEnter({ params: { clinicId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.clinic).toBe(foundClinic);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
