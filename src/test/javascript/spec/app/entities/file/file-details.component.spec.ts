/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import FileDetailComponent from '@/entities/file/file-details.vue';
import FileClass from '@/entities/file/file-details.component';
import FileService from '@/entities/file/file.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('File Management Detail Component', () => {
    let wrapper: Wrapper<FileClass>;
    let comp: FileClass;
    let fileServiceStub: SinonStubbedInstance<FileService>;

    beforeEach(() => {
      fileServiceStub = sinon.createStubInstance<FileService>(FileService);

      wrapper = shallowMount<FileClass>(FileDetailComponent, { store, localVue, router, provide: { fileService: () => fileServiceStub } });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundFile = { id: 123 };
        fileServiceStub.find.resolves(foundFile);

        // WHEN
        comp.retrieveFile(123);
        await comp.$nextTick();

        // THEN
        expect(comp.file).toBe(foundFile);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundFile = { id: 123 };
        fileServiceStub.find.resolves(foundFile);

        // WHEN
        comp.beforeRouteEnter({ params: { fileId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.file).toBe(foundFile);
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
