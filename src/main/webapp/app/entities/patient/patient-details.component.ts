import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPatient } from '@/shared/model/patient.model';
import PatientService from './patient.service';

@Component
export default class PatientDetails extends Vue {
  @Inject('patientService') private patientService: () => PatientService;
  public patient: IPatient = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.patientId) {
        vm.retrievePatient(to.params.patientId);
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

  public previousState() {
    this.$router.go(-1);
  }
}
