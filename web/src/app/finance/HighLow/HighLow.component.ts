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
       priceVO => this.showChart(priceVO),
           error => this.showError(error)
         );
   }
   
private showChart(priceVO:Array<CurrentMarketPrice>){
this.priceVO = priceVO;

}


private showError(error:any) {
   
    this.httpError = error;
  }
}
