import { Component, Vue, Inject } from 'vue-property-decorator';

import { IDetailsOfVisit } from '@/shared/model/details-of-visit.model';
import DetailsOfVisitService from './details-of-visit.service';

@Component
export default class DetailsOfVisitDetails extends Vue {
  @Inject('detailsOfVisitService') private detailsOfVisitService: () => DetailsOfVisitService;
  public detailsOfVisit: IDetailsOfVisit = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.detailsOfVisitId) {
        vm.retrieveDetailsOfVisit(to.params.detailsOfVisitId);
      }
    });
  }

  public retrieveDetailsOfVisit(detailsOfVisitId) {
    this.detailsOfVisitService()
      .find(detailsOfVisitId)
      .then(res => {
        this.detailsOfVisit = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
