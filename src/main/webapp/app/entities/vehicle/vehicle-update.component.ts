import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IVehicle, Vehicle } from 'app/shared/model/vehicle.model';
import { VehicleService } from './vehicle.service';
import { ILocation } from 'app/shared/model/location.model';
import { LocationService } from 'app/entities/location/location.service';
import { IWarehouse } from 'app/shared/model/warehouse.model';
import { WarehouseService } from 'app/entities/warehouse/warehouse.service';

type SelectableEntity = ILocation | IWarehouse;

@Component({
  selector: 'jhi-vehicle-update',
  templateUrl: './vehicle-update.component.html'
})
export class VehicleUpdateComponent implements OnInit {
  isSaving = false;

  locations: ILocation[] = [];

  warehouses: IWarehouse[] = [];

  editForm = this.fb.group({
    id: [],
    make: [null, [Validators.required, Validators.minLength(1)]],
    model: [null, [Validators.required, Validators.minLength(1)]],
    yearModel: [null, [Validators.required]],
    price: [null, [Validators.required]],
    licensed: [null, [Validators.required]],
    dateAdded: [null, [Validators.required]],
    location: [null, Validators.required],
    warehouse: [null, Validators.required]
  });

  constructor(
    protected vehicleService: VehicleService,
    protected locationService: LocationService,
    protected warehouseService: WarehouseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehicle }) => {
      this.updateForm(vehicle);

      this.locationService
        .query()
        .pipe(
          map((res: HttpResponse<ILocation[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ILocation[]) => (this.locations = resBody));

      this.warehouseService
        .query()
        .pipe(
          map((res: HttpResponse<IWarehouse[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IWarehouse[]) => (this.warehouses = resBody));
    });
  }

  updateForm(vehicle: IVehicle): void {
    this.editForm.patchValue({
      id: vehicle.id,
      make: vehicle.make,
      model: vehicle.model,
      yearModel: vehicle.yearModel,
      price: vehicle.price,
      licensed: vehicle.licensed,
      dateAdded: vehicle.dateAdded != null ? vehicle.dateAdded.format(DATE_TIME_FORMAT) : null,
      location: vehicle.location,
      warehouse: vehicle.warehouse
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vehicle = this.createFromForm();
    if (vehicle.id !== undefined) {
      this.subscribeToSaveResponse(this.vehicleService.update(vehicle));
    } else {
      this.subscribeToSaveResponse(this.vehicleService.create(vehicle));
    }
  }

  private createFromForm(): IVehicle {
    return {
      ...new Vehicle(),
      id: this.editForm.get(['id'])!.value,
      make: this.editForm.get(['make'])!.value,
      model: this.editForm.get(['model'])!.value,
      yearModel: this.editForm.get(['yearModel'])!.value,
      price: this.editForm.get(['price'])!.value,
      licensed: this.editForm.get(['licensed'])!.value,
      dateAdded:
        this.editForm.get(['dateAdded'])!.value != null ? moment(this.editForm.get(['dateAdded'])!.value, DATE_TIME_FORMAT) : undefined,
      location: this.editForm.get(['location'])!.value,
      warehouse: this.editForm.get(['warehouse'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehicle>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
