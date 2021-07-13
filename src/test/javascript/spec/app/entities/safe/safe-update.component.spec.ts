/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import SafeUpdateComponent from '@/entities/safe/safe-update.vue';
import SafeClass from '@/entities/safe/safe-update.component';
import SafeService from '@/entities/safe/safe.service';

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
  describe('Safe Management Update Component', () => {
    let wrapper: Wrapper<SafeClass>;
    let comp: SafeClass;
    let safeServiceStub: SinonStubbedInstance<SafeService>;

    beforeEach(() => {
      safeServiceStub = sinon.createStubInstance<SafeService>(SafeService);

      wrapper = shallowMount<SafeClass>(SafeUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          safeService: () => safeServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.safe = entity;
        safeServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(safeServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.safe = entity;
        safeServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(safeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundSafe = { id: 123 };
        safeServiceStub.find.resolves(foundSafe);
        safeServiceStub.retrieve.resolves([foundSafe]);

        // WHEN
        comp.beforeRouteEnter({ params: { safeId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.safe).toBe(foundSafe);
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
