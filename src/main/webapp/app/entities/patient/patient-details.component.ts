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

@Component({})
export default class PatientDetails extends mixins(JhiDataUtils) {
  @Inject('patientService') private patientService: () => PatientService;
  public patient: IPatient = {};

  @Inject('visitService') private visitService: () => VisitService;

  @Inject('accountService') private accountService: () => AccountService;

  @Inject('fileService') private fileService: () => FileService;

  public visits: IVisit[] = [];

  visit: IVisit = null;

  files: IFile[] = [];

  beforeRouteEnter(to, from, next) {
    console.log('yyyyyyyyyyyyyyyyyyyyyyyyyy');

    next(vm => {
      if (to.params.patientId) {
        vm.retrievePatient(to.params.patientId);
        vm.retrieveVisits(to.params.patientId);
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

              this.$router.push({ name: 'PatientView', params: { patientId: this.visits[0].patient.id + '' } });
              this.retrievePatient(this.visits[0].patient.id);
              this.visit = this.visits[0];
            } else {
              console.log('ttttttttttttttttttttttttttttttttttttttt');

              if (patientId + '' !== '-1') {
                console.log('eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee');

                this.$router.push({ name: 'PatientView', params: { patientId: patientId + '' } });
                this.retrievePatient(patientId);
                let vis = this.visits.filter(v => v.patient.id + '' === patientId + '');
                if (vis.length > 0) {
                  this.visit = vis[0];
                }
              } else {
                console.log('vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv');

                this.$router.push({ name: 'PatientView', params: { patientId: this.visits[0].patient.id + '' } });
                this.retrievePatient(this.visits[0].patient.id);
                this.visit = this.visits[0];
              }
            }
          } else {
            console.log('22222222222222222222222222222222222222222222');

            this.$router.push({ name: 'PatientView', params: { patientId: '-1' } });
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

            this.$router.push({ name: 'PatientView', params: { patientId: this.visits[0].patient.id + '' } });
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
              this.$router.push({ name: 'PatientView', params: { patientId: this.visits[0].patient.id + '' } });
              this.retrievePatient(this.visits[0].patient.id);
              this.visit = this.visits[0];
            }
          } else {
            console.log('4444444444444444444444444444444444444444444444444444444');

            this.$router.push({ name: 'PatientView', params: { patientId: '-1' } });
            this.retrievePatient(-1);
            this.visit = null;
          }
        }
      }
    });
  }

  public retrievePatient(patientId) {
    this.patientService()
      .find(patientId)
      .then(res => {
        this.patient = res;
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
