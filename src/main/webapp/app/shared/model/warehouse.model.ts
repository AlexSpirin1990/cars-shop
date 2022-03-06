export interface IWarehouse {
  id?: number;
  name?: string;
  locationLat?: number;
  locationLong?: number;
}

export class Warehouse implements IWarehouse {
  constructor(public id?: number, public name?: string, public locationLat?: number, public locationLong?: number) {}
}
