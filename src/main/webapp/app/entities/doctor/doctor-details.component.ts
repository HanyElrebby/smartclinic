import { Component, Vue, Inject } from 'vue-property-decorator';

import { IDoctor } from '@/shared/model/doctor.model';
import DoctorService from './doctor.service';

@Component
export default class DoctorDetails extends Vue {
  @Inject('doctorService') private doctorService: () => DoctorService;
  public doctor: IDoctor = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.doctorId) {
        vm.retrieveDoctor(to.params.doctorId);
      }
    });
  }

  public retrieveDoctor(doctorId) {
    this.doctorService()
      .find(doctorId)
      .then(res => {
        this.doctor = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
