import { IVisit } from '@/shared/model/visit.model';

export interface IDetailsOfVisit {
  id?: number;
  descriptionAilments?: string;
  nameOfDisease?: string;
  recommendations?: string | null;
  medicines?: string | null;
  dosage?: string | null;
  visit?: IVisit | null;
}

export class DetailsOfVisit implements IDetailsOfVisit {
  constructor(
    public id?: number,
    public descriptionAilments?: string,
    public nameOfDisease?: string,
    public recommendations?: string | null,
    public medicines?: string | null,
    public dosage?: string | null,
    public visit?: IVisit | null
  ) {}
}
