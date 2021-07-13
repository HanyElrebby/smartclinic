import { Component, Vue, Inject } from 'vue-property-decorator';

import { ISafe } from '@/shared/model/safe.model';
import SafeService from './safe.service';

@Component
export default class SafeDetails extends Vue {
  @Inject('safeService') private safeService: () => SafeService;
  public safe: ISafe = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.safeId) {
        vm.retrieveSafe(to.params.safeId);
      }
    });
  }

  public retrieveSafe(safeId) {
    this.safeService()
      .find(safeId)
      .then(res => {
        this.safe = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
