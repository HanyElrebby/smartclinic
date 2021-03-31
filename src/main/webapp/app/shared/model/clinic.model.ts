import { IDoctor } from '@/shared/model/doctor.model';
import { IVisit } from '@/shared/model/visit.model';
import { IUser } from '@/shared/model/user.model';

export interface IClinic {
  id?: number;
  nameOfClinic?: string;
  city?: string;
  postalCode?: string;
  street?: string;
  doctor?: IDoctor | null;
  visits?: IVisit[] | null;
  user?: IUser | null;
}

export class Clinic implements IClinic {
  constructor(
    public id?: number,
    public nameOfClinic?: string,
    public city?: string,
    public postalCode?: string,
    public street?: string,
    public doctor?: IDoctor | null,
    public visits?: IVisit[] | null,
    public user?: IUser | null
  ) {}
}
