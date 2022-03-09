import { Component, OnDestroy, OnInit } from '@angular/core';
import { BasketService } from 'app/layouts/navbar/basket/basket.service';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'jhi-basket',
  templateUrl: './basket.component.html',
  styleUrls: ['basket.scss']
})
export class BasketComponent implements OnInit, OnDestroy {
  subscription: Subscription | undefined;

  constructor(private basketService: BasketService, private router: Router) {}

  ngOnInit(): void {
    this.subscription = this.basketService.itemAddedOrRemoved.subscribe(value => {
      if (value.added) {
        this.basketService.vehicles.next([...this.basketService.vehicles.value, value.vehicle]);
      } else {
        this.basketService.vehicles.next(this.basketService.vehicles.value.filter(vehicle => vehicle._id !== value.vehicle._id));
      }
    });
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  goToBasketPage(): void {
    this.router.navigate(['/basket']);
  }
}
