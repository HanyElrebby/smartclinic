import { Component, Vue, Inject } from 'vue-property-decorator';

import { IExpense } from '@/shared/model/expense.model';
import ExpenseService from './expense.service';

@Component
export default class ExpenseDetails extends Vue {
  @Inject('expenseService') private expenseService: () => ExpenseService;
  public expense: IExpense = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.expenseId) {
        vm.retrieveExpense(to.params.expenseId);
      }
    });
  }

  public retrieveExpense(expenseId) {
    this.expenseService()
      .find(expenseId)
      .then(res => {
        this.expense = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
