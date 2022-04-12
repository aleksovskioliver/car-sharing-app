import { Component, Input, OnInit } from '@angular/core';
import { Reservation } from 'src/app/models/Reservation';
import { ReservationService } from 'src/app/services/reservation.service';

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.css']
})
export class ReservationComponent implements OnInit {

  @Input() reservations: Reservation[] = []

  constructor(private service: ReservationService) { }

  ngOnInit(): void {
  }
  reserved(id: number){
    this.service.addCustomerToReservation(id);
  }
}
