import { Component, OnInit } from '@angular/core';
import {FormGroup, FormControl} from '@angular/forms';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {

  public minDate: Date = new Date ("05/07/2017");
    public maxDate: Date = new Date ("08/27/2017");
    public value: Date = new Date ("05/16/2017");

  constructor() { }

  ngOnInit(): void {
  }
  
}
