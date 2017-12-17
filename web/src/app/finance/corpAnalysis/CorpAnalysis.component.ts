import { Component, OnInit, ViewEncapsulation  } from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {CorpAnalysisService} from './CorpAnalysis-service';
import {Message} from 'primeng/primeng';
import {StockAnalysisVO} from './StockAnalysisVO';
import {DataSets} from '../Charts/DataSets';
import {PriceVO} from '../Charts/PriceVO';
@Component({
 selector : 'CorpAnalysis', 
  templateUrl: './CorpAnalysis.component.html',
 
  encapsulation: ViewEncapsulation.None 
})
export class CorpAnalysis implements OnInit {
  private msgs : Message[] = [];
  private displayConfirmation : boolean;
  private idToBeDeleted : number;
  private httpError : string;
  private corpAnalysis : Array<StockAnalysisVO>;
  private profitiablityChart : PriceVO
 constructor( private service : CorpAnalysisService) {
  
 }

  ngOnInit(): void {
    this.getAnalysis();
  }
 
private getAnalysis() {
 this.service.getAnalysis().subscribe( 
    corpAnalysis => this.showAnalysis(corpAnalysis),
        error => this.showError(error)
      );
}

private showAnalysis(corpAnalysis:Array<StockAnalysisVO>){
this.corpAnalysis = corpAnalysis;
let profitiablityChart : PriceVO;
let i = this.corpAnalysis[0].maxCaptureYear- this.corpAnalysis[0].PBDITMarginYOY.length+1;
 for (;i<= this.corpAnalysis[0].maxCaptureYear;i++){
  profitiablityChart.labels.push(""+i);
 }

 for (let index: number=0;index < this.corpAnalysis.length;index++){
   let dataSet : DataSets = new DataSets();
   dataSet.borderColor = this.corpAnalysis[index].borderColor;
   dataSet.label = this.corpAnalysis[index].companyName.replace("/","");
   dataSet.data = this.corpAnalysis[index].PBDITMarginYOY;
  profitiablityChart.datasets.push(dataSet);
 }
 this.profitiablityChart = profitiablityChart;
}
private showError(error:any) {
   
    this.httpError = error;
  }
}
