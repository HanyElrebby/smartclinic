export interface IMedicine {
  id?: number;
  name?: string | null;
  quantity?: string | null;
  notes?: string | null;
  createdBy?: string | null;
  updatedBy?: string | null;
}

export class Medicine implements IMedicine {
  constructor(
    public id?: number,
    public name?: string | null,
    public quantity?: string | null,
    public notes?: string | null,
    public createdBy?: string | null,
    public updatedBy?: string | null
  ) {}
}
