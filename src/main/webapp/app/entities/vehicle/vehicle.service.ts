import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVehicle } from 'app/shared/model/vehicle.model';

type EntityResponseType = HttpResponse<IVehicle>;
type EntityArrayResponseType = HttpResponse<IVehicle[]>;

@Injectable({ providedIn: 'root' })
export class VehicleService {
  public resourceUrl = SERVER_API_URL + 'api/vehicles';

  constructor(protected http: HttpClient) {}

  create(vehicle: IVehicle): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehicle);
    return this.http
      .post<IVehicle>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(vehicle: IVehicle): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehicle);
    return this.http
      .put<IVehicle>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVehicle>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVehicle[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(vehicle: IVehicle): IVehicle {
    const copy: IVehicle = Object.assign({}, vehicle, {
      dateAdded: vehicle.dateAdded && vehicle.dateAdded.isValid() ? vehicle.dateAdded.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateAdded = res.body.dateAdded ? moment(res.body.dateAdded) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((vehicle: IVehicle) => {
        vehicle.dateAdded = vehicle.dateAdded ? moment(vehicle.dateAdded) : undefined;
      });
    }
    return res;
  }
}
