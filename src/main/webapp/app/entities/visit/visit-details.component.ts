import { Component, Vue, Inject } from 'vue-property-decorator';

import { IVisit } from '@/shared/model/visit.model';
import VisitService from './visit.service';
import { EventBus } from '@/event-bus';
import AccountService from '@/account/account.service';

@Component
export default class VisitDetails extends Vue {
  @Inject('visitService') private visitService: () => VisitService;
  @Inject('accountService') private accountService: () => AccountService;

  public visit: IVisit = {};

  currentVisitId = null;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.visitId) {
        vm.retrieveVisit(to.params.visitId);
      }
      if (to.params.currentVisitId) {
        vm.currentVisitId = to.params.currentVisitId;
      }
    });
  }

  public retrieveVisit(visitId) {
    this.visitService()
      .find(visitId)
      .then(res => {
        this.visit = res;
      });
  }

  public checkAction(actionName: string) {
    return this.accountService().userActions.filter(c => c === actionName).length > 0;
  }

  public previousState() {
    this.$router.go(-1);
  }
}
