import { IClinic } from '@/shared/model/clinic.model';

export interface IDoctor {
  id?: number;
  name?: string;
  specialization?: string;
  phoneNumber?: string;
  clinics?: IClinic[] | null;
}

export class Doctor implements IDoctor {
  constructor(
    public id?: number,
    public name?: string,
    public specialization?: string,
    public phoneNumber?: string,
    public clinics?: IClinic[] | null
  ) {}
}
