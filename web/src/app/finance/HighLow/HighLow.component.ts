import { Component, OnInit, ViewEncapsulation  } from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';


import {Message} from 'primeng/primeng';

import {SelectItem} from 'primeng/primeng';
import {CurrentMarketPrice} from './CurrentMarketPrice';
import {CorpAnalysisService} from '../corpAnalysis/CorpAnalysis-service';

@Component({
 selector : 'HighLow', 
  templateUrl: './HighLow.component.html',
 
  encapsulation: ViewEncapsulation.None 
})
export class HighLow implements OnInit {
  private msgs : Message[] = [];

  private priceVO : Array<CurrentMarketPrice>;
  private httpError : string;


 constructor(private service : CorpAnalysisService ) {

 }

  ngOnInit(): void {
    
    
    this.getHighLowData();

    
  }
  

  private getHighLowData() {
    this.service.getHighLowData().subscribe( 
       priceVO => this.showData(priceVO),
           error => this.showError(error)
         );
   }
   
private showData(priceVO:Array<CurrentMarketPrice>){
  for (let i=0;i<priceVO.length;i++){
    priceVO[i].dayHighChange =  (priceVO[i].l_fix - priceVO[i].dayHigh)/priceVO[i].dayHigh*100;
    priceVO[i].dayLowChange = (priceVO[i].l_fix - priceVO[i].dayLow)/priceVO[i].dayLow*100;
    priceVO[i].high52Change = (priceVO[i].l_fix - priceVO[i].high52)/priceVO[i].high52*100;
    priceVO[i].low52Change = (priceVO[i].l_fix - priceVO[i].low52)/priceVO[i].low52*100;
    priceVO[i].openChange = (priceVO[i].l_fix - priceVO[i].open)/priceVO[i].open*100;
    priceVO[i].previousCloseChange = (priceVO[i].l_fix - priceVO[i].previousClose)/priceVO[i].previousClose*100;
  }
this.priceVO = priceVO;

}


private showError(error:any) {
   
    this.httpError = error;
  }
}
