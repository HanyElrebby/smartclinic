import { IVisit } from '@/shared/model/visit.model';

export interface IPatient {
  id?: number;
  firstName?: string;
  lastName?: string;
  pesel?: string;
  firstFatherName?: string;
  contactNumber?: string;
  placeOfResidence?: string;
  dateOfBirth?: Date;
  bloodGroup?: string;
  phoneNumber?: string;
  visits?: IVisit[] | null;
}

export class Patient implements IPatient {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public pesel?: string,
    public firstFatherName?: string,
    public contactNumber?: string,
    public placeOfResidence?: string,
    public dateOfBirth?: Date,
    public bloodGroup?: string,
    public phoneNumber?: string,
    public visits?: IVisit[] | null
  ) {}
}
