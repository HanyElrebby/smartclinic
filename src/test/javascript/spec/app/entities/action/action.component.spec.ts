/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ActionComponent from '@/entities/action/action.vue';
import ActionClass from '@/entities/action/action.component';
import ActionService from '@/entities/action/action.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Action Management Component', () => {
    let wrapper: Wrapper<ActionClass>;
    let comp: ActionClass;
    let actionServiceStub: SinonStubbedInstance<ActionService>;

    beforeEach(() => {
      actionServiceStub = sinon.createStubInstance<ActionService>(ActionService);
      actionServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ActionClass>(ActionComponent, {
        store,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          actionService: () => actionServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      actionServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllActions();
      await comp.$nextTick();

      // THEN
      expect(actionServiceStub.retrieve.called).toBeTruthy();
      expect(comp.actions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      actionServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeAction();
      await comp.$nextTick();

      // THEN
      expect(actionServiceStub.delete.called).toBeTruthy();
      expect(actionServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
