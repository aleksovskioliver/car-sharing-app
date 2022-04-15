import { Component, Input, OnInit } from '@angular/core';
import { MapService } from 'src/app/services/map.service';
import { Reservation } from 'src/app/models/Reservation';
import { ReservationService } from 'src/app/services/reservation.service';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';
import * as moment from 'moment';

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.css']
})
export class ReservationComponent implements OnInit {

  @Input() reservations: Reservation[] = []
  p: number = 1;

  constructor(
    private router: Router,
    private authService: AuthService,
    private service: ReservationService,
    private mapService: MapService) { }

  ngOnInit(): void { }

  reserved(id: number, r: Reservation) {
    if (this.authService.isLoggedIn()) {
      r.availableSeats--
      this.service.addCustomerToReservation(id);
      //window.location.reload();
    } else {
      this.router.navigateByUrl("/login")
    }
  }

  setMarker(r: Reservation) {
    this.mapService.addMarker(r.pickupLocation.lat, r.pickupLocation.lng);
    this.mapService.addMarker(r.dropoutLocation.lat, r.dropoutLocation.lng);
  }

  formatTime(time: string) {
    const date = moment(time)
    return date.format("LLL")
  }
}
