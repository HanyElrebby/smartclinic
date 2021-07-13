import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAction } from '@/shared/model/action.model';

import ActionService from './action.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Action extends Vue {
  @Inject('actionService') private actionService: () => ActionService;
  private removeId: number = null;

  public actions: IAction[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllActions();
  }

  public clear(): void {
    this.retrieveAllActions();
  }

  public retrieveAllActions(): void {
    this.isFetching = true;

    this.actionService()
      .retrieve()
      .then(
        res => {
          this.actions = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IAction): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeAction(): void {
    this.actionService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A Action is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllActions();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
