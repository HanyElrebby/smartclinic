import { IVisit } from '@/shared/model/visit.model';

export interface IPatient {
  id?: number;
  name?: string;
  pesel?: string;
  firstFatherName?: string;
  placeOfResidence?: string;
  fileNumber?: string;
  dateOfBirth?: Date;
  bloodGroup?: string;
  diseases?: string;
  surgery?: string;
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
    public fileNumber?: string,
    public age?: string,
    public gender?: string,
    public placeOfResidence?: string,
    public dateOfBirth?: Date,
    public bloodGroup?: string,
    public diseases?: string,
    public surgery?: string,
    public phoneNumber?: string,
    public visits?: IVisit[] | null,
    public createdBy?: string,
    public updatedBy?: string
  ) {}
}
