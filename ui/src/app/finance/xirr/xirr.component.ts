import { Component, OnInit } from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {CalendarModule} from 'primeng/components/calendar/calendar';


@Component({
 selector : 'xirr', 
  templateUrl: './xirr.component.html',
  styleUrls: [ './xirr.component.css' ]
})
export class XIRR implements OnInit {
 private hero :String="Sandeep";
 value: Date;

  constructor() { }

  ngOnInit(): void {
    
  }
}
