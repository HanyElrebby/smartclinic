/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import DetailsOfVisitUpdateComponent from '@/entities/details-of-visit/details-of-visit-update.vue';
import DetailsOfVisitClass from '@/entities/details-of-visit/details-of-visit-update.component';
import DetailsOfVisitService from '@/entities/details-of-visit/details-of-visit.service';

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
  describe('DetailsOfVisit Management Update Component', () => {
    let wrapper: Wrapper<DetailsOfVisitClass>;
    let comp: DetailsOfVisitClass;
    let detailsOfVisitServiceStub: SinonStubbedInstance<DetailsOfVisitService>;

    beforeEach(() => {
      detailsOfVisitServiceStub = sinon.createStubInstance<DetailsOfVisitService>(DetailsOfVisitService);

      wrapper = shallowMount<DetailsOfVisitClass>(DetailsOfVisitUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          detailsOfVisitService: () => detailsOfVisitServiceStub,

          visitService: () => new VisitService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.detailsOfVisit = entity;
        detailsOfVisitServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(detailsOfVisitServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.detailsOfVisit = entity;
        detailsOfVisitServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(detailsOfVisitServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDetailsOfVisit = { id: 123 };
        detailsOfVisitServiceStub.find.resolves(foundDetailsOfVisit);
        detailsOfVisitServiceStub.retrieve.resolves([foundDetailsOfVisit]);

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
