import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import PatientService from '@/entities/patient/patient.service';
import { IPatient } from '@/shared/model/patient.model';

import { IFile, File } from '@/shared/model/file.model';
import FileService from './file.service';

const validations: any = {
  file: {
    fileName: {},
    file: {},
    dateUpload: {},
    note: {},
    createdBy: {},
    updatedBy: {},
  },
};

@Component({
  validations,
})
export default class FileUpdate extends mixins(JhiDataUtils) {
  @Inject('fileService') private fileService: () => FileService;
  public file: IFile = new File();

  @Inject('patientService') private patientService: () => PatientService;

  public patients: IPatient[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.fileId) {
        vm.retrieveFile(to.params.fileId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.file.id) {
      this.fileService()
        .update(this.file)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A File is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.fileService()
        .create(this.file)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A File is created with identifier ' + param.id;
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.file[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.file[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.file[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.file[field] = null;
    }
  }

  public retrieveFile(fileId): void {
    this.fileService()
      .find(fileId)
      .then(res => {
        res.dateUpload = new Date(res.dateUpload);
        this.file = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.patientService()
      .retrieve()
      .then(res => {
        this.patients = res.data;
      });
  }
}
