import { Component, OnInit, ViewEncapsulation  } from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {Withdrawal} from './withdrawal';
import  {SipService} from './sip-service';
import {XirrRequest} from './xirrRequestVO';
import {Company} from '../addFund/company';
import {NAV} from '../addFund/nav';
import {SelectItem} from 'primeng/primeng';

@Component({
 selector : 'sip', 
  templateUrl: './sip.component.html',
  styleUrls: [ './sip.component.css' ],
  encapsulation: ViewEncapsulation.None 
})
export class Sip implements OnInit {
private returnOnInsvement:number;
private SIPerrorStartDate:boolean;
private SIPerrorEndDate:boolean;
private startDate:Date;
private endDate:Date;
private sipAmount:number;
private httpError:String
 public withdrawlsRows :Array<Withdrawal> = [{"date":null,"amount":null}]; 

  constructor(private xirrService:SipService) {

   }

  
  public addRow() {
    let aWithdrawlsRow :Withdrawal = {"date":null,"amount":null};
    this.withdrawlsRows.push(aWithdrawlsRow);
  }
  public delRow() {
    if (this.withdrawlsRows.length > 1) {
       this.withdrawlsRows.pop();
       console.log(this.withdrawlsRows);
    }
  }
  public calculateReturns(){
     this.httpError  = "";
     this.returnOnInsvement = undefined;
    let payments : Array<number> = [];
    let dates : Array<String> = [];
    for (var i =0;i<this.withdrawlsRows.length;i++){
      payments.push(this.withdrawlsRows[i].amount);
      dates.push(this.withdrawlsRows[i].date.getDate()+"/"+(this.withdrawlsRows[i].date.getMonth()+1)+"/"+this.withdrawlsRows[i].date.getFullYear());
    }
    let datePointer = new Date(this.startDate.getTime());
    if(datePointer.getDate()> 28){
      datePointer.setDate(28);
    }
    let datePointerEnd = new Date(this.endDate.getTime());
    if(datePointerEnd.getDate()> 28){
      datePointerEnd.setDate(28);
    }
    while(datePointer.getTime() <= datePointerEnd.getTime()){
      console.log(datePointer);
      payments.push(this.sipAmount *-1);
      dates.push(datePointer.getDate()+"/"+(datePointer.getMonth()+1)+"/"+datePointer.getFullYear());
      datePointer.setMonth(datePointer.getMonth()+1);
    }
    //let dataToPost:XirrRequest = {};
    let dataToPost:XirrRequest ={"payments":payments,"dates":dates};
    this.xirrService.getXirr(dataToPost).subscribe( 
        returnOnInsvement => this.showXirrRate(returnOnInsvement),
        error => this.showError(error)
      );
    console.log(dataToPost);
   
  }
  private showXirrRate(returnOnInsvement: number) {
    this.returnOnInsvement = returnOnInsvement;
  }

  private showError(error:any) {
    this.returnOnInsvement = undefined;
    this.httpError = error;
  }
 
  ngOnInit(): void {
   

  }
  

  public anyErrorInForm():boolean{
    return this.SIPerrorStartDate || this.SIPerrorEndDate || !this.startDate || !this.endDate ||
    !this.sipAmount || (this.sipAmount ==0) ||
    (this.withdrawlsRows[0].amount ==0 || this.withdrawlsRows[0].amount == null) ||
    !this.withdrawlsRows[0].date || (this.withdrawlsRows[0].date.getTime() <=this.startDate.getTime());
  }
  private checkDateValidation(){
    this.SIPerrorStartDate = false;
    this.SIPerrorEndDate = false;
    if(this.startDate && this.endDate  ){
      if( this.startDate.getTime() > this.endDate.getTime() ){
        this.SIPerrorStartDate = true;
      }
      if( this.startDate.getDate() != this.endDate.getDate() ){
        this.SIPerrorEndDate = true;
      }
    }
    
  }

  private allFunds : SelectItem[] = [];
    

  

  
}
