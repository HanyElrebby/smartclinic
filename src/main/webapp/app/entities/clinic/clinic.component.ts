import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IClinic } from '@/shared/model/clinic.model';

import ClinicService from './clinic.service';
import { Table, TableColumn, DropdownMenu, DropdownItem, Dropdown } from 'element-ui';

@Component({
  mixins: [Vue2Filters.mixin],
  [Table.name]: Table,
  [TableColumn.name]: TableColumn,
  [Dropdown.name]: Dropdown,
  [DropdownItem.name]: DropdownItem,
  [DropdownMenu.name]: DropdownMenu,
})
export default class Clinic extends Vue {
  @Inject('clinicService') private clinicService: () => ClinicService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  type: {
    type: String; // striped | hover
    default: '';
    description: 'Whether table is striped or hover type';
  };

  theadClasses: {
    type: String;
    default: '';
    description: '<thead> css classes';
  };

  tbodyClasses: {
    type: String;
    default: '';
    description: '<tbody> css classes';
  };

  public clinics: IClinic[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllClinics();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllClinics();
  }

  public translate(key: string): string {
    return this.$t(key) as string;
  }

  public retrieveAllClinics(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.clinicService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.clinics = res.data;
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

  public prepareRemove(instance: IClinic): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeClinic(): void {
    this.clinicService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A Clinic is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllClinics();
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

  tableClass() {
    return this.type && `table-${this.type}`;
  }

  public loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  public transition(): void {
    this.retrieveAllClinics();
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
