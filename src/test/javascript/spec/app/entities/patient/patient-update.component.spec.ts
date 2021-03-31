/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import PatientUpdateComponent from '@/entities/patient/patient-update.vue';
import PatientClass from '@/entities/patient/patient-update.component';
import PatientService from '@/entities/patient/patient.service';

import VisitService from '@/entities/visit/visit.service';

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
  describe('Patient Management Update Component', () => {
    let wrapper: Wrapper<PatientClass>;
    let comp: PatientClass;
    let patientServiceStub: SinonStubbedInstance<PatientService>;

    beforeEach(() => {
      patientServiceStub = sinon.createStubInstance<PatientService>(PatientService);

      wrapper = shallowMount<PatientClass>(PatientUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          patientService: () => patientServiceStub,

          visitService: () => new VisitService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.patient = entity;
        patientServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(patientServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.patient = entity;
        patientServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(patientServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPatient = { id: 123 };
        patientServiceStub.find.resolves(foundPatient);
        patientServiceStub.retrieve.resolves([foundPatient]);

        // WHEN
        comp.beforeRouteEnter({ params: { patientId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.patient).toBe(foundPatient);
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
