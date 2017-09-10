import { Component, OnInit, ViewEncapsulation , ViewChild, Input, ElementRef, Renderer  } from '@angular/core';
import {AddCompany} from './addCompany/addCompany.component'
import {FundService} from './fundservice';
import {Company} from './company';
import {NAV} from './nav';
import {SelectItem} from 'primeng/primeng';
import {Message} from 'primeng/primeng';
import {Portfolio} from './portfolio';
import {Profile} from './profile';
import {Router} from '@angular/router';
import {EventService} from '../../common/EventService';


@Component({
 selector : 'add-fund', 
  templateUrl: './addFund.component.html',
  encapsulation: ViewEncapsulation.None 
})

export class AddFunds implements OnInit {
 
  private displayConfirmation : boolean = false;
  private displayConfirmation_pastMF : boolean = false;
  private profileIDToBedeleted : number;
  private profileIDToBedeleted_pastMF : number;
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
     private allProfiles_mf_archive : Array<Profile> = [];
     private mf_archive_totalInvestment : number;
     private mf_archive_totalReturn : number;
     private mf_archive_totalProfit : number;
     private mf_archive_totalProfitPercent : number;
     private totalGain : number;
     private totalXirr : number;
     private totalPercentGainAbsolute : number;
     private totalInvetment : number;
     private msgs : Message[] = [];
     private refreshTime : number = 0;
    
    constructor(private fundService :FundService,private renderer: Renderer, private router:Router, 
    private eventService : EventService) {} 
    private toggleInfo() {
        
        if ( this.msgs.length ==0){
          this.msgs.push({severity:'info: ', summary:'Info', detail:"Track your mutual fund's absolute and % growth."});
        }else {
          this.msgs = [];
        }
        
    }

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
      this.refreshPage(true);
    }
    private refreshPage(forceRefresh: boolean){
      if (forceRefresh && ((new Date().getTime() - this.refreshTime ) > 1000)){
        this.refreshTime = new Date().getTime();
      }else {
        forceRefresh = false;
        console.log(" You are hitting refresh too often");
      }
      
      let lastKnownPortFolio = localStorage.getItem('lastKnownPortFolio');

      if (null != lastKnownPortFolio){
        this.portfolio = JSON.parse(lastKnownPortFolio);
        this.allProfiles = this.portfolio.allProfiles;
        this.totalGain = this.portfolio.totalGain;
        this.totalXirr = this.portfolio.totalXirr;
        this.totalPercentGainAbsolute = this.portfolio.percentGainAbsolute;
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
     
        this.fundService.getPortfolio(forceRefresh).subscribe( 
        portfolio => this.portFolioLoaded(portfolio),
        error => this.showError(error));
        this.fundService.getPortfolio_mf_archive(forceRefresh).subscribe( 
        portfolio => this.portFolioLoaded_mf_archive(portfolio),
        error => this.showError(error));
        
    
    }
    ngOnInit(): void {
      this.refreshPage(false);
      this.eventService.refreshEvent.subscribe( (refresh : string)=> {
        if ("/lumpsump" === this.router.url ){
          
          console.log("Refesh from lump sump "+this.router.url);
         this.refreshPage(true);
        }
        
      })
  }


private portFolioLoaded(portfolio:Portfolio){
     
    this.portfolio = portfolio;
    localStorage.setItem('lastKnownPortFolio', JSON.stringify(portfolio));
    this.allProfiles = portfolio.allProfiles;
    this.totalGain = this.portfolio.totalGain;
    this.totalXirr = this.portfolio.totalXirr;
    this.totalPercentGainAbsolute = this.portfolio.percentGainAbsolute;
    this.totalInvetment = this.portfolio.totalInvetment;
    this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
}
private portFolioLoaded_mf_archive(portfolio:Portfolio){
     
    this.portfolio = portfolio;
    localStorage.setItem('lastKnownPortFolio_mf_archive', JSON.stringify(portfolio));
    this.allProfiles_mf_archive = portfolio.allProfiles;  
    this.mf_archive_totalInvestment = 0;
    this.mf_archive_totalReturn = 0;
    for (let i=0 ; i< this.allProfiles_mf_archive.length ;i++){
      this.mf_archive_totalInvestment +=this.allProfiles_mf_archive[i].investmentAmount;
      this.mf_archive_totalReturn += this.allProfiles_mf_archive[i].currentValue;
    } 
    this.mf_archive_totalProfit = this.mf_archive_totalReturn -  this.mf_archive_totalInvestment;
    this.mf_archive_totalProfitPercent = this.mf_archive_totalProfit/this.mf_archive_totalInvestment *100;
}
private profileDeleted(message:string){
   this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
   this.refreshPage(true);
}

private recalculatePastMFtotals(){

  this.mf_archive_totalInvestment = 0;
    this.mf_archive_totalReturn = 0;
    for (let i=0 ; i< this.allProfiles_mf_archive.length ;i++){
      this.allProfiles_mf_archive[i].absoluteGain = this.allProfiles_mf_archive[i].currentValue -
                                                    this.allProfiles_mf_archive[i].investmentAmount;
      this.allProfiles_mf_archive[i].percentGainAbsolute = 
      this.allProfiles_mf_archive[i].absoluteGain / this.allProfiles_mf_archive[i].investmentAmount *100;

      this.mf_archive_totalInvestment +=this.allProfiles_mf_archive[i].investmentAmount;
      this.mf_archive_totalReturn += this.allProfiles_mf_archive[i].currentValue;
    } 
    this.mf_archive_totalProfit = this.mf_archive_totalReturn -  this.mf_archive_totalInvestment;
    this.mf_archive_totalProfitPercent = this.mf_archive_totalProfit/this.mf_archive_totalInvestment *100;

}
private deleteProfile(profileID :number){
  this.profileIDToBedeleted = profileID;
  this.displayConfirmation = true;
 
}
private deleteProfileFromPastData(profileID : number){
  
   this.profileIDToBedeleted_pastMF = profileID;
   this.displayConfirmation_pastMF = true;
   
}
private confirmDelete_pastMF(){
let  allProfiles_mf_archive_afterDelete : Array<Profile> = [];
for (let i =0;i<this.allProfiles_mf_archive.length;i++){
     if(this.allProfiles_mf_archive[i].profileID != this.profileIDToBedeleted_pastMF){
      allProfiles_mf_archive_afterDelete.push(this.allProfiles_mf_archive[i]);
     }else {
       console.log(" I will delte "+this.profileIDToBedeleted_pastMF)
     }
   }
   this.allProfiles_mf_archive = allProfiles_mf_archive_afterDelete;
   //this.saveTerminatedScheme();
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

private saveTerminatedScheme(){
this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','block');
this.fundService.editProfiles_mf_archive(this.allProfiles_mf_archive).subscribe( 
        data => this.editSuccess(data),
        error => this.showError(error)
      );
console.log(this.allProfiles_mf_archive[0].exitDate);

}

private editSuccess(data : string){
  this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
}
private exportToExcel(){
  this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','block');
  this.fundService.sendDownloadRequest().subscribe( 
        data => this.downloadFile(data),
        error => this.showError(error)
      );
}
  
  private downloadFile(data: Response){
 
   let parsedResponse = data.text();
        let blob = new Blob(['\ufeff' + parsedResponse], { type: 'text/csv;charset=utf-8;' });
        let dwldLink = document.createElement("a");
        let url = window.URL.createObjectURL(blob);
        let isSafariBrowser = navigator.userAgent.indexOf('Safari') != -1 && navigator.userAgent.indexOf('Chrome') == -1;
        if (isSafariBrowser) {  //if Safari open in new window to save file with random filename.
        dwldLink.setAttribute("target", "_blank");
    }
        dwldLink.setAttribute("href", url);
        dwldLink.setAttribute("download", "Enterprise.csv");
        dwldLink.style.visibility = "hidden";
        document.body.appendChild(dwldLink);
        dwldLink.click();
        document.body.removeChild(dwldLink);
        window.URL.revokeObjectURL(url);
        this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
   }
}