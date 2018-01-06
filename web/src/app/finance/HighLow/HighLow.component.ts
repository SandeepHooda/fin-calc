import { Component, OnInit, ViewEncapsulation  } from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';


import {Message} from 'primeng/primeng';

import {SelectItem} from 'primeng/primeng';
import {CurrentMarketPrice} from './CurrentMarketPrice';
import {CorpAnalysisService} from '../corpAnalysis/CorpAnalysis-service';
import { CurrentMarketPriceWrapper } from './CurrentMarketPriceWrapper';

@Component({
 selector : 'HighLow', 
  templateUrl: './HighLow.component.html',
 
  encapsulation: ViewEncapsulation.None 
})
export class HighLow implements OnInit {
  private msgs : Message[] = [];

  private priceVO : Array<CurrentMarketPrice>;
  private oldSnapShots : Array<Array<CurrentMarketPrice>> = [];
  private selectedScripts : Array<CurrentMarketPrice> = [];
  private selectedScriptsCopy : Array<CurrentMarketPrice> = [];
  private httpError : string;


 constructor(private service : CorpAnalysisService ) {

 }

  ngOnInit(): void {
    this.getHighLowData();
}
 private  onRowSelect(event:any) {
    this.selectedScriptsCopy.push(event.data);
    localStorage.setItem('selectedScripts', JSON.stringify(this.selectedScriptsCopy));
  }

private onRowUnselect(event:any) {
  for (let i=0;i<this.selectedScriptsCopy.length;i++){
    if (this.selectedScriptsCopy[i].t === event.data.t){
      this.selectedScriptsCopy.splice(i,1);
     
    }
   
  }
  
  localStorage.setItem('selectedScripts', JSON.stringify(this.selectedScriptsCopy));
}

  private getHighLowData() {
    this.service.getHighLowData().subscribe( 
       priceVO => this.showData(priceVO),
           error => this.showError(error)
         );
   }
   
private showData(response:Array<CurrentMarketPriceWrapper>){
  let oldSnapShots : Array<Array<CurrentMarketPrice>> = [];
  for (let index=0;index<response.length;index++){
    let priceVO : Array<CurrentMarketPrice> = response[index].marketPices;
    for (let i=0;i<priceVO.length;i++){
      priceVO[i].dayHighChange =  (priceVO[i].l_fix - priceVO[i].dayHigh)/priceVO[i].dayHigh*100;
      priceVO[i].dayLowChange = (priceVO[i].l_fix - priceVO[i].dayLow)/priceVO[i].dayLow*100;
      priceVO[i].high52Change = (priceVO[i].l_fix - priceVO[i].high52)/priceVO[i].high52*100;
      priceVO[i].low52Change = (priceVO[i].l_fix - priceVO[i].low52)/priceVO[i].low52*100;
      priceVO[i].openChange = (priceVO[i].l_fix - priceVO[i].open)/priceVO[i].open*100;
      priceVO[i].previousCloseChange = (priceVO[i].l_fix - priceVO[i].previousClose)/priceVO[i].previousClose*100;
    }
    if (index == 0){//curent days snap shot
      this.selectedScripts = [];
    
      this.priceVO = priceVO;
      if (localStorage.getItem('selectedScripts')){
        this.selectedScripts = JSON.parse(localStorage.getItem('selectedScripts'));
        this.selectedScriptsCopy = JSON.parse(localStorage.getItem('selectedScripts'));
      
      }
    }
    oldSnapShots.push(priceVO);
    
    this.oldSnapShots = oldSnapShots;
  }

  

}


private showError(error:any) {
    this.httpError = error;
}
}
