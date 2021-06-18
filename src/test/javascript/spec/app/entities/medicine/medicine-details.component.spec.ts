/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import MedicineDetailComponent from '@/entities/medicine/medicine-details.vue';
import MedicineClass from '@/entities/medicine/medicine-details.component';
import MedicineService from '@/entities/medicine/medicine.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Medicine Management Detail Component', () => {
    let wrapper: Wrapper<MedicineClass>;
    let comp: MedicineClass;
    let medicineServiceStub: SinonStubbedInstance<MedicineService>;

    beforeEach(() => {
      medicineServiceStub = sinon.createStubInstance<MedicineService>(MedicineService);

      wrapper = shallowMount<MedicineClass>(MedicineDetailComponent, {
        store,
        localVue,
        router,
        provide: { medicineService: () => medicineServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundMedicine = { id: 123 };
        medicineServiceStub.find.resolves(foundMedicine);

        // WHEN
        comp.retrieveMedicine(123);
        await comp.$nextTick();

        // THEN
        expect(comp.medicine).toBe(foundMedicine);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMedicine = { id: 123 };
        medicineServiceStub.find.resolves(foundMedicine);

        // WHEN
        comp.beforeRouteEnter({ params: { medicineId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.medicine).toBe(foundMedicine);
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
