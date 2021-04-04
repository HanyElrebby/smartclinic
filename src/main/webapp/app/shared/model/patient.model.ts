import { IVisit } from '@/shared/model/visit.model';

export interface IPatient {
  id?: number;
  name?: string;
  pesel?: string;
  firstFatherName?: string;
  contactNumber?: string;
  placeOfResidence?: string;
  dateOfBirth?: Date;
  bloodGroup?: string;
  phoneNumber?: string;
  visits?: IVisit[] | null;
  createdBy?: string;
  updatedBy?: string;
}

export class Patient implements IPatient {
  constructor(
    public id?: number,
    public name?: string,
    public pesel?: string,
    public firstFatherName?: string,
    public contactNumber?: string,
    public fileNumber?: string,
    public age?: string,
    public gender?: string,
    public placeOfResidence?: string,
    public dateOfBirth?: Date,
    public bloodGroup?: string,
    public phoneNumber?: string,
    public visits?: IVisit[] | null,
    public createdBy?: string,
    public updatedBy?: string
  ) {}
}
