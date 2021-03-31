import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, maxLength } from 'vuelidate/lib/validators';

import DoctorService from '@/entities/doctor/doctor.service';
import { IDoctor } from '@/shared/model/doctor.model';

import VisitService from '@/entities/visit/visit.service';
import { IVisit } from '@/shared/model/visit.model';

import UserService from '@/admin/user-management/user-management.service';

import { IClinic, Clinic } from '@/shared/model/clinic.model';
import ClinicService from './clinic.service';

const validations: any = {
  clinic: {
    nameOfClinic: {
      required,
      maxLength: maxLength(30),
    },
    city: {
      required,
      maxLength: maxLength(30),
    },
    postalCode: {
      required,
      maxLength: maxLength(30),
    },
    street: {
      required,
      maxLength: maxLength(30),
    },
  },
};

@Component({
  validations,
})
export default class ClinicUpdate extends Vue {
  @Inject('clinicService') private clinicService: () => ClinicService;
  public clinic: IClinic = new Clinic();

  @Inject('doctorService') private doctorService: () => DoctorService;

  public doctors: IDoctor[] = [];

  @Inject('visitService') private visitService: () => VisitService;

  public visits: IVisit[] = [];

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.clinicId) {
        vm.retrieveClinic(to.params.clinicId);
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
    if (this.clinic.id) {
      this.clinicService()
        .update(this.clinic)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Clinic is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.clinicService()
        .create(this.clinic)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Clinic is created with identifier ' + param.id;
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

  public retrieveClinic(clinicId): void {
    this.clinicService()
      .find(clinicId)
      .then(res => {
        this.clinic = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.doctorService()
      .retrieve()
      .then(res => {
        this.doctors = res.data;
      });
    this.visitService()
      .retrieve()
      .then(res => {
        this.visits = res.data;
      });
    this.userService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
  }
}
