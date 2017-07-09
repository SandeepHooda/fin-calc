import { Component, OnInit, ViewEncapsulation  } from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {Withdrawal} from './withdrawal';

@Component({
 selector : 'xirr', 
  templateUrl: './xirr.component.html',
  styleUrls: [ './xirr.component.css' ],
  encapsulation: ViewEncapsulation.None 
})
export class XIRR implements OnInit {
public return:number;
 public withdrawlsRows :Array<Withdrawal> = [{"date":null,"amount":null}]; 

  constructor() {

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
    let payments : Array<number> = [];
    let dates : Array<String> = [];
    for (var i =0;i<this.withdrawlsRows.length;i++){
      payments.push(this.withdrawlsRows[i].amount);
      console.log(this.withdrawlsRows[i].date.getDate());
      dates.push(this.withdrawlsRows[i].date.getDate()+"/"+(this.withdrawlsRows[i].date.getMonth()+1)+"/"+this.withdrawlsRows[i].date.getFullYear());
    }
    
    let dataToPost ={"payments":payments,"dates":dates};
    console.log(dates);
    console.log(dataToPost);
    this.return = 50;
  }
  ngOnInit(): void {
    
  }
}
