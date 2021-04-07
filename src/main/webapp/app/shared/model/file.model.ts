import { IPatient } from '@/shared/model/patient.model';

export interface IFile {
  id?: number;
  fileName?: string | null;
  fileContentType?: string | null;
  file?: string | null;
  dateUpload?: Date | null;
  note?: string | null;
  createdBy?: string | null;
  updatedBy?: string | null;
  patient?: IPatient | null;
}

export class File implements IFile {
  constructor(
    public id?: number,
    public fileName?: string | null,
    public fileContentType?: string | null,
    public file?: string | null,
    public dateUpload?: Date | null,
    public note?: string | null,
    public createdBy?: string | null,
    public updatedBy?: string | null,
    public patient?: IPatient | null
  ) {}
}
