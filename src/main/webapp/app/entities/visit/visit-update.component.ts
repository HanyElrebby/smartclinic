import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, maxLength } from 'vuelidate/lib/validators';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import ClinicService from '@/entities/clinic/clinic.service';
import { IClinic } from '@/shared/model/clinic.model';

import PatientService from '@/entities/patient/patient.service';
import { IPatient, Patient } from '@/shared/model/patient.model';

import DetailsOfVisitService from '@/entities/details-of-visit/details-of-visit.service';
import { IDetailsOfVisit } from '@/shared/model/details-of-visit.model';

import { IVisit, Visit } from '@/shared/model/visit.model';
import VisitService from './visit.service';

const validations: any = {
  visit: {
    dateOfVisit: {
      required,
    },
    visitType: {
      required,
      maxLength: maxLength(30),
    },
    cost: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class VisitUpdate extends Vue {
  @Inject('visitService') private visitService: () => VisitService;
  public visit: IVisit = new Visit();

  @Inject('clinicService') private clinicService: () => ClinicService;

  public clinics: IClinic[] = [];

  @Inject('patientService') private patientService: () => PatientService;

  public patients: IPatient[] = [];

  @Inject('detailsOfVisitService') private detailsOfVisitService: () => DetailsOfVisitService;

  public detailsOfVisits: IDetailsOfVisit[] = [];
  public isSaving = false;
  public currentLanguage = '';

  public patientId;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.visitId) {
        vm.retrieveVisit(to.params.visitId);
      } else {
        vm.visit.dateOfVisit = new Date();
      }
      if (to.params.patientId) {
        vm.patientId = parseInt(to.params.patientId);
        let patient = new Patient();
        patient.id = vm.patientId;
        vm.visit.patient = patient;
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

  public save(): void {
    this.isSaving = true;
    if (this.visit.id) {
      this.visit.createdBy = this.username;
      this.visit.updatedBy = this.username;
      this.visitService()
        .update(this.visit)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Visit is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.visit.updatedBy = this.username;
      this.visitService()
        .create(this.visit)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Visit is created with identifier ' + param.id;
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
      this.visit[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.visit[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.visit[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.visit[field] = null;
    }
  }

  public retrieveVisit(visitId): void {
    this.visitService()
      .find(visitId)
      .then(res => {
        res.dateOfVisit = new Date(res.dateOfVisit);
        this.visit = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.clinicService()
      .retrieve()
      .then(res => {
        this.clinics = res.data;
        if (this.clinics && this.clinics.length > 0) {
          this.visit.clinic = this.clinics[0];
        }
      });
    this.patientService()
      .retrieve()
      .then(res => {
        this.patients = res.data;
      });
    this.detailsOfVisitService()
      .retrieve()
      .then(res => {
        this.detailsOfVisits = res.data;
      });
  }
}
