import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { CarsShopSharedModule } from 'app/shared/shared.module';
import { CarsShopCoreModule } from 'app/core/core.module';
import { CarsShopAppRoutingModule } from './app-routing.module';
import { CarsShopHomeModule } from './home/home.module';
import { CarsShopEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';
import { BasketComponent } from 'app/layouts/navbar/basket/basket.component';
import { BasketPageModule } from 'app/basket-page/basket-page.module';

@NgModule({
  imports: [
    BrowserModule,
    CarsShopSharedModule,
    CarsShopCoreModule,
    CarsShopHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    CarsShopEntityModule,
    BasketPageModule,
    CarsShopAppRoutingModule
  ],
  declarations: [
    MainComponent,
    NavbarComponent,
    ErrorComponent,
    PageRibbonComponent,
    ActiveMenuDirective,
    FooterComponent,
    BasketComponent
  ],
  bootstrap: [MainComponent]
})
export class CarsShopAppModule {}
