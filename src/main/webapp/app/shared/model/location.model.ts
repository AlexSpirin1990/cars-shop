export interface ILocation {
  id?: number;
  name?: string;
}

export class Location implements ILocation {
  constructor(public id?: number, public name?: string) {}
}
