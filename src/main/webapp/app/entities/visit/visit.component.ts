import { mixins } from 'vue-class-component';

import { Datetime } from 'vue-datetime';
import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IVisit } from '@/shared/model/visit.model';

import VisitService from './visit.service';
import TrackerService from '@/core/SidebarPlugin/tracker.service';
import AccountService from '@/account/account.service';

@Component({
  mixins: [Vue2Filters.mixin],
  components: { datetime: Datetime },
  data() {
    return {
      value1: new Date().toISOString(),
    };
  },
})
export default class Visit extends Vue {
  @Inject('visitService') private visitService: () => VisitService;
  @Inject('accountService') private accountService: () => AccountService;

  private removeId: number = null;
  public itemsPerPage = 10;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public visits: IVisit[] = [];

  public isFetching = false;

  public date = new Date();

  public currentDate;

  public mounted(): void {
    this.retrieveAllVisits();
  }

  formatDate(date) {
    var d = date,
      month = '' + (d.getMonth() + 1),
      day = '' + d.getDate(),
      year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;

    return [year, month, day].join('-');
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllVisits();
  }

  public retrieveAllVisits(): void {
    this.isFetching = true;

    this.currentDate = this.formatDate(new Date(this.$data.value1));

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.visitService()
      .retrieveByDate(this.currentDate, paginationQuery)
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

  public checkAction(actionName: string) {
    return this.accountService().userActions.filter(c => c === actionName).length > 0;
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

  public formatDateView(dateString: string) {
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
