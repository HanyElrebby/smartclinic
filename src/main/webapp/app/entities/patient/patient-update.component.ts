import { Component, Vue, Inject, VModel } from 'vue-property-decorator';

import { required, maxLength } from 'vuelidate/lib/validators';

import VisitService from '@/entities/visit/visit.service';
import { IVisit } from '@/shared/model/visit.model';

import { IPatient, Patient } from '@/shared/model/patient.model';
import PatientService from './patient.service';
import { Datetime } from 'vue-datetime';
import { faVolumeMute } from '@fortawesome/free-solid-svg-icons';

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
    fileNumber: {
      required,
    },
    placeOfResidence: {
      required,
      maxLength: maxLength(30),
    },
    bloodGroup: {
      required,
      maxLength: maxLength(30),
    },
    phoneNumber: {
      required,
      maxLength: maxLength(11),
    },
    diseases: {
      maxLength: maxLength(1000),
    },
    surgery: {
      maxLength: maxLength(1000),
    },
  },
  value1: {
    required,
  },
};

@Component({
  validations,
  components: { datetime: Datetime },
  data() {
    return {
      value1: null,
    };
  },
})
export default class PatientUpdate extends Vue {
  @Inject('patientService') private patientService: () => PatientService;
  public patient: IPatient = new Patient();

  @Inject('visitService') private visitService: () => VisitService;

  public visits: IVisit[] = [];
  public isSaving = false;
  public currentLanguage = '';

  public lastPatients = [];

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.patientId) {
        vm.retrievePatient(to.params.patientId);
      }
      vm.patient.fileNumber = '1';
      vm.patient.bloodGroup = '-O';
      vm.patient.gender = 'Male';

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

  public closeDialogMedicine(): void {
    (<any>this.$refs.addMedicines).hide();
  }

  public save(): void {
    this.isSaving = true;
    this.patient.dateOfBirth = new Date(this.$data.value1);
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
        this.$data.value1 = new Date(this.patient.dateOfBirth).toISOString();
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
    this.patientService()
      .gatLastPatient()
      .then(res => {
        this.lastPatients = res.data;
        if (this.lastPatients != null && this.lastPatients != undefined && this.lastPatients.length > 0) {
          this.patient.fileNumber = parseInt(this.lastPatients[0].fileNumber) + 1 + '';
        }
      });
  }
}
