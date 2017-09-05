
import { Component, OnInit, ViewEncapsulation , Output, EventEmitter, Input, ElementRef, Renderer, ViewChild  } from '@angular/core';
import {   NG_VALUE_ACCESSOR,NgModel} from '@angular/forms';
import {SelectItem} from 'primeng/primeng';
import {ValueAccessorBase} from '../../../common/value.access';
import {StockVO} from '../stockVO'
import {NavService} from '../../addFund/addfundDetails/nav.service';
import {StockService} from '../stockService';
@Component({
 selector : 'add-stock-details', 
  templateUrl: './addStock.component.html',
   
})

export class AddStockDetails  implements OnInit{
  @Input()
  selectedStockStr : string;
  @Output() onStockSelect :EventEmitter<StockVO> = new EventEmitter();
  @ViewChild('spinnerElement') spinnerElement: ElementRef;
  @Output() onSaveSuccess :EventEmitter<number> = new EventEmitter();
  private httpError : string; 
  private formSubmit : boolean; 
  private selectedStock : StockVO = new StockVO();
  private purchaseDate : Date;
  private purchaseQty : number;
  private purchasePrice : number;
  
  constructor( private stockService : StockService , private navService : NavService ,private renderer: Renderer) {  }

     

    ngOnInit(): void {
      
      //this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
      let stockDetails : string [] = this.selectedStockStr.split(":");
      this.selectedStock.exchange = stockDetails[0];
      this.selectedStock.ticker = stockDetails[1];
      this.selectedStock.companyName = stockDetails[2];

    }

    private addStockToProfile(){
      this.httpError = "";
      this.formSubmit = false;
      this.selectedStock.purchaseDateStr = this.navService.dateToStr(this.purchaseDate);
      this.selectedStock.purchasePrice = this.purchasePrice;
      this.selectedStock.purchaseQty = this.purchaseQty;
      //this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','block');
      this.stockService.addStockToProfile(this.selectedStock).subscribe( 
        result => this.addedToProfile(result),
        error => this.showError());
    }

  private addedToProfile(result:string){
        this.formSubmit = false;
        //this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
      this.onSaveSuccess.emit();
           
        
    }
     private showError(){
      // this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');  
    }
    public anyErrorInForm():boolean{
      return !this.purchaseDate || !this.purchaseQty || !this.purchasePrice || this.formSubmit;
   }

 
}