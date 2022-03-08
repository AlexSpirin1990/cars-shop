import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';

import { LoginModalService } from 'app/core/login/login-modal.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { WarehouseService } from 'app/entities/warehouse/warehouse.service';
import { WarehouseDTO } from 'app/shared/model/warehouseDTO.model';
import { VehicleDTO } from 'app/shared/model/vehicleDTO.model';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  authSubscription?: Subscription;
  warehouses?: WarehouseDTO[];
  currWarehouse?: WarehouseDTO;
  currVehicle?: VehicleDTO;

  constructor(
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private warehouseService: WarehouseService
  ) {}

  ngOnInit(): void {
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account));

    this.warehouseService.getAllForShop().subscribe(res => {
      this.warehouses = res.body ? res.body : [];
      if (this.warehouses.length > 0) {
        this.currWarehouse = this.warehouses[0];
      }
    });
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  login(): void {
    this.loginModalService.open();
  }

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }

  changeCurrWarehause(warehouse: WarehouseDTO): void {
    this.currWarehouse = warehouse;
    this.currVehicle = undefined;
  }

  changeCurrVehicle(vehicle: VehicleDTO): void {
    if (!vehicle.licensed) {
      return;
    }
    this.currVehicle = vehicle;
  }
}
