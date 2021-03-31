/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import DetailsOfVisitDetailComponent from '@/entities/details-of-visit/details-of-visit-details.vue';
import DetailsOfVisitClass from '@/entities/details-of-visit/details-of-visit-details.component';
import DetailsOfVisitService from '@/entities/details-of-visit/details-of-visit.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('DetailsOfVisit Management Detail Component', () => {
    let wrapper: Wrapper<DetailsOfVisitClass>;
    let comp: DetailsOfVisitClass;
    let detailsOfVisitServiceStub: SinonStubbedInstance<DetailsOfVisitService>;

    beforeEach(() => {
      detailsOfVisitServiceStub = sinon.createStubInstance<DetailsOfVisitService>(DetailsOfVisitService);

      wrapper = shallowMount<DetailsOfVisitClass>(DetailsOfVisitDetailComponent, {
        store,
        localVue,
        router,
        provide: { detailsOfVisitService: () => detailsOfVisitServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundDetailsOfVisit = { id: 123 };
        detailsOfVisitServiceStub.find.resolves(foundDetailsOfVisit);

        // WHEN
        comp.retrieveDetailsOfVisit(123);
        await comp.$nextTick();

        // THEN
        expect(comp.detailsOfVisit).toBe(foundDetailsOfVisit);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDetailsOfVisit = { id: 123 };
        detailsOfVisitServiceStub.find.resolves(foundDetailsOfVisit);

        // WHEN
        comp.beforeRouteEnter({ params: { detailsOfVisitId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.detailsOfVisit).toBe(foundDetailsOfVisit);
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
