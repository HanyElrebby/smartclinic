import { Component, Vue, Inject } from 'vue-property-decorator';

import { IClinic } from '@/shared/model/clinic.model';
import ClinicService from './clinic.service';

@Component
export default class ClinicDetails extends Vue {
  @Inject('clinicService') private clinicService: () => ClinicService;
  public clinic: IClinic = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.clinicId) {
        vm.retrieveClinic(to.params.clinicId);
      }
    });
  }

  public retrieveClinic(clinicId) {
    this.clinicService()
      .find(clinicId)
      .then(res => {
        this.clinic = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
