import { Component, Vue, Inject } from 'vue-property-decorator';

import { IVisit } from '@/shared/model/visit.model';
import VisitService from './visit.service';

@Component
export default class VisitDetails extends Vue {
  @Inject('visitService') private visitService: () => VisitService;
  public visit: IVisit = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.visitId) {
        vm.retrieveVisit(to.params.visitId);
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

  public previousState() {
    this.$router.go(-1);
  }
}
