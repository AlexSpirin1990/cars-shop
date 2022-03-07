import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { VehicleService } from 'app/entities/vehicle/vehicle.service';
import { IVehicle, Vehicle } from 'app/shared/model/vehicle.model';

describe('Service Tests', () => {
  describe('Vehicle Service', () => {
    let injector: TestBed;
    let service: VehicleService;
    let httpMock: HttpTestingController;
    let elemDefault: IVehicle;
    let expectedResult: IVehicle | IVehicle[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(VehicleService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Vehicle(0, 'AAAAAAA', 'AAAAAAA', 0, 0, false, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateAdded: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Vehicle', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateAdded: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateAdded: currentDate
          },
          returnedFromService
        );
        service
          .create(new Vehicle())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Vehicle', () => {
        const returnedFromService = Object.assign(
          {
            make: 'BBBBBB',
            model: 'BBBBBB',
            yearModel: 1,
            price: 1,
            licensed: true,
            dateAdded: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateAdded: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Vehicle', () => {
        const returnedFromService = Object.assign(
          {
            make: 'BBBBBB',
            model: 'BBBBBB',
            yearModel: 1,
            price: 1,
            licensed: true,
            dateAdded: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateAdded: currentDate
          },
          returnedFromService
        );
        service
          .query()
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Vehicle', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
