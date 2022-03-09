import { Component } from '@angular/core';
import { BasketService } from 'app/layouts/navbar/basket/basket.service';

@Component({
  selector: 'jhi-home',
  templateUrl: './basket-page.component.html',
  styleUrls: ['basket-page.scss']
})
export class BasketPageComponent {
  constructor(private basketService: BasketService) {}

  getTotalPrice(): number | undefined {
    return this.basketService.vehicles.value
      .map(vehicle => vehicle.price)
      .reduce((accumulator, currentValue) => (accumulator ? accumulator : 0) + (currentValue ? currentValue : 0));
  }
}
