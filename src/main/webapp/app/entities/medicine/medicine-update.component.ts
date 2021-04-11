import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMedicine, Medicine } from '@/shared/model/medicine.model';
import MedicineService from './medicine.service';
import { required, maxLength } from 'vuelidate/lib/validators';

const validations: any = {
  medicine: {
    name: {
      required,
    },
    quantity: {
      required,
    },
    notes: {},
  },
};

@Component({
  validations,
})
export default class MedicineUpdate extends Vue {
  @Inject('medicineService') private medicineService: () => MedicineService;
  public medicine: IMedicine = new Medicine();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.medicineId) {
        vm.retrieveMedicine(to.params.medicineId);
      }
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
    if (this.medicine.id) {
      this.medicine.updatedBy = this.username;
      this.medicineService()
        .update(this.medicine)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Medicine is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.medicine.createdBy = this.username;
      this.medicine.updatedBy = this.username;
      this.medicineService()
        .create(this.medicine)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Medicine is created with identifier ' + param.id;
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

  public retrieveMedicine(medicineId): void {
    this.medicineService()
      .find(medicineId)
      .then(res => {
        this.medicine = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
