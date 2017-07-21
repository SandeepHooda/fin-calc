import { Component, OnInit, ViewEncapsulation  } from '@angular/core';
import {FundService} from './fundservice';
import {Company} from './company';
import {NAV} from './nav';
import {SelectItem} from 'primeng/primeng';

@Component({
 selector : 'all-funds', 
  templateUrl: './addfund.component.html',
  encapsulation: ViewEncapsulation.None 
})

export class AddFund implements OnInit {
   private companyNames : SelectItem[];
    private navOfACompany : SelectItem[];
    private allNavs:Array<Company> = [];
    private httpError:any;
    constructor(private fundService :FundService) { 
     
    }

     
     

    ngOnInit(): void {
      this.companyNames = JSON.parse(localStorage.getItem('companyNames'));
      this.allNavs = JSON.parse(localStorage.getItem('allFunds'));
      if(!this. companyNames || this. companyNames.length <=0){
        this.fundService.getAllFunds().subscribe( 
        funds => this.showAllFunds(funds),
        error => this.showError(error)
      );
      }
       
  }

  private showAllFunds(funds:Array<Company>){
    this.companyNames = [];
    let returnedNAV : SelectItem[] = [];
    let companiesLen: number = funds.length;
    for ( let i=0;i<companiesLen;i++){
      let navLen: number = funds[i].navs.length;
      this.companyNames.push({label:funds[i].companyName, value:i})
      
   }
   localStorage.setItem('companyNames', JSON.stringify(this.companyNames));
   localStorage.setItem('allFunds', JSON.stringify(funds));
   
  }

  private showError(error:any) {
    this.httpError = error;
  }
}