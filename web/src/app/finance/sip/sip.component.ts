import { Component, OnInit, ViewEncapsulation  } from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {SipSchemeVO} from './scheme/sip.schemeVO';
import {Withdrawal} from './scheme/withdrawal';
import {SipService} from './sip-service';
@Component({
 selector : 'sip', 
  templateUrl: './sip.component.html',
  styleUrls: [ './sip.component.css' ],
  encapsulation: ViewEncapsulation.None 
})
export class Sip implements OnInit {
  private httpError : string;
  private listOfSipSchemes : Array<SipSchemeVO> = [];
 constructor( private sipService : SipService) {}

  ngOnInit(): void {
    this.getSipList();
  }
  
  private addScheme() {
    if (!this.listOfSipSchemes) {
      this.listOfSipSchemes = [];
    }
  let scheme : SipSchemeVO = new SipSchemeVO();
    scheme.schemeName = "New Scheme";
    scheme.startDate = new Date();
    scheme.endDate = new Date();
    scheme.sipAmount = 0;
    let withdrawal : Withdrawal = new Withdrawal();
    withdrawal.amount = 0;
    withdrawal.date = new Date();
    scheme.withdrawlsRows[0] = withdrawal;
    this.listOfSipSchemes.push(scheme);
    
  }

private getSipList() {
  this.sipService.getSipList().subscribe( 
        listOfSipSchemes => this.showSipList(listOfSipSchemes),
        error => this.showError(error)
      );
}
private showSipList(listOfSipSchemes : Array<SipSchemeVO>) {
  this.listOfSipSchemes = listOfSipSchemes;
}
private saveListOfSips() {
  this.httpError = "";
  this.sipService.saveSipList(this.listOfSipSchemes).subscribe( 
        result => this.saveListOfSipsResult(result),
        error => this.showError(error)
      );
}

private saveListOfSipsResult (result : String){

}

private showError(error:any) {
   
    this.httpError = error;
  }
}
