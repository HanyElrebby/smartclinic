/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import DoctorDetailComponent from '@/entities/doctor/doctor-details.vue';
import DoctorClass from '@/entities/doctor/doctor-details.component';
import DoctorService from '@/entities/doctor/doctor.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Doctor Management Detail Component', () => {
    let wrapper: Wrapper<DoctorClass>;
    let comp: DoctorClass;
    let doctorServiceStub: SinonStubbedInstance<DoctorService>;

    beforeEach(() => {
      doctorServiceStub = sinon.createStubInstance<DoctorService>(DoctorService);

      wrapper = shallowMount<DoctorClass>(DoctorDetailComponent, {
        store,
        localVue,
        router,
        provide: { doctorService: () => doctorServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundDoctor = { id: 123 };
        doctorServiceStub.find.resolves(foundDoctor);

        // WHEN
        comp.retrieveDoctor(123);
        await comp.$nextTick();

        // THEN
        expect(comp.doctor).toBe(foundDoctor);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDoctor = { id: 123 };
        doctorServiceStub.find.resolves(foundDoctor);

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
