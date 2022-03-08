export interface IVehicleDTO {
  _id?: number;
  make?: string;
  model?: string;
  year_model?: number;
  price?: number;
  licensed?: string;
  date_added?: string;
}

export class VehicleDTO implements IVehicleDTO {
  constructor(
    public _id?: number,
    public make?: string,
    public model?: string,
    public year_model?: number,
    public price?: number,
    public licensed?: string,
    public date_added?: string
  ) {}
}
