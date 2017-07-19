import { Component, OnInit, ViewEncapsulation  } from '@angular/core';
import {FundService} from './fundservice';
import {Company} from './company';

@Component({
 selector : 'all-funds', 
  templateUrl: './addfund.component.html',
  encapsulation: ViewEncapsulation.None 
})

export class AddFund implements OnInit {
    private allFunds : Array<Company>;
    private httpError:any;
    constructor(private fundService :FundService) { }

     
     

    ngOnInit(): void {
      /* this.fundService.getAllFunds().subscribe( 
        funds => this.showAllFunds(funds),
        error => this.showError(error)
      );*/
  }

  private showAllFunds(funds:Array<Company>){
    this.allFunds = funds;
  }

  private showError(error:any) {
    
    this.httpError = error;
  }
}