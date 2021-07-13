import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, decimal, minValue } from 'vuelidate/lib/validators';

import { ISafe, Safe } from '@/shared/model/safe.model';
import SafeService from './safe.service';

const validations: any = {
  safe: {
    safeName: {
      required,
    },
    safeValue: {
      required,
      decimal,
      min: minValue(0),
    },
    notes: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class SafeUpdate extends Vue {
  @Inject('safeService') private safeService: () => SafeService;
  public safe: ISafe = new Safe();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.safeId) {
        vm.retrieveSafe(to.params.safeId);
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

  public save(): void {
    this.isSaving = true;
    if (this.safe.id) {
      this.safe.safeSecretary = this.username;
      this.safeService()
        .update(this.safe)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Safe is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.safe.safeSecretary = this.username;
      this.safeService()
        .create(this.safe)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Safe is created with identifier ' + param.id;
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

  public retrieveSafe(safeId): void {
    this.safeService()
      .find(safeId)
      .then(res => {
        this.safe = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}

  public get username(): string {
    return this.$store.getters.account ? this.$store.getters.account.login : '';
  }
}
