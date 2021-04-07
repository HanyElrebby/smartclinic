import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMedicine } from '@/shared/model/medicine.model';
import MedicineService from './medicine.service';

@Component
export default class MedicineDetails extends Vue {
  @Inject('medicineService') private medicineService: () => MedicineService;
  public medicine: IMedicine = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.medicineId) {
        vm.retrieveMedicine(to.params.medicineId);
      }
    });
  }

  public retrieveMedicine(medicineId) {
    this.medicineService()
      .find(medicineId)
      .then(res => {
        this.medicine = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
