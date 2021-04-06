import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPatient } from '@/shared/model/patient.model';

import PatientService from './patient.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Patient extends Vue {
  @Inject('patientService') private patientService: () => PatientService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public patients: IPatient[] = [];

  public isFetching = false;

  public searchValue = '';

  public mounted(): void {
    this.retrieveAllPatients();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllPatients();
  }

  public retrieveAllPatients(): void {
    this.isFetching = true;

    console.log(this.searchValue, 'searchValue ---------->');

    let search = 'zZnull';
    if (this.searchValue !== null && this.searchValue !== undefined && this.searchValue !== '') {
      search = this.searchValue;
    }

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.patientService()
      .search(search, paginationQuery)
      .then(
        res => {
          this.patients = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IPatient): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePatient(): void {
    this.patientService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A Patient is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllPatients();
        this.closeDialog();
      });
  }

  public sort(): Array<any> {
    const result = [this.propOrder + ',' + (this.reverse ? 'desc' : 'asc')];
    if (this.propOrder !== 'id') {
      result.push('id');
    }
    return result;
  }

  public loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  public transition(): void {
    this.retrieveAllPatients();
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
    this.transition();
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
