import { Component, Inject, Vue } from 'vue-property-decorator';
import { mixins } from 'vue-class-component';
import Vue2Filters from 'vue2-filters';
import AccountService from '@/account/account.service';
import ChartValuesService from '@/entities/chart-values/chart-values.service';

@Component({
  data() {
    return {
      chartOptions: {
        chart: {
          type: 'spline',
        },
        title: {
          text: 'منحنى تطور الطول',
        },
        subtitle: {
          text: 'العمر',
        },
        xAxis: {
          labels: {
            formatter: function () {
              return this.value;
            },
          },
        },
        yAxis: {
          title: {
            text: 'الطول',
          },

          labels: {
            formatter: function () {
              return this.value;
            },
          },
        },
        tooltip: {
          crosshairs: true,
          shared: true,
        },
        plotOptions: {
          spline: {
            marker: {
              radius: 4,
              lineColor: '#666666',
              lineWidth: 1,
            },
          },
        },
        series: [
          {
            name: 'الحد الاقصى',
            marker: {
              symbol: 'square',
            },
            color: '#A42105',
            data: [52.5, 80, 92.5, 102.5],
          },

          {
            name: 'الحد الادنى',
            marker: {
              symbol: 'diamond',
            },
            color: '#FC2C00',
            data: [42.5, 70, 80, 87.5],
          },
        ],
      },
    };
  },
})
export default class Charts extends Vue {
  @Inject('accountService') private accountService: () => AccountService;

  @Inject('chartValuesService') private chartValuesService: () => ChartValuesService;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.retriveChartData();
    });
  }

  retriveChartData() {
    this.chartValuesService()
      .getForChart()
      .then(
        res => {
          console.log('fffffffffffffffffffffffffff');
          console.log(res.data);
          console.log('gggggggggggggggggggggggdddddddddd');
          if (res.data !== null && res.data !== undefined && res.data.length > 0) {
            res.data.forEach(element => {
              var obj = {
                name: element.name,
                marker: {
                  symbol: 'circle',
                },
                color: '#17FC00',
                data: element.data,
              };
              this.$data.chartOptions.series.push(obj);
              console.log(this.$data.chartOptions.series);
            });
          }
        },
        err => {}
      );
  }

  public get username(): string {
    return this.$store.getters.account ? this.$store.getters.account.login : '';
  }

  public navigateTo(path: string): void {
    this.$router.push(path, () => {});
  }
}
