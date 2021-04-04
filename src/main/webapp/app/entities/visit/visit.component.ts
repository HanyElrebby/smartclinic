import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IVisit } from '@/shared/model/visit.model';

import VisitService from './visit.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Visit extends Vue {
  @Inject('visitService') private visitService: () => VisitService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public visits: IVisit[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllVisits();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllVisits();
  }

  public retrieveAllVisits(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.visitService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.visits = res.data;
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

  public prepareRemove(instance: IVisit): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeVisit(): void {
    this.visitService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A Visit is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllVisits();
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
    this.retrieveAllVisits();
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
