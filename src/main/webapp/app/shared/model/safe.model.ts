export interface ISafe {
  id?: number;
  safeName?: string;
  safeSecretary?: string;
  safeValue?: number;
  notes?: string;
}

export class Safe implements ISafe {
  constructor(
    public id?: number,
    public safeName?: string,
    public safeSecretary?: string,
    public safeValue?: number,
    public notes?: string
  ) {}
}
