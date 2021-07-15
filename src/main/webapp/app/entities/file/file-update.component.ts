import { Component, Inject } from 'vue-property-decorator';

import { required, maxLength } from 'vuelidate/lib/validators';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import PatientService from '@/entities/patient/patient.service';
import { IPatient, Patient } from '@/shared/model/patient.model';

import { IFile, File } from '@/shared/model/file.model';
import FileService from './file.service';

const validations: any = {
  file: {
    fileName: {
      required,
    },
    file: {
      required,
    },
    dateUpload: {},
    note: {},
    createdBy: {},
    updatedBy: {},
    patient: {
      required,
    },
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

  patientId: number;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.fileId) {
        vm.retrieveFile(to.params.fileId);
      }
      if (to.params.patientId) {
        vm.patientId = parseInt(to.params.patientId);
        let patient = new Patient();
        patient.id = vm.patientId;
        vm.file.patient = patient;
        console.log(vm.visit);
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

  public get username(): string {
    return this.$store.getters.account ? this.$store.getters.account.login : '';
  }

  public translate(key: string): string {
    return this.$t(key) as string;
  }

  public save(): void {
    this.isSaving = true;
    if (this.file.id) {
      this.file.updatedBy = this.username;
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
      this.file.updatedBy = this.username;
      this.file.createdBy = this.username;
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

  public getFileName(file: File, $event): void {
    console.log('iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii');
    console.log(file, $event);

    this.file.fileName = $event.target.files[0].name;
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
