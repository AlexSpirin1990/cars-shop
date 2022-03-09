import { Injectable } from '@angular/core';
import { Subject, BehaviorSubject } from 'rxjs';
import { VehicleDTO } from 'app/shared/model/vehicleDTO.model';

@Injectable({ providedIn: 'root' })
export class BasketService {
  itemAddedOrRemoved = new Subject<{ added: boolean; vehicle: VehicleDTO }>();
  vehicles = new BehaviorSubject<VehicleDTO[]>([]);

  constructor() {}
}
