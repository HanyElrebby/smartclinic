import { IClinic } from '@/shared/model/clinic.model';
import { IPatient } from '@/shared/model/patient.model';
import { IDetailsOfVisit } from '@/shared/model/details-of-visit.model';
import { IDoctor } from './doctor.model';

export interface IVisit {
  id?: number;
  dateOfVisit?: Date;
  visitType?: string;
  clinic?: IClinic | null;
  patient?: IPatient | null;
  detailsOfVisits?: IDetailsOfVisit[] | null;
  createdBy?: string;
  updatedBy?: string;
  description?: string;
  medicine?: string;
  note?: string;
  cost?: number;
  status?: string;
  doctor?: IDoctor | null;
}

export class Visit implements IVisit {
  constructor(
    public id?: number,
    public dateOfVisit?: Date,
    public visitType?: string,
    public clinic?: IClinic | null,
    public patient?: IPatient | null,
    public detailsOfVisits?: IDetailsOfVisit[] | null,
    public createdBy?: string,
    public updatedBy?: string,
    public description?: string,
    public medicine?: string,
    public note?: string,
    public cost?: number,
    public status?: string,
    public doctor?: IDoctor | null
  ) {}
}
