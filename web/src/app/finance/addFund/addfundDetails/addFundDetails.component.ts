import { Component, OnInit , ViewEncapsulation ,Input,ViewChild, Renderer, ElementRef} from '@angular/core';
import {NavService} from './nav.service';
import {Profile} from '../profile';
import {Response as ResponseVO} from './response';
@Component({
 selector : 'add-fund-details', 
  templateUrl: './addFundDetails.component.html',
  encapsulation: ViewEncapsulation.None 
})

export class AddFundsDetails implements OnInit {
    @ViewChild('spinnerElement') spinnerElement: ElementRef;
    @Input()
    companyIDSelected :string;
    @Input()
    schemeCode :string;
    private units:number;
    private investmentAmount:number;
    private maxDate :Date;
    private investmentDate:Date;
    private httpError :string;
    private navOnInvestmentDate:number;
     constructor(private navService: NavService, private renderer: Renderer) {} 
     ngOnInit(): void {
         this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
         this.httpError = "";
         this.maxDate = new Date();
         this.maxDate.setDate(this.maxDate.getDate() - 1);
     }
     private replaceAll(actualData : string, search:string, replacement:string) :string {
    let target = actualData;
    return target.replace(new RegExp(search, 'g'), replacement);
    };

     private fetchNAV(){
         this.httpError = "";

        this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','block');
        let schemeCode_req = this.replaceAll(this.schemeCode, " ", "_");
        this.navService.getNav(this.investmentDate, schemeCode_req).subscribe( 
        nav => this.showNav(nav),
        error => this.showError(error));
         //schemeCode
       
     }

    private changeUnitsBought(){
        if (this.investmentAmount && this.navOnInvestmentDate){
                this.units = this.investmentAmount /this.navOnInvestmentDate;
            }else {
               this.units = undefined; 
            }
    }
    private addedToProfile(result:ResponseVO){
        this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
        if ("SUCCESS" == result.data){
            //move to show profile
        }
    }
     private showNav(nav:number){
       
         if (-9999.9999 == nav){
            this.httpError = "Could't find NAV for this date. Please enter nav yourself."
         }else {
            this.navOnInvestmentDate = nav;
            this.changeUnitsBought();
            
         }
        
        this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
    }

  private showError(error:any) {
    this.httpError = error;
    this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
  }
  private saveFundDetails(){
      let profile: Profile = new Profile();
      profile.investmentAmount = this.investmentAmount;
      profile.nav = this.navOnInvestmentDate;
      profile.investmentDate = this.navService.dateToStr(this.investmentDate);
      profile.units = this.units;
      profile.schemeName = this.schemeCode;
      this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','block');
      this.navService.addToPortfolio(profile).subscribe( 
        result => this.addedToProfile(result),
        error => this.showError(error));
  }
}