import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, maxLength } from 'vuelidate/lib/validators';

import ClinicService from '@/entities/clinic/clinic.service';
import { IClinic } from '@/shared/model/clinic.model';

import { IDoctor, Doctor } from '@/shared/model/doctor.model';
import DoctorService from './doctor.service';

const validations: any = {
  doctor: {
    name: {
      required,
      maxLength: maxLength(30),
    },
    specialization: {
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
export default class DoctorUpdate extends Vue {
  @Inject('doctorService') private doctorService: () => DoctorService;
  public doctor: IDoctor = new Doctor();

  @Inject('clinicService') private clinicService: () => ClinicService;

  public clinics: IClinic[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.doctorId) {
        vm.retrieveDoctor(to.params.doctorId);
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
    if (this.doctor.id) {
      this.doctor.createdBy = this.username;
      this.doctor.updatedBy = this.username;
      this.doctorService()
        .update(this.doctor)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Doctor is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.doctor.updatedBy = this.username;
      this.doctorService()
        .create(this.doctor)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Doctor is created with identifier ' + param.id;
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

  public retrieveDoctor(doctorId): void {
    this.doctorService()
      .find(doctorId)
      .then(res => {
        this.doctor = res;
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
      });
  }
}
