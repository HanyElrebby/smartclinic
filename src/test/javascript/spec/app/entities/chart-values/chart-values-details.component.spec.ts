/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ChartValuesDetailComponent from '@/entities/chart-values/chart-values-details.vue';
import ChartValuesClass from '@/entities/chart-values/chart-values-details.component';
import ChartValuesService from '@/entities/chart-values/chart-values.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ChartValues Management Detail Component', () => {
    let wrapper: Wrapper<ChartValuesClass>;
    let comp: ChartValuesClass;
    let chartValuesServiceStub: SinonStubbedInstance<ChartValuesService>;

    beforeEach(() => {
      chartValuesServiceStub = sinon.createStubInstance<ChartValuesService>(ChartValuesService);

      wrapper = shallowMount<ChartValuesClass>(ChartValuesDetailComponent, {
        store,
        localVue,
        router,
        provide: { chartValuesService: () => chartValuesServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundChartValues = { id: 123 };
        chartValuesServiceStub.find.resolves(foundChartValues);

        // WHEN
        comp.retrieveChartValues(123);
        await comp.$nextTick();

        // THEN
        expect(comp.chartValues).toBe(foundChartValues);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundChartValues = { id: 123 };
        chartValuesServiceStub.find.resolves(foundChartValues);

        // WHEN
        comp.beforeRouteEnter({ params: { chartValuesId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.chartValues).toBe(foundChartValues);
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
