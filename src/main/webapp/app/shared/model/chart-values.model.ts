import { IPatient } from './patient.model';

export interface IChartValues {
  id?: number;
  xValue?: number | null;
  yValue?: number | null;
  type?: string | null;
  patient?: IPatient | null;
}

export class ChartValues implements IChartValues {
  constructor(
    public id?: number,
    public xValue?: number | null,
    public yValue?: number | null,
    public type?: string | null,
    public patient?: IPatient
  ) {}
}
