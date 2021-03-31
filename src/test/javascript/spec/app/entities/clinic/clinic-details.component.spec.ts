/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ClinicDetailComponent from '@/entities/clinic/clinic-details.vue';
import ClinicClass from '@/entities/clinic/clinic-details.component';
import ClinicService from '@/entities/clinic/clinic.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Clinic Management Detail Component', () => {
    let wrapper: Wrapper<ClinicClass>;
    let comp: ClinicClass;
    let clinicServiceStub: SinonStubbedInstance<ClinicService>;

    beforeEach(() => {
      clinicServiceStub = sinon.createStubInstance<ClinicService>(ClinicService);

      wrapper = shallowMount<ClinicClass>(ClinicDetailComponent, {
        store,
        localVue,
        router,
        provide: { clinicService: () => clinicServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundClinic = { id: 123 };
        clinicServiceStub.find.resolves(foundClinic);

        // WHEN
        comp.retrieveClinic(123);
        await comp.$nextTick();

        // THEN
        expect(comp.clinic).toBe(foundClinic);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundClinic = { id: 123 };
        clinicServiceStub.find.resolves(foundClinic);

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
