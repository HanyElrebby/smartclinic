import { Component, Vue, Inject, VModel } from 'vue-property-decorator';

import { required, maxLength } from 'vuelidate/lib/validators';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT, DATE_TIME_FORMAT } from '@/shared/date/filters';

import ClinicService from '@/entities/clinic/clinic.service';
import { IClinic } from '@/shared/model/clinic.model';

import PatientService from '@/entities/patient/patient.service';
import { IPatient, Patient } from '@/shared/model/patient.model';

import DetailsOfVisitService from '@/entities/details-of-visit/details-of-visit.service';
import { IDetailsOfVisit } from '@/shared/model/details-of-visit.model';

import { IVisit, Visit } from '@/shared/model/visit.model';
import VisitService from './visit.service';

import DatePicker from 'vue2-datepicker';
import 'vue2-datepicker/index.css';

import { Datetime } from 'vue-datetime';
// You need a specific loader for CSS files
import 'vue-datetime/dist/vue-datetime.css';
import TrackerService from '@/core/SidebarPlugin/tracker.service';
import { EventBus } from '@/event-bus';
import AccountService from '@/account/account.service';

const validations: any = {
  visit: {
    visitType: {
      required,
      maxLength: maxLength(30),
    },
    cost: {
      required,
      maxLength: maxLength(7),
    },
    status: {
      required,
    },
    description: { maxLength: maxLength(1000) },
    medicine: { maxLength: maxLength(1000) },
    note: { maxLength: maxLength(1000) },
    clinic: {
      required,
    },
    patient: {
      required,
    },
  },
  value1: {
    required,
  },
  hour: {
    required,
  },
  minute: {
    required,
  },
  period: {
    required,
  },
};

@Component({
  validations,
  components: { datetime: Datetime },
  data() {
    return {
      value1: new Date().toISOString(),
      hour: new Date().getHours() > 12 ? new Date().getHours() % 12 : new Date().getHours(),
      minute: new Date().getMinutes(),
      period: new Date().getHours() >= 12 ? 'pm' : 'am',
    };
  },
})
export default class VisitUpdate extends Vue {
  @Inject('visitService') private visitService: () => VisitService;
  public visit: IVisit = new Visit();

  @Inject('trackerService') private trackerService: () => TrackerService;

  @Inject('clinicService') private clinicService: () => ClinicService;

  public clinics: IClinic[] = [];

  @Inject('patientService') private patientService: () => PatientService;

  public patients: IPatient[] = [];

  @Inject('detailsOfVisitService') private detailsOfVisitService: () => DetailsOfVisitService;

  @Inject('accountService') private accountService: () => AccountService;

  public detailsOfVisits: IDetailsOfVisit[] = [];
  public isSaving = false;
  public currentLanguage = '';

  public patientId;

  public value: string = '2018-05-12T17:19:06.151Z';

  visits: IVisit[] = [];

  hasAnyAuthorityValue = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.visitId) {
        vm.retrieveVisit(to.params.visitId);
      } else {
        //vm.value = dayjs(new Date()).format(DATE_TIME_LONG_FORMAT);
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

  mounted(): void {
    EventBus.$on('waitedVisits', waitedVisits => {
      this.visits = waitedVisits;
    });
  }

  public hasAnyAuthority(authorities: any): boolean {
    this.accountService()
      .hasAnyAuthorityAndCheckAuth(authorities)
      .then(value => {
        this.hasAnyAuthorityValue = value;
      });
    return this.hasAnyAuthorityValue;
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
    console.log(this.$data.value1, 'valueeeeeeeeeeee');
    console.log(this.$data.hour);
    console.log(this.$data.minute);
    console.log(this.$data.period);

    let hourv = '';

    if (this.$data.period === 'pm') {
      if (this.$data.hour < 12) {
        hourv = this.$data.hour + 12 + '';
      } else {
        hourv = this.$data.hour + '';
      }
    } else {
      hourv = (this.$data.hour % 12) + '';
    }

    let minuteV = this.$data.minute + '';
    if (hourv.length < 2) {
      hourv = '0' + hourv;
      if (hourv.length < 2) {
        hourv = '0' + hourv;
      }
    }
    if (minuteV.length < 2) {
      minuteV = '0' + minuteV;
      if (minuteV.length < 2) {
        minuteV = '0' + minuteV;
      }
    }

    let dateFor = this.$data.value1.substring(0, this.$data.value1.indexOf('T') + 1) + hourv + ':' + minuteV + ':00';

    let dated = new Date(this.$data.value1.indexOf('T'));

    console.log(dateFor, 'ddddddddddddddddddddddddddddddd');

    this.visit.dateOfVisit = new Date(dateFor);
    console.log(this.visit.dateOfVisit);

    if (this.visit.id) {
      this.visit.createdBy = this.username;
      this.visit.updatedBy = this.username;
      this.visitService()
        .update(this.visit)
        .then(param => {
          this.isSaving = false;

          if (this.visit.status === 'Served' && this.accountService().hasAnyAuthorityAndCheckAuth('ROLE_ADMIN')) {
            if (this.visits.length > 1) {
              this.$router.push({ name: 'PatientView', params: { patientId: this.visits[1].patient.id + '' } });
            } else {
              this.$router.push({ name: 'PatientView', params: { patientId: '-1' } });
            }
          } else {
            this.$router.go(-1);
          }

          const message = 'A Visit is updated with identifier ' + param.id;
          console.log('sucesss ------------>');
          this.trackerService().sendActivity();

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
          if (this.visit.status === 'Served' && this.accountService().hasAnyAuthorityAndCheckAuth('ROLE_ADMIN')) {
            if (this.visits.length > 1) {
              this.$router.push({ name: 'PatientView', params: { patientId: this.visits[1].patient.id + '' } });
            } else {
              this.$router.push({ name: 'PatientView', params: { patientId: '-1' } });
            }
          } else {
            this.$router.go(-1);
          }
          console.log('sucesss ------------>');

          this.trackerService().sendActivity();
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
      return dayjs(date).format(DATE_TIME_FORMAT);
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
    console.log('fffffffffffffffffffffffffffffff', event);

    if (event.target.value) {
      this.visit[field] = dayjs(event.target.value, DATE_TIME_FORMAT);
    } else {
      this.visit[field] = null;
    }
  }

  public retrieveVisit(visitId): void {
    this.visitService()
      .find(visitId)
      .then(res => {
        console.log(this.$data.value1, '4444444444444444444555555555555555555555');

        this.$data.value1 = dayjs(new Date(res.dateOfVisit)).format(DATE_TIME_LONG_FORMAT);
        this.$data.hour =
          new Date(res.dateOfVisit).getHours() > 12 ? new Date(res.dateOfVisit).getHours() % 12 : new Date(res.dateOfVisit).getHours();
        this.$data.minute = new Date(res.dateOfVisit).getMinutes();
        this.$data.period = new Date(res.dateOfVisit).getHours() >= 12 ? 'pm' : 'am';
        console.log(this.$data.value1, '77777777777777777777777777777777777');
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
