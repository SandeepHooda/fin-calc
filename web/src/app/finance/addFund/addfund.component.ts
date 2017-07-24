import { Component, OnInit, ViewEncapsulation , ViewChild } from '@angular/core';
import {AddCompany} from './addCompany/addCompany.component'
import {NAV} from './addCompany/nav';

@Component({
 selector : 'add-fund', 
  templateUrl: './addFund.component.html',
  encapsulation: ViewEncapsulation.None 
})

export class AddFunds implements OnInit {

    @ViewChild(AddCompany)
    private addcompanyModule : AddCompany;
    private company:number;
    constructor() {} 
    private companyIDSelected (company:number){
        this.company = company;
        console.log(this.addcompanyModule.companyNames);
    }
    ngOnInit(): void {
        
  }


  
}