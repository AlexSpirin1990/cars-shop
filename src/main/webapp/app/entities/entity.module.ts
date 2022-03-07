import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'warehouse',
        loadChildren: () => import('./warehouse/warehouse.module').then(m => m.CarsShopWarehouseModule)
      },
      {
        path: 'location',
        loadChildren: () => import('./location/location.module').then(m => m.CarsShopLocationModule)
      },
      {
        path: 'vehicle',
        loadChildren: () => import('./vehicle/vehicle.module').then(m => m.CarsShopVehicleModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class CarsShopEntityModule {}
