import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { Vehicle } from '../models/Vehicle';

@Injectable({
  providedIn: 'root'
})
export class VehiclesService {


  url = 'http://localhost:8080/api'


  constructor(private http: HttpClient) { }

  addVehicle(vehicle: Vehicle) {
    this.http.post(`${this.url}/vehicle/create`, vehicle).pipe(
      catchError(error => throwError(() => new Error(error.error)))
    )
  }
}
