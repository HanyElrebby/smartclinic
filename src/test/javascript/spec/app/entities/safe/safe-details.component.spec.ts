/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import SafeDetailComponent from '@/entities/safe/safe-details.vue';
import SafeClass from '@/entities/safe/safe-details.component';
import SafeService from '@/entities/safe/safe.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Safe Management Detail Component', () => {
    let wrapper: Wrapper<SafeClass>;
    let comp: SafeClass;
    let safeServiceStub: SinonStubbedInstance<SafeService>;

    beforeEach(() => {
      safeServiceStub = sinon.createStubInstance<SafeService>(SafeService);

      wrapper = shallowMount<SafeClass>(SafeDetailComponent, { store, localVue, router, provide: { safeService: () => safeServiceStub } });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundSafe = { id: 123 };
        safeServiceStub.find.resolves(foundSafe);

        // WHEN
        comp.retrieveSafe(123);
        await comp.$nextTick();

        // THEN
        expect(comp.safe).toBe(foundSafe);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundSafe = { id: 123 };
        safeServiceStub.find.resolves(foundSafe);

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
