import { Component, OnInit, ViewEncapsulation , ViewChild, Input, ElementRef, Renderer  } from '@angular/core';
import {AddCompany} from './addCompany/addCompany.component'
import {FundService} from './fundservice';
import {Company} from './company';
import {NAV} from './nav';
import {SelectItem} from 'primeng/primeng';
import {Portfolio} from './portfolio';
import {Profile} from './profile';
@Component({
 selector : 'add-fund', 
  templateUrl: './addFund.component.html',
  encapsulation: ViewEncapsulation.None 
})

export class AddFunds implements OnInit {
  private schemeCode : number;
  private schemeName : string;
  private stepIndicator:number=0;
  private httpError:any;
   public companyNames : SelectItem[];
    private allNavs:Array<Company> = [];
    @ViewChild('spinnerElement') spinnerElement: ElementRef;
    @ViewChild(AddCompany)
    private addcompanyModule : AddCompany;
    private companyName:string;
     private portfolio:Portfolio;
     private allProfiles : Array<Profile> = [];
    constructor(private fundService :FundService,private renderer: Renderer) {} 
    private schemeSelected  (nav:NAV){
        this.schemeCode = nav.SchemeCode;
        this.schemeName = nav.SchemeName;   
        this.stepIndicator++;
    }
    private companyNameSelected (companyName:string){
        this.companyName = companyName;   
        this.stepIndicator ++;
    }
    private showProfile (){
      this.stepIndicator =0;
    }
    ngOnInit(): void {
      //this.allProfiles =  [ { "profileID" : 1 , "investmentDate" : "12-Jul-2017" , "schemeName" : "AXIS CHILDREN'S GIFT FUND LOCK IN DIRECT DIVIDEND" , "nav" : 11.9223 , "investmentAmount" : 33.0 , "units" : 2.7679222968722477} , { "profileID" : 2 , "investmentDate" : "12-Jul-2017" , "schemeName" : "AXIS CHILDREN'S GIFT FUND LOCK IN DIRECT DIVIDEND" , "nav" : 11.9223 , "investmentAmount" : 33.0 , "units" : 2.7679222968722477} , { "profileID" : 3 , "investmentDate" : "12-Jul-2017" , "schemeName" : "AXIS CHILDREN'S GIFT FUND LOCK IN DIRECT DIVIDEND" , "nav" : 11.9223 , "investmentAmount" : 33.0 , "units" : 2.7679222968722477}];
      this.companyNames = JSON.parse(localStorage.getItem('companyNames'));
      this.allNavs = JSON.parse(localStorage.getItem('allFunds'));
      if('28_JUL_2017' != localStorage.getItem('cacheDate')){
        this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','block');
        this.fundService.getAllFunds().subscribe( 
        funds => this.showAllFunds(funds),
        error => this.showError(error)
      );
      }else {
        this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
      }
        this.fundService.getPortfolio().subscribe( 
        portfolio => this.portFolioLoaded(portfolio),
        error => this.showError(error));
  }


private portFolioLoaded(portfolio:Portfolio){
     
    this.portfolio = portfolio;
    this.allProfiles = portfolio.allProfiles;
    this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
}
   private showAllFunds(funds:Array<Company>){

    this.companyNames = [];
    let returnedNAV : SelectItem[] = [];
    let companiesLen: number = funds.length;
    for ( let i=0;i<companiesLen;i++){
      let navLen: number = funds[i].navs.length;
      this.companyNames.push({label:funds[i].companyName, value:i+'#'+funds[i].companyName})
   }
   localStorage.setItem('cacheDate', '28_JUL_2017');
   localStorage.setItem('companyNames', JSON.stringify(this.companyNames));
   localStorage.setItem('allFunds', JSON.stringify(funds));
   this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
  }

  private showError(error:any) {
    this.httpError = error;
    this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
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