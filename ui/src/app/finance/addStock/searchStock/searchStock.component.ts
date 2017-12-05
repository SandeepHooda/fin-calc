import { Component, OnInit, ViewEncapsulation, Output, EventEmitter,ViewChild ,Input} from '@angular/core';
import {   NG_VALUE_ACCESSOR,NgModel} from '@angular/forms';
import {SelectItem} from 'primeng/primeng';
import {ValueAccessorBase} from '../../../common/value.access';
import {StockVO} from '../stockVO'


@Component({
 selector : 'search-stock', 
  templateUrl: './searchStock.component.html',
   
})

export class SearchStock  implements OnInit{
  @Input()
  allListedStocksNSE : SelectItem[];
  @Input()
  allListedStocksBSE : SelectItem[];
  private nseExchange : boolean = true;
  @Output() onStockSelect :EventEmitter<StockVO> = new EventEmitter();
    private selectedStock : StockVO;
   
    constructor(  ) {  }

    private notifyParentAboutStockSelection(){
      this.onStockSelect.emit(this.selectedStock); 
    } 
     

    ngOnInit(): void {
      console.log("NSE" +this.allListedStocksNSE[0].label);
      console.log("BSE" +this.allListedStocksBSE[0].label);
    }

   

 
}