import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPatient } from '@/shared/model/patient.model';
import PatientService from './patient.service';
import VisitService from '../visit/visit.service';
import { IVisit } from '@/shared/model/visit.model';

@Component
export default class PatientDetails extends Vue {
  @Inject('patientService') private patientService: () => PatientService;
  public patient: IPatient = {};

  @Inject('visitService') private visitService: () => VisitService;

  public visits: IVisit[] = [];

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.patientId) {
        vm.retrievePatient(to.params.patientId);
        //vm.retrieveVisits(to.params.patientId);
      }
    });
  }

  public retrievePatient(patientId) {
    this.patientService()
      .find(patientId)
      .then(res => {
        this.patient = res;
      });
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
