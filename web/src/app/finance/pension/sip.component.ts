import { Component, OnInit, ViewEncapsulation  } from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {SipSchemeVO} from './scheme/sip.schemeVO';
import {Withdrawal} from './scheme/withdrawal';
import {PensionService} from './sip-service';
@Component({
 selector : 'pension', 
  templateUrl: './sip.component.html',
  styleUrls: [ './sip.component.css' ],
  encapsulation: ViewEncapsulation.None 
})
export class Pension implements OnInit {
  private displayConfirmation : boolean;
  private idToBeDeleted : number;
  private httpError : string;
  private listOfSipSchemes : Array<SipSchemeVO> = [];
 constructor( private sipService : PensionService) {}

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
    scheme.pensionStartDate = new Date();
    scheme.pensionEndDate = new Date();
    scheme.pensionAmount = 0;
    let withdrawal : Withdrawal = new Withdrawal();
    withdrawal.amount = 0;
    withdrawal.date = new Date();
    scheme.withdrawlsRows[0] = withdrawal;
    scheme.schemeID = Math.random();
    this.listOfSipSchemes.push(scheme);
    
  }

private getSipList() {
 //this.listOfSipSchemes = [{schemeID: 0,"schemeName":"New Scheme1","startDate":null,"endDate":null,"startDateLong":1501858390000,"endDateLong":1501858390000,"sipAmount":10.0,"returnOnInsvement":0.0,"withdrawlsRows":[{"date":null,"dateLong":1502389800000,"amount":11.0}]},{schemeID:8,"schemeName":"New Scheme","startDate":null,"endDate":null,"startDateLong":1501858397000,"endDateLong":1501858397000,"sipAmount":13.0,"returnOnInsvement":590.3998217332212,"withdrawlsRows":[{"date":null,"dateLong":1502994600000,"amount":14.0}]}];
  //this.showSipList(this.listOfSipSchemes);
  this.sipService.getSipList().subscribe( 
        listOfSipSchemes => this.showSipList(listOfSipSchemes),
        error => this.showError(error)
      );
}
private showSipList(listOfSipSchemes : Array<SipSchemeVO>) {
  this.listOfSipSchemes = listOfSipSchemes;
  for (let i=0;i<this.listOfSipSchemes.length; i++){
    this.listOfSipSchemes[i].schemeID = Math.random();
      this.listOfSipSchemes[i].endDate = new Date(this.listOfSipSchemes[i].endDateLong);
       this.listOfSipSchemes[i].startDate = new Date(this.listOfSipSchemes[i].startDateLong);
       this.listOfSipSchemes[i].pensionEndDate = new Date(this.listOfSipSchemes[i].pensionEndDateLong);
       this.listOfSipSchemes[i].pensionStartDate = new Date(this.listOfSipSchemes[i].pensionStartDateLong);
       for (let j=0; j<this.listOfSipSchemes[i].withdrawlsRows.length;j++){
          this.listOfSipSchemes[i].withdrawlsRows[j].date = new Date(this.listOfSipSchemes[i].withdrawlsRows[j].dateLong);
       }
  }
}
private saveListOfSips() {
  this.httpError = "";
  this.sipService.saveSipList(this.listOfSipSchemes).subscribe( 
        result => this.saveListOfSipsResult(result),
        error => this.showError(error)
      );
}
private confirmDelete(){
  this.displayConfirmation = false;
let newSipScheleList : Array<SipSchemeVO> = []
  for (let i=0;i<this.listOfSipSchemes.length; i++){
    if (this.listOfSipSchemes[i].schemeID != this.idToBeDeleted ){
      newSipScheleList.push(this.listOfSipSchemes[i]);
    }
  }
  this.listOfSipSchemes = newSipScheleList;
  this.saveListOfSips();
}
private deleteFromProfile(id: number){
  this.displayConfirmation = true;
  this.idToBeDeleted = id;
  
}
private saveListOfSipsResult (result : String){

}

private showError(error:any) {
   
    this.httpError = error;
  }
}
