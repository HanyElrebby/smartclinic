import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, maxLength } from 'vuelidate/lib/validators';

import VisitService from '@/entities/visit/visit.service';
import { IVisit } from '@/shared/model/visit.model';

import { IPatient, Patient } from '@/shared/model/patient.model';
import PatientService from './patient.service';

const validations: any = {
  patient: {
    name: {
      required,
      maxLength: maxLength(30),
    },
    age: {
      required,
      maxLength: maxLength(3),
    },
    gender: {
      required,
      maxLength: maxLength(6),
    },
    contactNumber: {
      required,
      maxLength: maxLength(11),
    },
    fileNumber: {},
    placeOfResidence: {
      required,
      maxLength: maxLength(30),
    },
    dateOfBirth: {
      required,
    },
    bloodGroup: {
      required,
      maxLength: maxLength(30),
    },
    phoneNumber: {
      required,
      maxLength: maxLength(11),
    },
  },
};

@Component({
  validations,
})
export default class PatientUpdate extends Vue {
  @Inject('patientService') private patientService: () => PatientService;
  public patient: IPatient = new Patient();

  @Inject('visitService') private visitService: () => VisitService;

  public visits: IVisit[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.patientId) {
        vm.retrievePatient(to.params.patientId);
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
    if (this.patient.id) {
      this.patient.createdBy = this.username;
      this.patient.updatedBy = this.username;
      this.patientService()
        .update(this.patient)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Patient is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.patient.updatedBy = this.username;
      this.patientService()
        .create(this.patient)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Patient is created with identifier ' + param.id;
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

  public retrievePatient(patientId): void {
    this.patientService()
      .find(patientId)
      .then(res => {
        this.patient = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.visitService()
      .retrieve()
      .then(res => {
        this.visits = res.data;
      });
  }
}
