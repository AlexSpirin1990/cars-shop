import { Moment } from 'moment';
import { ILocation } from 'app/shared/model/location.model';
import { IWarehouse } from 'app/shared/model/warehouse.model';

export interface IVehicle {
  id?: number;
  make?: string;
  model?: string;
  yearModel?: number;
  price?: number;
  licensed?: boolean;
  dateAdded?: Moment;
  location?: ILocation;
  warehouse?: IWarehouse;
}

export class Vehicle implements IVehicle {
  constructor(
    public id?: number,
    public make?: string,
    public model?: string,
    public yearModel?: number,
    public price?: number,
    public licensed?: boolean,
    public dateAdded?: Moment,
    public location?: ILocation,
    public warehouse?: IWarehouse
  ) {
    this.licensed = this.licensed || false;
  }
}
