import { Component, OnInit, ViewEncapsulation , ViewChild, Input, ElementRef, Renderer  } from '@angular/core';
import {SelectItem} from 'primeng/primeng';
import {Message} from 'primeng/primeng';

import {Router} from '@angular/router';
import {EventService} from '../../common/EventService';
import {StockVO} from './stockVO';
import {StockPortfolioVO} from './StockPortfolioVO';

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
 @ViewChild('spinnerElement') spinnerElement: ElementRef;

 public allStocks : Array<StockVO> = [];
  
    constructor(private renderer: Renderer, private stockService : StockService) {} 
   
    ngOnInit(): void {
     this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
     this.refreshPage();
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
     this.stepIndicator = 0;
     this.allListedStocksNSE = JSON.parse(localStorage.getItem('allListedStocksNSE'));
     this.allListedStocksBSE = JSON.parse(localStorage.getItem('allListedStocksBSE'));
     if (!this.allListedStocksNSE || !this.allListedStocksBSE ){
        this.stockService.getAllListedStocks().subscribe( 
            funds => this.getAllListedStocksResult(funds),
            error => this.showError(error))
     }
    this.stockService.getStockProfile().subscribe( 
            funds => this.getStockProfileResult(funds),
            error => this.showError(error))
  }
     
private getStockProfileResult( stocks : StockPortfolioVO ){
console.log(stocks);
  this.allStocks = stocks.allStocks;
    
  }
private getAllListedStocksResult( stocks : Array <StockVO> ){

    this.allListedStocksNSE = [];
    this.allListedStocksBSE = [];
   
    let stockDataLen : number = stocks.length;
    for ( let i=0; i < stockDataLen ; i++){
    if ("NSE" === stocks[i].exchange ){
      
      this.allListedStocksNSE.push({label: stocks[i].exchange +":"+  stocks[i].ticker +" - "+stocks[i].companyName  , 
      value:stocks[i].exchange + ":"+ stocks[i].ticker+ ":"+stocks[i].companyName})
    }else {
     
      this.allListedStocksBSE.push({label: stocks[i].exchange +":"+  stocks[i].ticker +" - "+stocks[i].companyName  , 
      value:stocks[i].exchange + ":"+ stocks[i].ticker+ ":"+stocks[i].companyName})
    }
      
   }

   localStorage.setItem('allListedStocksNSE', JSON.stringify(this.allListedStocksNSE));
   localStorage.setItem('allListedStocksBSE', JSON.stringify(this.allListedStocksBSE));
   //this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
  }

    private showError(error:any) {
      
      this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
    }

}