import { Component, OnInit, ViewEncapsulation , ViewChild, Input, ElementRef, Renderer  } from '@angular/core';
import {AddCompany} from './addCompany/addCompany.component'
import {FundService} from './fundservice';
import {Company} from './company';
import {NAV} from './nav';
import {SelectItem} from 'primeng/primeng';
import {Portfolio} from './portfolio';
import {Profile} from './profile';
import {Router} from '@angular/router';
@Component({
 selector : 'add-fund', 
  templateUrl: './addFund.component.html',
  encapsulation: ViewEncapsulation.None 
})

export class AddFunds implements OnInit {
 
  private displayConfirmation : boolean = false;
  private profileIDToBedeleted : number;
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
     private totalGain : number;
     private totalXirr : number;
     private totalInvetment : number;
    constructor(private fundService :FundService,private renderer: Renderer, private router:Router) {} 
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
      this.refreshPage();
    }
    private refreshPage(){
      let lastKnownPortFolio = localStorage.getItem('lastKnownPortFolio');

      if (null != lastKnownPortFolio){
        this.portfolio = JSON.parse(lastKnownPortFolio);
        this.allProfiles = this.portfolio.allProfiles;
        this.totalGain = this.portfolio.totalGain;
        this.totalXirr = this.portfolio.totalXirr;
        this.totalInvetment = this.portfolio.totalInvetment;
      }
      
   
      //this.allProfiles =  [{"profileID":1,"investmentDate":"12-Jul-2017","schemeName":"Axis Children\u0027s Gift Fund - Lock in - Direct Dividend","schemeCode":"135765","nav":11.9223,"investmentAmount":22.0,"units":1.8452815312481652,"currentValue":22.463350192496417,"currentNav":12.1734,"xirr":56.44024048694586,"companyName":"Axis Mutual Fund"}];
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
      this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','block');
        this.fundService.getPortfolio().subscribe( 
        portfolio => this.portFolioLoaded(portfolio),
        error => this.showError(error));
    }
    ngOnInit(): void {
      this.refreshPage();
      
  }


private portFolioLoaded(portfolio:Portfolio){
     
    this.portfolio = portfolio;
    localStorage.setItem('lastKnownPortFolio', JSON.stringify(portfolio));
    this.allProfiles = portfolio.allProfiles;
    this.totalGain = this.portfolio.totalGain;
    this.totalXirr = this.portfolio.totalXirr;
    this.totalInvetment = this.portfolio.totalInvetment;
    this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
}
private profileDeleted(message:string){
   this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
   this.refreshPage();
}

private deleteProfile(profileID :number){
  this.profileIDToBedeleted = profileID;
  this.displayConfirmation = true;
 
}
private confirmDelete(){
this.displayConfirmation = false;
 console.log("deleting "+this.profileIDToBedeleted);
 this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','block');
 this.fundService.deleteProfile(this.profileIDToBedeleted).subscribe( 
        msg => this.profileDeleted(msg),
        error => this.showError(error));
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