import { Component, Inject, Vue } from 'vue-property-decorator';
import { mixins } from 'vue-class-component';
import Vue2Filters from 'vue2-filters';
import AccountService from '@/account/account.service';

@Component({})
export default class OwnerDashboard extends Vue {
  @Inject('accountService') private accountService: () => AccountService;

  public get username(): string {
    return this.$store.getters.account ? this.$store.getters.account.login : '';
  }

  public navigateTo(path: string): void {
    this.$router.push(path, () => {});
  }
}
