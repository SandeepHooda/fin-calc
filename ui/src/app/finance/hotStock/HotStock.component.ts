import { Component, OnInit, ViewEncapsulation  } from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {HotStockSrv} from './HotStock-service';
import {Message} from 'primeng/primeng';
import {HotStockVO} from './HotStock';
@Component({
 selector : 'HotStock', 
  templateUrl: './HotStock.component.html',
 
  encapsulation: ViewEncapsulation.None 
})
export class HotStock implements OnInit {
  private msgs : Message[] = [];
  private displayConfirmation : boolean;
  private idToBeDeleted : number;
  private httpError : string;
  private hotStocks : Array<HotStockVO>;

 constructor( private service : HotStockSrv) {}

  ngOnInit(): void {
    this.getHotStocks();
  }
 
private getHotStocks() {
 this.service.getHotStocks().subscribe( 
    hotStocks => this.showHotStocks(hotStocks),
        error => this.showError(error)
      );
}

public lookupRowStyleClass(rowData: HotStockVO) {
  if (rowData.isHighProfile){
    return   'redRow' ;
  }
  
}
private showHotStocks(hotStocks:Array<HotStockVO>){
this.hotStocks = hotStocks;
}
private showError(error:any) {
   
    this.httpError = error;
  }
}
