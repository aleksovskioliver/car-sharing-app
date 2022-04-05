import { Injectable } from '@angular/core';
import{HttpClient} from '@angular/common/http'
import { Reservation } from '../models/Reservation';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  URL = 'http://localhost:8080/api'

  constructor(private http: HttpClient) {
    console.log('ReservationService constructor')
   }

   getReservations(): Observable<Reservation[]>{
     return this.http.get<Reservation[]>('http://localhost:8080/api/reservation/findAll')
   }

}
