import { IClinic } from '@/shared/model/clinic.model';

export interface IDoctor {
  id?: number;
  firstName?: string;
  lastName?: string;
  specialization?: string;
  phoneNumber?: string;
  clinics?: IClinic[] | null;
}

export class Doctor implements IDoctor {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public specialization?: string,
    public phoneNumber?: string,
    public clinics?: IClinic[] | null
  ) {}
}
