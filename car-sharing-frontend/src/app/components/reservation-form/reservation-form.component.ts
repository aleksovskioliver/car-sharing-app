import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MyLocation } from 'src/app/models/MyLocation';
import { ReservationService } from 'src/app/services/reservation.service';

@Component({
  selector: 'app-reservation-form',
  templateUrl: './reservation-form.component.html',
  styleUrls: ['./reservation-form.component.css']
})
export class ReservationFormComponent implements OnInit {

  reservationForm: FormGroup = this.formBuilder.group({
    pickupCity: ['', [Validators.required]],
    dropoffCity: ['', [Validators.required]],
    availableSeats: ['', Validators.required],
    startTime: ['', Validators.required],
    endTime: ['', Validators.required],
    price: ['', Validators.required]
  })
  isSubmitted = false
  cities: MyLocation[] = []

  constructor(
    private formBuilder: FormBuilder,
    private reservationService: ReservationService) { }

  ngOnInit(): void {
    this.reservationService.getLocations().subscribe({
      next: locations => {
        this.cities = locations
      },
      error: error => {
        console.log(error.error)
      }
    })
  }

  onSubmit() {
    this.isSubmitted = true;
    if (this.reservationForm.invalid) {
      return;
    }

    console.log(this.reservationForm.value)
  }

  changeCity(e: any) {
    
  }

  get f(): { [key: string]: AbstractControl } {
    return this.reservationForm.controls;
  }

}
