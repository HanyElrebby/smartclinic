import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IFile } from '@/shared/model/file.model';

import JhiDataUtils from '@/shared/data/data-utils.service';

import FileService from './file.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class File extends mixins(JhiDataUtils) {
  @Inject('fileService') private fileService: () => FileService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public files: IFile[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllFiles();
  }

  public translate(key: string): string {
    return this.$t(key) as string;
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllFiles();
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

  public retrieveAllFiles(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.fileService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.files = res.data;
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

  public prepareRemove(instance: IFile): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeFile(): void {
    this.fileService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A File is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllFiles();
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
    this.retrieveAllFiles();
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
