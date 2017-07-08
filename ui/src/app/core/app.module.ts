import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';
import { HttpModule }    from '@angular/http';
import {CalendarModule} from 'primeng/components/calendar/calendar';
import { AppRoutingModule } from './app-routing.module';
import {XIRR} from '../finance/xirr/xirr.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';


@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    CalendarModule 
  ],
  declarations: [
    XIRR
  ],
  providers: [  ],
  bootstrap: [ XIRR ]
})
export class AppModule { }
