import { Component, OnInit, ViewEncapsulation , ViewChild, Input, ElementRef, Renderer  } from '@angular/core';
import {AddCompany} from './addCompany/addCompany.component'
import {FundService} from './fundservice';
import {Company} from './company';
import {NAV} from './nav';
import {SelectItem} from 'primeng/primeng';

@Component({
 selector : 'add-fund', 
  templateUrl: './addFund.component.html',
  encapsulation: ViewEncapsulation.None 
})

export class AddFunds implements OnInit {
  private schemeCode :number;
  private stepIndicator:number=0;
  private httpError:any;
   public companyNames : SelectItem[];
    private allNavs:Array<Company> = [];
    @ViewChild('spinnerElement') spinnerElement: ElementRef;
    @ViewChild(AddCompany)
    private addcompanyModule : AddCompany;
    private companyID:string;
    constructor(private fundService :FundService,private renderer: Renderer) {} 
    private schemeSelected  (schemeCode:number){
        this.schemeCode = schemeCode;   
        this.stepIndicator++;
    }
    private companyIDSelected (companyIDSelected:string){
        this.companyID = companyIDSelected;   
        this.stepIndicator ++;
    }
    ngOnInit(): void {
      this.companyNames = JSON.parse(localStorage.getItem('companyNames'));
      this.allNavs = JSON.parse(localStorage.getItem('allFunds'));
      //if(!this. companyNames || this. companyNames.length <=0){
        this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','block');
        this.fundService.getAllFunds().subscribe( 
        funds => this.showAllFunds(funds),
        error => this.showError(error)
      );
     /* }else {
        this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
      }*/
        
  }

   private showAllFunds(funds:Array<Company>){

    this.companyNames = [];
    let returnedNAV : SelectItem[] = [];
    let companiesLen: number = funds.length;
    for ( let i=0;i<companiesLen;i++){
      let navLen: number = funds[i].navs.length;
      this.companyNames.push({label:funds[i].companyName, value:i+'#'+funds[i].companyName})
   }
   localStorage.setItem('companyNames', JSON.stringify(this.companyNames));
   localStorage.setItem('allFunds', JSON.stringify(funds));
   this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
  }

  private showError(error:any) {
    this.httpError = error;
  }

private nextStep(){
  this.stepIndicator++;
}
private previousStep(){
  this.stepIndicator--;
}
private selectCompanyStep() {
   this.stepIndicator = 1;
}
private showProfileSummaryStep(){
  this.stepIndicator = 0;
}
  
}