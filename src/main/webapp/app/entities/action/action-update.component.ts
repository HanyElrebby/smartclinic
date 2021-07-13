import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAction, Action } from '@/shared/model/action.model';
import ActionService from './action.service';

const validations: any = {
  action: {
    name: {},
  },
};

@Component({
  validations,
})
export default class ActionUpdate extends Vue {
  @Inject('actionService') private actionService: () => ActionService;
  public action: IAction = new Action();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.actionId) {
        vm.retrieveAction(to.params.actionId);
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
    if (this.action.id) {
      this.actionService()
        .update(this.action)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Action is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.actionService()
        .create(this.action)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Action is created with identifier ' + param.id;
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

  public retrieveAction(actionId): void {
    this.actionService()
      .find(actionId)
      .then(res => {
        this.action = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
