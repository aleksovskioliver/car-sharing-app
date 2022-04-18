import { Component, Input, OnInit } from '@angular/core';
import * as moment from 'moment';
import { Reservation } from 'src/app/models/Reservation';

@Component({
  selector: 'app-created',
  templateUrl: './created.component.html',
  styleUrls: ['./created.component.css']
})
export class CreatedComponent implements OnInit {

  @Input() admin = false;
  @Input() customer = false;
  @Input() reservations: Reservation[] = []
  p: number = 1;

  constructor() { }

  ngOnInit(): void {
  }

  formatTime(time: string) {
    const date = moment(time)
    return date.format("LLL")
  }

}