/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import DoctorUpdateComponent from '@/entities/doctor/doctor-update.vue';
import DoctorClass from '@/entities/doctor/doctor-update.component';
import DoctorService from '@/entities/doctor/doctor.service';

import ClinicService from '@/entities/clinic/clinic.service';

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
  describe('Doctor Management Update Component', () => {
    let wrapper: Wrapper<DoctorClass>;
    let comp: DoctorClass;
    let doctorServiceStub: SinonStubbedInstance<DoctorService>;

    beforeEach(() => {
      doctorServiceStub = sinon.createStubInstance<DoctorService>(DoctorService);

      wrapper = shallowMount<DoctorClass>(DoctorUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          doctorService: () => doctorServiceStub,

          clinicService: () => new ClinicService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.doctor = entity;
        doctorServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(doctorServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.doctor = entity;
        doctorServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(doctorServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDoctor = { id: 123 };
        doctorServiceStub.find.resolves(foundDoctor);
        doctorServiceStub.retrieve.resolves([foundDoctor]);

        // WHEN
        comp.beforeRouteEnter({ params: { doctorId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.doctor).toBe(foundDoctor);
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
