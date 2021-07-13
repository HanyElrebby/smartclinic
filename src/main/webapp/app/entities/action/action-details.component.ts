import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAction } from '@/shared/model/action.model';
import ActionService from './action.service';

@Component
export default class ActionDetails extends Vue {
  @Inject('actionService') private actionService: () => ActionService;
  public action: IAction = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.actionId) {
        vm.retrieveAction(to.params.actionId);
      }
    });
  }

  public retrieveAction(actionId) {
    this.actionService()
      .find(actionId)
      .then(res => {
        this.action = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
