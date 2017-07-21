import { Component, OnInit, ViewEncapsulation  } from '@angular/core';
import {NAV} from './addCompany/nav';

@Component({
 selector : 'add-fund', 
  templateUrl: './addFund.component.html',
  encapsulation: ViewEncapsulation.None 
})

export class AddFunds implements OnInit {
    private company:number;
    constructor() {} 
    private companyIDSelected (company:number){
        this.company = company;
    }
    ngOnInit(): void {
       
  }


  
}