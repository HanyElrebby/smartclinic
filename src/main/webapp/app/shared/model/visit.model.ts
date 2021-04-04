import { IClinic } from '@/shared/model/clinic.model';
import { IPatient } from '@/shared/model/patient.model';
import { IDetailsOfVisit } from '@/shared/model/details-of-visit.model';

export interface IVisit {
  id?: number;
  dateOfVisit?: Date;
  visitType?: string;
  clinic?: IClinic | null;
  patient?: IPatient | null;
  detailsOfVisits?: IDetailsOfVisit[] | null;
}

export class Visit implements IVisit {
  constructor(
    public id?: number,
    public dateOfVisit?: Date,
    public visitType?: string,
    public clinic?: IClinic | null,
    public patient?: IPatient | null,
    public detailsOfVisits?: IDetailsOfVisit[] | null
  ) {}
}
