import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  createReservation = false

  constructor() { }

  ngOnInit(): void {
  }

  onClick() {
    this.createReservation = !this.createReservation
  }
  
}
