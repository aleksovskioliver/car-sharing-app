import { Injectable } from '@angular/core';
import{HttpClient} from '@angular/common/http'
import { Reservation } from '../models/Reservation';
import { Observable, tap } from 'rxjs';
import { MyLocation } from '../models/MyLocation';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  url = 'http://localhost:8080/api'

  constructor(private http: HttpClient) {
    console.log('ReservationService constructor')
   }

   getReservations(pickupCity: string,dropoutCity: string): Observable<Reservation[]>{
     if((pickupCity == '') && (dropoutCity != '')){
      return this.http.get<Reservation[]>(`http://localhost:8080/api/reservation?dropoutCity=${dropoutCity}`)
     }else if((pickupCity != '') && (dropoutCity == '')){
      return this.http.get<Reservation[]>(`http://localhost:8080/api/reservation?pickupCity=${pickupCity}`);
     }else if((pickupCity == '') && (dropoutCity == '')){
      return this.http.get<Reservation[]>(`http://localhost:8080/api/reservation`).pipe(tap(data => console.log(data)) ); 
     }else{
      return this.http.get<Reservation[]>(`http://localhost:8080/api/reservation?pickupCity=${pickupCity}&dropoutCity=${dropoutCity}`); 
     }
   }

   getLocations(): Observable<MyLocation[]> {
      return this.http.get<MyLocation[]>(`${this.url}/getLocations`)
   }
}
