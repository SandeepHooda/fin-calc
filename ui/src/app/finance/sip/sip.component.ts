import { Component, OnInit, ViewEncapsulation  } from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {SipSchemeVO} from './scheme/sip.schemeVO';
import {Withdrawal} from './scheme/withdrawal';
@Component({
 selector : 'sip', 
  templateUrl: './sip.component.html',
  styleUrls: [ './sip.component.css' ],
  encapsulation: ViewEncapsulation.None 
})
export class Sip implements OnInit {
  private listOfSipSchemes : Array<SipSchemeVO> = [];
 constructor() {}

  ngOnInit(): void {
    let scheme : SipSchemeVO = new SipSchemeVO();
    scheme.schemeName = "Sandeep";
    scheme.startDate = new Date();
    scheme.endDate = new Date();
    scheme.sipAmount = 400;
    let withdrawal : Withdrawal = new Withdrawal();
    withdrawal.amount = 700;
    withdrawal.date = new Date();
    scheme.withdrawlsRows[0] = withdrawal;
    this.listOfSipSchemes.push(scheme);
    
  }
  

}
