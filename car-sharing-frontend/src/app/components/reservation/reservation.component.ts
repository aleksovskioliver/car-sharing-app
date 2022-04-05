import { Component, OnInit } from '@angular/core';
import { Reservation } from 'src/app/models/Reservation';
import { ReservationService } from 'src/app/services/reservation.service';

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.css']
})
export class ReservationComponent implements OnInit {

  reservations: Reservation[] = []

  constructor(private service: ReservationService) { }

  ngOnInit(): void {
    this.service.getReservations().subscribe({
      next: (data)=>{
        console.log('data',data);
        this.reservations = data;
      }
    })
  }
}
