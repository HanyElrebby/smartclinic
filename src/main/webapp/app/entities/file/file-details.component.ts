import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IFile } from '@/shared/model/file.model';
import FileService from './file.service';

@Component
export default class FileDetails extends mixins(JhiDataUtils) {
  @Inject('fileService') private fileService: () => FileService;
  public file: IFile = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.fileId) {
        vm.retrieveFile(to.params.fileId);
      }
    });
  }

  public retrieveFile(fileId) {
    this.fileService()
      .find(fileId)
      .then(res => {
        this.file = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
