import { Component, OnInit, ViewEncapsulation , ViewChild, Input, ElementRef, Renderer  } from '@angular/core';
import {SelectItem} from 'primeng/primeng';
import {Message} from 'primeng/primeng';

import {Router} from '@angular/router';
import {EventService} from '../../common/EventService';
import {StockVO} from './stockVO';
import {StockPortfolioVO} from './StockPortfolioVO';
import {WishList} from './wishList';
import {StockService} from './stockService';
@Component({
 selector : 'add-stock', 
  templateUrl: './addStock.component.html',
  encapsulation: ViewEncapsulation.None 
})

export class Stock implements OnInit {
  private stepIndicator : number = 0;
  private msgs : Message[] = [];
  private newStockSelected : string;
  public allListedStocksNSE : SelectItem[];
  public allListedStocksBSE : SelectItem[];
  private displayConfirmation : boolean = false;
  private profileIDToBedeleted : number;
  private totalGain : number;
  private totalXirr : number;
  private totalPercentGainAbsolute : number;
  private totalInvetment : number;
  private displayConfirmation_pastEQ : boolean = false;
  private profileIDToBedeleted_pastEQ : number;
  private displayConfirmation_wishList : boolean = false;
  
 @ViewChild('spinnerElement') spinnerElement: ElementRef;

 public allStocks : Array<StockVO> = [];
 private allStocks_eq_archive : Array<StockVO> = [];
 private eq_archive_totalInvestment : number;
 
 private wishListEquity : Array<WishList> = [];
 private eq_archive_totalReturn : number;
 private eq_archive_totalProfit : number;
 private eq_archive_totalProfitPercent : number;
  
    constructor(private renderer: Renderer, private stockService : StockService,
    private eventService : EventService,private router:Router) {} 
   
    ngOnInit(): void {
      this.restoreStocksFromLocalStorage();
     this.refreshPage();
     this.eventService.refreshEvent.subscribe( (refresh : string)=> {
        if ("/Stock" === this.router.url ){
         console.log("Refesh from lump sump "+this.router.url);
         this.refreshPage();
        }
        
      })
      
  }

  private addToWishList(){
    let wishList = this.wishListEquity;
    let wish : WishList = new WishList();
    wish.profileID = new Date().getTime();
    wishList.push(wish);
    this.wishListEquity = [];
    if (wishList){
      for(let i=0;i<wishList.length;i++ ){
        this.wishListEquity.push(wishList[i]);
      }
    }
    
    console.log("wishListEquity "+this.wishListEquity)
  }
  private deleteFromWishList(profileID : number){
    console.log(" do you want to delete "+profileID)
     this.profileIDToBedeleted_pastEQ = profileID;
     this.displayConfirmation_wishList = true;
     
  }
  private confirmDeleteFromWishList(){
    this.displayConfirmation_wishList = false;
  let  wishListAfterDelete : Array<WishList> = [];
  for (let i =0;i<this.wishListEquity.length;i++){
       if(this.wishListEquity[i].profileID != this.profileIDToBedeleted_pastEQ){
        wishListAfterDelete.push(this.wishListEquity[i]);
       }else {
         console.log(" I will delte "+this.profileIDToBedeleted_pastEQ)
       }
     }
     this.wishListEquity = wishListAfterDelete;
     this.saveWishList();
  }
  private deleteProfileFromPastData(profileID : number){
    
     this.profileIDToBedeleted_pastEQ = profileID;
     this.displayConfirmation_pastEQ = true;
     
  }
  private confirmDelete_pastEQ(){
    this.displayConfirmation_pastEQ = false;
  let  allProfiles_EQ_archive_afterDelete : Array<StockVO> = [];
  for (let i =0;i<this.allStocks_eq_archive.length;i++){
       if(this.allStocks_eq_archive[i].profileID != this.profileIDToBedeleted_pastEQ){
        allProfiles_EQ_archive_afterDelete.push(this.allStocks_eq_archive[i]);
       }else {
         console.log(" I will delte "+this.profileIDToBedeleted_pastEQ)
       }
     }
     this.allStocks_eq_archive = allProfiles_EQ_archive_afterDelete;
     this.saveTerminatedScheme();
  }
private toggleInfo() {
        
        if ( this.msgs.length ==0){
          this.msgs.push({severity:'info: ', summary:'Info', detail:"Track your Stock's absolute and % growth."});
        }else {
          this.msgs = [];
        }
        
    }

 private previousStep(){
   this.stepIndicator --;
 }
 private showProfileSummaryStep(){
   this.stepIndicator = 0;
 }
  private moveToSearchStockPage() {
   this.stepIndicator = 1;
  }
  private userSelectedAStock(newStockSelected : string){
        this.newStockSelected = newStockSelected;        
        this.stepIndicator ++;
  }
   private refreshPage(){
    
     this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','block');
     this.stepIndicator = 0;
     if (localStorage.getItem('allListedStocksNSE') && localStorage.getItem('allListedStocksNSE') != 'undefined'){
      
      this.allListedStocksNSE = JSON.parse(localStorage.getItem('allListedStocksNSE'));
     }
     if (localStorage.getItem('allListedStocksBSE')  && localStorage.getItem('allListedStocksBSE') != 'undefined'){
      this.allListedStocksBSE = JSON.parse(localStorage.getItem('allListedStocksBSE'));
     }
     
     if (!this.allListedStocksNSE || !this.allListedStocksBSE ){
        this.stockService.getAllListedStocks().subscribe( 
            funds => this.getAllListedStocksResult(funds),
            error => this.showError(error))
     }
    this.stockService.getStockProfile().subscribe( 
            funds => this.getStockProfileResult(funds),
            error => this.showError(error));
    this.stockService.getStockProfile_eq_archive().subscribe( 
              funds => this.getStockProfileResult_eq_archive(funds),
              error => this.showError(error));
    this.stockService.getWishList().subscribe(
      funds => this.gotWishList(funds),
      error => this.showError(error));
  }
private deleteFromProfile(profileID : number){
  this.profileIDToBedeleted = profileID;
  this.displayConfirmation = true;
}  
private confirmDelete(){
this.displayConfirmation = false;
 console.log("deleting "+this.profileIDToBedeleted);
 this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','block');
 this.stockService.deleteFromProfile(this.profileIDToBedeleted).subscribe( 
        msg => this.deletedFromProfile(msg),
        error => this.showError(error));
}
private saveWishList(){
  this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','block');
  this.stockService.saveWishList(this.wishListEquity).subscribe( 
    msg => this.saveWishListSuccess(msg),
    error => this.showError(error));
}
private getWishList(){
  this.stockService.getWishList().subscribe( 
    msg => this.gotWishList(msg),
    error => this.showError(error));
}
private deletedFromProfile(message:string){
   this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
   this.refreshPage();
}
private getStockProfileResult( stocks : StockPortfolioVO ){
  this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
  localStorage.setItem('lastKnownStockProfile', JSON.stringify(stocks));
  this.assignStockValues(stocks);
  }
  private gotWishList(wishList : Array<WishList>){
    this.wishListEquity = wishList;
  }
  private saveWishListSuccess(result: string){
    this.getWishList();
  }
  private assignStockValues(stocks : StockPortfolioVO){
    this.allStocks = stocks.allStocks;
    this.totalGain= stocks.totalGain;
   this.totalXirr = stocks.totalXirr;
   this.totalPercentGainAbsolute = stocks.percentGainAbsolute;
   this.totalInvetment = stocks.totalInvetment;
  }
  private restoreStocksFromLocalStorage(){
    if (null != localStorage.getItem('lastKnownStockProfile')){
      let stocks : StockPortfolioVO = JSON.parse(localStorage.getItem('lastKnownStockProfile'));
      this.assignStockValues(stocks);
    }
   
  }
  private getStockProfileResult_eq_archive( stocks : StockPortfolioVO ){
     this.allStocks_eq_archive = stocks.allStocks; 
     this.calculateTotalsForEqArchive();
  }
  private calculateTotalsForEqArchive(){
    this.eq_archive_totalInvestment = 0;
    this.eq_archive_totalReturn = 0;
    for (let i=0 ; i< this.allStocks_eq_archive.length ;i++){
      this.allStocks_eq_archive[i].absoluteGain = this.allStocks_eq_archive[i].currentValue -
                                                    this.allStocks_eq_archive[i].investmentAmount;
      this.allStocks_eq_archive[i].percentGainAbsolute = 
      this.allStocks_eq_archive[i].absoluteGain / this.allStocks_eq_archive[i].investmentAmount *100;

      this.eq_archive_totalInvestment +=this.allStocks_eq_archive[i].investmentAmount;
      this.eq_archive_totalReturn += this.allStocks_eq_archive[i].currentValue;
    } 
    this.eq_archive_totalProfit = this.eq_archive_totalReturn -  this.eq_archive_totalInvestment;
    this.eq_archive_totalProfitPercent = this.eq_archive_totalProfit/this.eq_archive_totalInvestment *100;

  }

  private saveTerminatedScheme(){
    this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','block');
    this.stockService.editProfiles_eq_archive(this.allStocks_eq_archive).subscribe( 
            data => this.editSuccess(data),
            error => this.showError(error)
          );
    
    
    }
    private editSuccess(data : string){
      this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
    }
private getAllListedStocksResult( stocks : Array <StockVO> ){

    this.allListedStocksNSE = [];
    this.allListedStocksBSE = [];
   
    let stockDataLen : number = stocks.length;
    for ( let i=0; i < stockDataLen ; i++){
    if ("NSE" === stocks[i].exchange ){
      
      this.allListedStocksNSE.push({label: stocks[i].exchange +":"+  stocks[i].ticker +" - "+stocks[i].companyName +"- isin : "+stocks[i].isin , 
      value:stocks[i].exchange + ":"+ stocks[i].ticker+ ":"+stocks[i].companyName})
    }else {
     
      this.allListedStocksBSE.push({label: stocks[i].exchange +":"+  stocks[i].ticker +" - "+stocks[i].companyName +"- isin : "+stocks[i].isin , 
      value:stocks[i].exchange + ":"+ stocks[i].ticker+ ":"+stocks[i].companyName})
    }
      
   }

   localStorage.setItem('allListedStocksNSE', JSON.stringify(this.allListedStocksNSE));
   localStorage.setItem('allListedStocksBSE', JSON.stringify(this.allListedStocksBSE));
  
  }

    private showError(error:any) {
      
      this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
    }

    

}