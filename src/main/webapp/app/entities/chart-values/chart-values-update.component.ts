import { Component, Vue, Inject } from 'vue-property-decorator';

import { IChartValues, ChartValues } from '@/shared/model/chart-values.model';
import ChartValuesService from './chart-values.service';

const validations: any = {
  chartValues: {
    weight: {},
    length: {},
    type: {},
  },
};

@Component({
  validations,
})
export default class ChartValuesUpdate extends Vue {
  @Inject('chartValuesService') private chartValuesService: () => ChartValuesService;
  public chartValues: IChartValues = new ChartValues();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.chartValuesId) {
        vm.retrieveChartValues(to.params.chartValuesId);
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
    if (this.chartValues.id) {
      this.chartValuesService()
        .update(this.chartValues)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A ChartValues is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.chartValuesService()
        .create(this.chartValues)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A ChartValues is created with identifier ' + param.id;
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

  public retrieveChartValues(chartValuesId): void {
    this.chartValuesService()
      .find(chartValuesId)
      .then(res => {
        this.chartValues = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
