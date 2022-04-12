import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-reservation-form',
  templateUrl: './reservation-form.component.html',
  styleUrls: ['./reservation-form.component.css']
})
export class ReservationFormComponent implements OnInit {

  reservationForm: FormGroup = this.formBuilder.group({
    cityName: ['', [Validators.required]]
  })
  isSubmitted = false
  City: any = ['Florida', 'South Dakota', 'Tennessee', 'Michigan'];

  constructor(private formBuilder: FormBuilder) { }

  ngOnInit(): void {
  }

  onSubmit() {

  }

  changeCity(event: any) {

  }

  get cityName() {
    return this.reservationForm.get('cityName');
  }

}
