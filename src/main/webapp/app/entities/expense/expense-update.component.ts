import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, decimal, minValue } from 'vuelidate/lib/validators';

import SafeService from '@/entities/safe/safe.service';
import { ISafe } from '@/shared/model/safe.model';

import { IExpense, Expense } from '@/shared/model/expense.model';
import ExpenseService from './expense.service';

const validations: any = {
  expense: {
    expenseType: {
      required,
    },
    price: {
      required,
      decimal,
      min: minValue(0),
    },
    statement: {
      required,
    },
    detailedStatement: {
      required,
    },
    notes: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class ExpenseUpdate extends Vue {
  @Inject('expenseService') private expenseService: () => ExpenseService;
  public expense: IExpense = new Expense();

  @Inject('safeService') private safeService: () => SafeService;

  public safes: ISafe[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.expenseId) {
        vm.retrieveExpense(to.params.expenseId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.expense.id) {
      this.expenseService()
        .update(this.expense)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Expense is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.expense.expenseDate = new Date();
      this.expense.safe = this.safeService().findBySecretary(this.username);
      this.expenseService()
        .create(this.expense)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Expense is created with identifier ' + param.id;
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public retrieveExpense(expenseId): void {
    this.expenseService()
      .find(expenseId)
      .then(res => {
        this.expense = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.safeService()
      .retrieve()
      .then(res => {
        this.safes = res.data;
      });
  }

  public get username(): string {
    return this.$store.getters.account ? this.$store.getters.account.login : '';
  }
}
