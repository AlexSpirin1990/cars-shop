export interface ILocationDTO {
  lat?: number;
  long?: number;
}

export class LocationDTO implements ILocationDTO {
  constructor(public lat?: number, public long?: number) {}
}
