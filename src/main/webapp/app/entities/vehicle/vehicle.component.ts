import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVehicle } from 'app/shared/model/vehicle.model';
import { VehicleService } from './vehicle.service';
import { VehicleDeleteDialogComponent } from './vehicle-delete-dialog.component';

@Component({
  selector: 'jhi-vehicle',
  templateUrl: './vehicle.component.html'
})
export class VehicleComponent implements OnInit, OnDestroy {
  vehicles?: IVehicle[];
  eventSubscriber?: Subscription;

  constructor(protected vehicleService: VehicleService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.vehicleService.query().subscribe((res: HttpResponse<IVehicle[]>) => {
      this.vehicles = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInVehicles();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IVehicle): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInVehicles(): void {
    this.eventSubscriber = this.eventManager.subscribe('vehicleListModification', () => this.loadAll());
  }

  delete(vehicle: IVehicle): void {
    const modalRef = this.modalService.open(VehicleDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.vehicle = vehicle;
  }
}
