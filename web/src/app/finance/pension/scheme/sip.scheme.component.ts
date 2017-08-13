import { Component, OnInit, ViewEncapsulation , Input,Output, EventEmitter } from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {SipSchemeVO} from './sip.schemeVO';
import {Withdrawal} from './withdrawal';
import {XirrRequest} from './xirrRequestVO';
import  {SchemePensionService} from './sip-service';

@Component({
 selector : 'pension-scheme', 
  templateUrl: './sip.scheme.component.html',
  styleUrls: [ './sip.scheme.component.css' ],
  encapsulation: ViewEncapsulation.None 
})
export class PensionScheme implements OnInit {
 @Input()
 private sipSchemeDetails : SipSchemeVO;
  @Output() onSchemeUpdated :EventEmitter<SipSchemeVO> = new EventEmitter();
   @Output() onSchemeDelete :EventEmitter<number> = new EventEmitter(); 
  

    private notifyParentAboutSchemeUpdate(){
      this.onSchemeUpdated.emit(this.sipSchemeDetails); 
    } 
    private deleteFromProfile(){
      this.onSchemeDelete.emit(this.sipSchemeDetails.schemeID);
    }
 private httpError:String

 private SIPerrorStartDate:boolean;
 private SIPerrorEndDate:boolean;
 private pensionErrorStartDate:boolean;
 private pensionErrorEndDate:boolean;
  constructor(private sipService : SchemePensionService) {

   }

  ngOnInit(): void {
   

  }
  
  public addRow() {
    let aWithdrawlsRow :Withdrawal = { "date" : null ,"dateLong":0, "amount" : null };
    this.sipSchemeDetails.withdrawlsRows.push(aWithdrawlsRow);
  }
  public delRow() {
    if (this.sipSchemeDetails.withdrawlsRows.length > 1) {
       this.sipSchemeDetails.withdrawlsRows.pop();
      
    }
  }
  public calculateReturns(){
     this.httpError  = "";
     this.sipSchemeDetails.returnOnInsvement = undefined;
    let payments : Array<number> = [];
    let dates : Array<String> = [];

    //Withdrawls 
    for (var i =0;i<this.sipSchemeDetails.withdrawlsRows.length;i++){
      payments.push(this.sipSchemeDetails.withdrawlsRows[i].amount);
      dates.push(this.sipSchemeDetails.withdrawlsRows[i].date.getDate()+"/"+(this.sipSchemeDetails.withdrawlsRows[i].date.getMonth()+1)+"/"+this.sipSchemeDetails.withdrawlsRows[i].date.getFullYear());
    }
    //Investments
    let startDate : Date = this.sipSchemeDetails.startDate;
    let datePointer = new Date(startDate.getFullYear(), startDate.getMonth(), startDate.getDate());
    if(datePointer.getDate()> 28){
      datePointer.setDate(28);
    }
    let endDate : Date = this.sipSchemeDetails.endDate;
    let datePointerEnd = new Date(endDate.getFullYear(), endDate.getMonth(), endDate.getDate());
    if(datePointerEnd.getDate()> 28){
      datePointerEnd.setDate(28);
    }
    while(datePointer.getTime() <= datePointerEnd.getTime()){
      console.log(datePointer);
      payments.push(this.sipSchemeDetails.sipAmount *-1);
      dates.push(datePointer.getDate()+"/"+(datePointer.getMonth()+1)+"/"+datePointer.getFullYear());
      datePointer.setMonth(datePointer.getMonth()+1);
    }

    //Pension
    let pensionStartDate : Date = this.sipSchemeDetails.pensionStartDate;
    let pensionDatePointer = new Date(pensionStartDate.getFullYear(), pensionStartDate.getMonth(), pensionStartDate.getDate());
    if(pensionDatePointer.getDate()> 28){
      pensionDatePointer.setDate(28);
    }
    let pensionEndDate : Date = this.sipSchemeDetails.pensionEndDate;
    let pensionDatePointerEnd = new Date(pensionEndDate.getFullYear(), pensionEndDate.getMonth(), pensionEndDate.getDate());
    if(pensionDatePointerEnd.getDate()> 28){
      pensionDatePointerEnd.setDate(28);
    }
    while(pensionDatePointer.getTime() <= pensionDatePointerEnd.getTime()){
      console.log(pensionDatePointer);
      payments.push(this.sipSchemeDetails.pensionAmount );
      dates.push(pensionDatePointer.getDate()+"/"+(pensionDatePointer.getMonth()+1)+"/"+pensionDatePointer.getFullYear());
      pensionDatePointer.setMonth(pensionDatePointer.getMonth()+1);
    }
    //let dataToPost:XirrRequest = {};
    let dataToPost:XirrRequest ={"payments":payments,"dates":dates};
    this.sipService.getXirr(dataToPost).subscribe( 
        returnOnInsvement => this.showXirrRate(returnOnInsvement),
        error => this.showError(error)
      );
    console.log(dataToPost);
   
  }
  private showXirrRate(returnOnInsvement: number) {
    this.sipSchemeDetails.returnOnInsvement = returnOnInsvement;
    this.notifyParentAboutSchemeUpdate();
  }

  private showError(error:any) {
    this.sipSchemeDetails.returnOnInsvement = undefined;
    this.httpError = error;
  }
 
  

  public anyErrorInForm():boolean{
    this.sipSchemeDetails.withdrawlsRows[0].date.getFullYear();
    return this.SIPerrorStartDate ||this.pensionErrorStartDate || this.SIPerrorEndDate ||this.pensionErrorEndDate 
    || !this.sipSchemeDetails.startDate || !this.sipSchemeDetails.endDate ||
     !this.sipSchemeDetails.pensionStartDate || !this.sipSchemeDetails.pensionEndDate ||
    !this.sipSchemeDetails.sipAmount ||!this.sipSchemeDetails.pensionAmount 
    || (this.sipSchemeDetails.sipAmount ==0) || (this.sipSchemeDetails.pensionAmount ==0) ||
    (this.sipSchemeDetails.withdrawlsRows[0].amount ==0 || this.sipSchemeDetails.withdrawlsRows[0].amount == null) ||
    !this.sipSchemeDetails.withdrawlsRows[0].date || (this.sipSchemeDetails.withdrawlsRows[0].date.getTime() <=this.sipSchemeDetails.startDate.getTime());
  }
  private checkDateValidation(){
    this.SIPerrorStartDate = false;
    this.SIPerrorEndDate = false;
    if(this.sipSchemeDetails.startDate && this.sipSchemeDetails.endDate  ){
      if( this.sipSchemeDetails.startDate.getTime() > this.sipSchemeDetails.endDate.getTime() ){
        this.SIPerrorStartDate = true;
      }
      if( this.sipSchemeDetails.startDate.getDate() != this.sipSchemeDetails.endDate.getDate() ){
        this.SIPerrorEndDate = true;
      }
    }
    
  }

private checkPensionDateValidation()
{
  this.pensionErrorStartDate = false;
    this.pensionErrorEndDate = false;
    if(this.sipSchemeDetails.pensionStartDate && this.sipSchemeDetails.pensionEndDate  ){
      if( this.sipSchemeDetails.pensionStartDate.getTime() > this.sipSchemeDetails.pensionEndDate.getTime() ){
        this.pensionErrorStartDate = true;
      }
      if( this.sipSchemeDetails.pensionStartDate.getDate() != this.sipSchemeDetails.pensionEndDate.getDate() ){
        this.pensionErrorEndDate = true;
      }
    }
}

  
}
