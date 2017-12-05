import { Component, OnInit, ViewEncapsulation  } from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {ChartsService} from './Charts-service';
import {Message} from 'primeng/primeng';
import {PriceVO} from './PriceVO'
@Component({
 selector : 'Charts', 
  templateUrl: './Charts.component.html',
 
  encapsulation: ViewEncapsulation.None 
})
export class Charts implements OnInit {
  private msgs : Message[] = [];
  private displayConfirmation : boolean;
  private priceVO : Array<PriceVO>;
  private httpError : string;

 constructor( private service : ChartsService) {}

  ngOnInit(): void {
    this.getChartsData();
  }
 
private getChartsData() {
 this.service.getChartsData().subscribe( 
    priceVO => this.showChart(priceVO),
        error => this.showError(error)
      );
}

private showChart(priceVO:Array<PriceVO>){
this.priceVO = priceVO;
}
private showError(error:any) {
   
    this.httpError = error;
  }
}
