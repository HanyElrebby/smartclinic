import { IClinic } from '@/shared/model/clinic.model';

export interface IDoctor {
  id?: number;
  name?: string;
  specialization?: string;
  phoneNumber?: string;
  clinics?: IClinic[] | null;
  createdBy?: string;
  updatedBy?: string;
}

export class Doctor implements IDoctor {
  constructor(
    public id?: number,
    public name?: string,
    public specialization?: string,
    public phoneNumber?: string,
    public clinics?: IClinic[] | null,
    public createdBy?: string,
    public updatedBy?: string
  ) {}
}
