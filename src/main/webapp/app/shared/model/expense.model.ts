import { ISafe } from '@/shared/model/safe.model';

export interface IExpense {
  id?: number;
  expenseType?: string;
  price?: number;
  statement?: string;
  detailedStatement?: string;
  expenseDate?: Date;
  notes?: string;
  safe?: Promise<ISafe>;
}

export class Expense implements IExpense {
  constructor(
    public id?: number,
    public expenseType?: string,
    public price?: number,
    public statement?: string,
    public detailedStatement?: string,
    public expenseDate?: Date,
    public notes?: string,
    public safe?: Promise<ISafe>
  ) {}
}
