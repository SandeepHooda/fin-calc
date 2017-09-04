import { Component, OnInit, ViewEncapsulation, Output, EventEmitter,ViewChild ,Input} from '@angular/core';
import {   NG_VALUE_ACCESSOR,NgModel} from '@angular/forms';
import {SelectItem} from 'primeng/primeng';
import {ValueAccessorBase} from '../../../common/value.access';
import {StockVO} from '../stockVO'

@Component({
 selector : 'add-stock-details', 
  templateUrl: './addStock.component.html',
   
})

export class AddStock  implements OnInit{
  @Input()
  selectedStockStr : string;
  @Output() onStockSelect :EventEmitter<StockVO> = new EventEmitter();
    
  private selectedStock : StockVO = new StockVO();
  private investmentDate : Date;
  constructor(  ) {  }

     

    ngOnInit(): void {
      let stockDetails : string [] = this.selectedStockStr.split(":");
      this.selectedStock.exchange = stockDetails[0];
      this.selectedStock.ticker = stockDetails[1];
      this.selectedStock.companyName = stockDetails[2];

    }

 
}