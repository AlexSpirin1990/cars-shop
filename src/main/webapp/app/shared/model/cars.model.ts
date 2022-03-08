import { VehicleDTO } from 'app/shared/model/vehicleDTO.model';

export interface ICars {
  location?: string;
  vehicles?: VehicleDTO[];
}

export class Cars implements ICars {
  constructor(public location?: string, public vehicles?: VehicleDTO[]) {}
}
