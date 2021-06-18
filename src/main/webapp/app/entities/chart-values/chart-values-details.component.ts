import { Component, Vue, Inject } from 'vue-property-decorator';

import { IChartValues } from '@/shared/model/chart-values.model';
import ChartValuesService from './chart-values.service';

@Component
export default class ChartValuesDetails extends Vue {
  @Inject('chartValuesService') private chartValuesService: () => ChartValuesService;
  public chartValues: IChartValues = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.chartValuesId) {
        vm.retrieveChartValues(to.params.chartValuesId);
      }
    });
  }

  public retrieveChartValues(chartValuesId) {
    this.chartValuesService()
      .find(chartValuesId)
      .then(res => {
        this.chartValues = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
