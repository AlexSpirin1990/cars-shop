import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CarsShopSharedModule } from 'app/shared/shared.module';
import { BasketPageComponent } from './basket-page.component';
import { basketPageRoute } from 'app/basket-page/basket-page.route';

@NgModule({
  imports: [CarsShopSharedModule, RouterModule.forChild(basketPageRoute)],
  declarations: [BasketPageComponent]
})
export class BasketPageModule {}
