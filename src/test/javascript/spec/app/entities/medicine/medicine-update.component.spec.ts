/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import MedicineUpdateComponent from '@/entities/medicine/medicine-update.vue';
import MedicineClass from '@/entities/medicine/medicine-update.component';
import MedicineService from '@/entities/medicine/medicine.service';

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
  describe('Medicine Management Update Component', () => {
    let wrapper: Wrapper<MedicineClass>;
    let comp: MedicineClass;
    let medicineServiceStub: SinonStubbedInstance<MedicineService>;

    beforeEach(() => {
      medicineServiceStub = sinon.createStubInstance<MedicineService>(MedicineService);

      wrapper = shallowMount<MedicineClass>(MedicineUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          medicineService: () => medicineServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.medicine = entity;
        medicineServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(medicineServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.medicine = entity;
        medicineServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(medicineServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMedicine = { id: 123 };
        medicineServiceStub.find.resolves(foundMedicine);
        medicineServiceStub.retrieve.resolves([foundMedicine]);

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
