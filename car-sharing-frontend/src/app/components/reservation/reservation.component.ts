import { Component, Input, OnInit } from '@angular/core';
import { MapService } from 'src/app/services/map.service';
import { Reservation } from 'src/app/models/Reservation';
import { ReservationService } from 'src/app/services/reservation.service';

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.css']
})
export class ReservationComponent implements OnInit {

  @Input() reservations: Reservation[] = []
  p: number = 1;

  constructor(private service: ReservationService,
    private mapService: MapService) { }

  ngOnInit(): void {
  }
  reserved(id: number){
    this.service.addCustomerToReservation(id);
  }
  setMarker(r: Reservation){
    this.mapService.addMarker(r.pickupLocation.lat,r.pickupLocation.lng);
    this.mapService.addMarker(r.dropoutLocation.lat,r.dropoutLocation.lng);
  }
}
