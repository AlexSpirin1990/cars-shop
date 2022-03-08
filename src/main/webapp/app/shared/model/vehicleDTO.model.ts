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
  constructor(_id?: number, make?: string, model?: string, year_model?: number, price?: number, licensed?: string, date_added?: string) {}
}
