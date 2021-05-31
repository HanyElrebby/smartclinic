import { IPatient } from './patient.model';

export interface IChartValues {
  id?: number;
  age?: number | null;
  length?: number | null;
  weight?: number | null;
  type?: string | null;
  patient?: IPatient | null;
}

export class ChartValues implements IChartValues {
  constructor(
    public id?: number,
    public age?: number | null,
    public length?: number | null,
    public weight?: number | null,
    public type?: string | null,
    public patient?: IPatient
  ) {}
}
