import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPatient } from '@/shared/model/patient.model';
import PatientService from './patient.service';
import VisitService from '../visit/visit.service';
import { IVisit } from '@/shared/model/visit.model';
import { EventBus } from '@/event-bus';
import AccountService from '@/account/account.service';
import FileService from '../file/file.service';
import { IFile } from '@/shared/model/file.model';
import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';
import { ChartValues, IChartValues } from '@/shared/model/chart-values.model';
import ChartValuesService from '../chart-values/chart-values.service';
import { required, maxLength } from 'vuelidate/lib/validators';

const validations: any = {
  chartValues: {
    age: { required },
    length: { required },
    weight: { required },
  },
};

@Component({
  validations,
  data() {
    return {
      chartLengthOptions: {
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
            data: [
              [0, 52.5],
              [1, 80],
              [2, 92.5],
              [3, 102.5],
            ],
          },

          {
            name: 'الحد الادنى',
            marker: {
              symbol: 'diamond',
            },
            color: '#FC2C00',
            data: [
              [0, 42.5],
              [1, 70],
              [2, 80],
              [3, 87.5],
            ],
          },
        ],
      },
      chartWeightOptions: {
        chart: {
          type: 'spline',
        },
        title: {
          text: 'منحنى تطور الوزن',
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
            text: 'الوزن',
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
            data: [
              [0, 5],
              [1, 15],
              [2, 25],
              [3, 35],
            ],
          },

          {
            name: 'الحد الادنى',
            marker: {
              symbol: 'diamond',
            },
            color: '#FC2C00',
            data: [
              [0, 3.5],
              [1, 11],
              [2, 17],
              [3, 25],
            ],
          },
        ],
      },
    };
  },
})
export default class PatientDetails extends mixins(JhiDataUtils) {
  @Inject('patientService') private patientService: () => PatientService;
  public patient: IPatient = {};

  @Inject('visitService') private visitService: () => VisitService;

  @Inject('accountService') private accountService: () => AccountService;

  @Inject('fileService') private fileService: () => FileService;

  @Inject('chartValuesService') private chartValuesService: () => ChartValuesService;
  public chartValues: IChartValues = new ChartValues();
  public isSaving = false;

  public visits: IVisit[] = [];

  visit: IVisit = null;

  files: IFile[] = [];
  lengths: IChartValues[] = [];

  femaleLengthSeries = [
    {
      name: 'الحد الاقصى',
      marker: {
        symbol: 'square',
      },
      color: '#A42105',
      data: [
        [0, 52.5],
        [1 / 12, 57.4],
        [2 / 12, 60.9],
        [3 / 12, 63.8],
        [4 / 12, 66.2],
        [5 / 12, 68.2],
        [6 / 12, 70],
        [7 / 12, 71.6],
        [8 / 12, 73.2],
        [9 / 12, 74.7],
        [10 / 12, 76.1],
        [11 / 12, 77.5],
        [1, 78.9],
      ],
    },

    {
      name: 'الحد الادنى',
      marker: {
        symbol: 'diamond',
      },
      color: '#FC2C00',
      data: [
        [0, 46.5],
        [1 / 12, 50],
        [2 / 12, 53.2],
        [3 / 12, 55.8],
        [4 / 12, 58],
        [5 / 12, 59.9],
        [6 / 12, 61.5],
        [7 / 12, 62.9],
        [8 / 12, 64.3],
        [9 / 12, 65.6],
        [10 / 12, 66.8],
        [11 / 12, 68],
        [1, 69.2],
      ],
    },
  ];

  maleLengthSeries = [
    {
      name: 'الحد الاقصى',
      marker: {
        symbol: 'square',
      },
      color: '#A42105',
      data: [
        [0, 53.4],
        [1 / 12, 58.4],
        [2 / 12, 62.2],
        [3 / 12, 65.3],
        [4 / 12, 67.8],
        [5 / 12, 69.9],
        [6 / 12, 71.6],
        [7 / 12, 73.2],
        [8 / 12, 74.4],
        [9 / 12, 76.2],
        [10 / 12, 77.6],
        [11 / 12, 78.9],
        [1, 80.2],
      ],
    },
    {
      name: 'الحد الادنى',
      marker: {
        symbol: 'diamond',
      },
      color: '#FC2C00',
      data: [
        [0, 46.5],
        [1 / 12, 50],
        [2 / 12, 53.2],
        [3 / 12, 55.8],
        [4 / 12, 58],
        [5 / 12, 59.9],
        [6 / 12, 61.5],
        [7 / 12, 62.9],
        [8 / 12, 64.3],
        [9 / 12, 65.6],
        [10 / 12, 66.8],
        [11 / 12, 68],
        [1, 69.2],
      ],
    },
  ];

  femaleWeightSeries = [
    {
      name: 'الحد الاقصى',
      marker: {
        symbol: 'square',
      },
      color: '#A42105',
      data: [
        [0, 4.2],
        [1 / 12, 5.4],
        [2 / 12, 6.5],
        [3 / 12, 7.4],
        [4 / 12, 8.1],
        [5 / 12, 8.7],
        [6 / 12, 9.2],
        [7 / 12, 9.6],
        [8 / 12, 10.4],
        [9 / 12, 10.4],
        [10 / 12, 10.7],
        [11 / 12, 11],
        [1, 12.3],
      ],
    },

    {
      name: 'الحد الادنى',
      marker: {
        symbol: 'diamond',
      },
      color: '#FC2C00',
      data: [
        [0, 2.4],
        [1 / 12, 3.2],
        [2 / 12, 4],
        [3 / 12, 4.6],
        [4 / 12, 5.1],
        [5 / 12, 5.5],
        [6 / 12, 5.8],
        [7 / 12, 6.1],
        [8 / 12, 6.6],
        [9 / 12, 6.6],
        [10 / 12, 6.8],
        [11 / 12, 8.6],
        [1, 10.1],
      ],
    },
  ];

  maleWeightSeries = [
    {
      name: 'الحد الاقصى',
      marker: {
        symbol: 'square',
      },
      color: '#A42105',
      data: [
        [0, 4.3],
        [1 / 12, 5.7],
        [2 / 12, 7],
        [3 / 12, 7.9],
        [4 / 12, 8.6],
        [5 / 12, 9.2],
        [6 / 12, 9.7],
        [7 / 12, 10.2],
        [8 / 12, 10.5],
        [9 / 12, 10.9],
        [10 / 12, 11.2],
        [11 / 12, 11.5],
        [1, 11.8],
      ],
    },
    {
      name: 'الحد الادنى',
      marker: {
        symbol: 'diamond',
      },
      color: '#FC2C00',
      data: [
        [0, 2.5],
        [1 / 12, 3.4],
        [2 / 12, 4.4],
        [3 / 12, 5.1],
        [4 / 12, 5.6],
        [5 / 12, 6.1],
        [6 / 12, 6.4],
        [7 / 12, 6.7],
        [8 / 12, 7],
        [9 / 12, 7.2],
        [10 / 12, 7.5],
        [11 / 12, 8.4],
        [1, 8.7],
      ],
    },
  ];

  beforeRouteEnter(to, from, next) {
    console.log('yyyyyyyyyyyyyyyyyyyyyyyyyy');

    next(vm => {
      if (to.params.patientId) {
        EventBus.$emit('patientId', to.params.patientId);

        vm.retrievePatient(to.params.patientId);
      }
    });
  }

  retriveFiles(patientId) {
    this.fileService()
      .retrieveByPatientId(patientId)
      .then(
        res => {
          this.files = res.data;
        },
        err => {}
      );
  }

  retriveChartData(patientId) {
    this.chartValuesService()
      .getForChartPyPatientId(patientId)
      .then(
        res => {
          console.log('fffffffffffffffffffffffffff');
          console.log(res.data);
          console.log('gggggggggggggggggggggggdddddddddd');
          if (res.data !== null && res.data !== undefined) {
            let seriesLength = [];

            if (this.patient.gender === 'Male') {
              seriesLength = this.maleLengthSeries.concat([]);
            } else {
              seriesLength = this.femaleLengthSeries.concat([]);
            }

            let objl = {
              name: res.data.name,
              marker: {
                symbol: 'diamond',
              },
              color: '#17FC00',
              data: res.data.lengthData,
            };
            seriesLength.push(objl);

            console.log(seriesLength, this.maleLengthSeries, this.femaleLengthSeries);

            let seriesWeight = [];

            if (this.patient.gender === 'Male') {
              seriesWeight = this.maleWeightSeries.concat([]);
            } else {
              seriesWeight = this.femaleWeightSeries.concat([]);
            }

            let objW = {
              name: res.data.name,
              marker: {
                symbol: 'diamond',
              },
              color: '#17FC00',
              data: res.data.weightData,
            };

            seriesWeight.push(objW);

            this.$data.chartWeightOptions.series = seriesWeight;
            this.$data.chartLengthOptions.series = seriesLength;
          }
        },
        err => {}
      );
  }

  formatDate(dateString: string) {
    let date = new Date(dateString);
    let months = ['يناير', 'فبراير', 'مارس', 'إبريل', 'مايو', 'يونيو', 'يوليو', 'أغسطس', 'سبتمبر', 'أكتوبر', 'نوفمبر', 'ديسمبر'];
    var days = ['اﻷحد', 'اﻷثنين', 'الثلاثاء', 'اﻷربعاء', 'الخميس', 'الجمعة', 'السبت'];

    let ha = 'ص';
    let hourNumber = date.getHours();
    if (hourNumber > 12) {
      ha = 'م';
      hourNumber = hourNumber % 12;
    }
    if (hourNumber === 12) {
      ha = 'م';
    }

    let hour = hourNumber + '';

    if (hour.length < 2) {
      hour = '0' + hour;
    }
    let minute = date.getMinutes() + '';
    if (minute.length < 2) {
      minute = '0' + minute;
    }

    var delDateString =
      days[date.getDay()] +
      ', ' +
      date.getDate() +
      ' ' +
      months[date.getMonth()] +
      ', ' +
      date.getFullYear() +
      ' ' +
      hour +
      ':' +
      minute +
      ' ' +
      ha;

    return delDateString;
  }

  mounted(): void {
    EventBus.$on('waitedVisits', waitedVisits => {
      let lastVisits = this.visits;
      this.visits = waitedVisits;
      if (
        this.$route.fullPath.includes('patient') &&
        this.$route.fullPath.includes('view') &&
        this.accountService().hasAnyAuthorityAndCheckAuth('ROLE_ADMIN')
      ) {
        let patientId = this.$route.params.patientId;
        console.log(patientId, 'patient id ----->');
        if (lastVisits !== null && lastVisits !== undefined && lastVisits.length > 0) {
          console.log('25252525225252525525252252252225');

          if (this.visits !== null && this.visits !== undefined && this.visits.length > 0) {
            console.log('8585858585858585885858585858585858585858858');

            console.log(this.visits, lastVisits);

            console.log(patientId + '', lastVisits[0].patient.id + '');

            console.log(this.$router.getRoutes(), 'routessssssss');

            if (this.visits[0].patient.id !== lastVisits[0].patient.id && patientId + '' === lastVisits[0].patient.id + '') {
              console.log('111111111111111111111111111111111111111111111111111');

              this.$router.push({ name: 'PatientView', params: { patientId: this.visits[0].patient.id + '' } }).catch(() => {});
              this.retrievePatient(this.visits[0].patient.id);
              this.visit = this.visits[0];
            } else {
              console.log('ttttttttttttttttttttttttttttttttttttttt');

              if (patientId + '' !== '-1') {
                console.log('eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee');

                this.$router.push({ name: 'PatientView', params: { patientId: patientId + '' } }).catch(() => {});
                this.retrievePatient(patientId);
                let vis = this.visits.filter(v => v.patient.id + '' === patientId + '');
                if (vis.length > 0) {
                  this.visit = vis[0];
                }
              } else {
                console.log('vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv');

                this.$router.push({ name: 'PatientView', params: { patientId: this.visits[0].patient.id + '' } }).catch(() => {});
                this.retrievePatient(this.visits[0].patient.id);
                this.visit = this.visits[0];
              }
            }
          } else {
            console.log('22222222222222222222222222222222222222222222');

            this.$router.push({ name: 'PatientView', params: { patientId: '-1' } }).catch(() => {});
            this.retrievePatient(-1);
            this.visit = null;
          }
        } else {
          if (
            (this.visits !== null && this.visits !== undefined && this.visits.length > 0 && patientId + '' === '-1') ||
            patientId === undefined ||
            patientId === null
          ) {
            console.log('333333333333333333333333333333333333333333333333333333333');

            this.$router.push({ name: 'PatientView', params: { patientId: this.visits[0].patient.id + '' } }).catch(() => {});
            this.retrievePatient(this.visits[0].patient.id);
            this.visit = this.visits[0];
          } else if (patientId !== null && patientId !== undefined) {
            if (patientId + '' !== '-1') {
              this.$router.push({ name: 'PatientView', params: { patientId: patientId + '' } });
              this.retrievePatient(patientId);
              let vis = this.visits.filter(v => v.patient.id + '' === patientId + '');
              if (vis.length > 0) {
                this.visit = vis[0];
              }
            } else {
              this.$router.push({ name: 'PatientView', params: { patientId: this.visits[0].patient.id + '' } }).catch(() => {});
              this.retrievePatient(this.visits[0].patient.id);
              this.visit = this.visits[0];
            }
          } else {
            console.log('4444444444444444444444444444444444444444444444444444444');

            this.$router.push({ name: 'PatientView', params: { patientId: '-1' } }).catch(() => {});
            this.retrievePatient(-1);
            this.visit = null;
          }
        }
      }
    });
  }

  public closeDialogLength(): void {
    (<any>this.$refs.addLenght).hide();
  }

  public saveLength(): void {
    this.isSaving = true;
    this.chartValues.patient = this.patient;
    this.chartValues.type = 'LENGTH';
    if (this.chartValues.id) {
      this.chartValuesService()
        .update(this.chartValues)
        .then(param => {
          this.isSaving = false;
          this.retriveChartData(this.patient.id);
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
          this.retriveChartData(this.patient.id);
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

  public retrievePatient(patientId) {
    this.patientService()
      .find(patientId)
      .then(res => {
        this.patient = res;
        if (this.patient.gender === 'Male') {
          this.$data.chartLengthOptions.series = this.maleLengthSeries;
          this.$data.chartWeightOptions.series = this.maleWeightSeries;
        } else {
          this.$data.chartLengthOptions.series = this.femaleLengthSeries;
          this.$data.chartWeightOptions.series = this.femaleWeightSeries;
        }
        this.retriveChartData(patientId);
      });
    this.retriveFiles(patientId);
  }

  visitTypeVale(visitType) {
    if (visitType === 'Reveal') {
      return 'كشف';
    } else if (visitType === 'Repeat') {
      return 'أعادة';
    } else if (visitType === 'Other') {
      return 'أخرى';
    } else {
      return visitType;
    }
  }

  public retrieveVisits(patientId) {
    this.visitService()
      .retrieveByPatientId(patientId)
      .then(res => {
        this.visits = res.data;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
