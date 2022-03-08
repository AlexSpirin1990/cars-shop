import { LocationDTO } from 'app/shared/model/locationDTO.model';
import { Cars } from 'app/shared/model/cars.model';

export interface IWarehouseDTO {
  _id?: number;
  name?: string;
  location?: LocationDTO;
  cars?: Cars;
}

export class WarehouseDTO implements IWarehouseDTO {
  constructor(public _id?: number, public name?: string, public location?: LocationDTO, public cars?: Cars) {}
}
