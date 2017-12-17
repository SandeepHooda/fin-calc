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
  private profitiablityChart : PriceVO;
  private revenueChart : PriceVO;
  private profitChart : PriceVO;
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
let profitiablityChart : PriceVO = new PriceVO();
profitiablityChart.labels = [];
profitiablityChart.datasets = [];

let revenueChart : PriceVO = new PriceVO();
revenueChart.labels = [];
revenueChart.datasets = [];

let profitChart : PriceVO = new PriceVO();
profitChart.labels = [];
profitChart.datasets = [];

let i = this.corpAnalysis[0].maxCaptureYear- this.corpAnalysis[0].PBDITMarginYOY.length+1;
 for (;i<= this.corpAnalysis[0].maxCaptureYear;i++){
  profitiablityChart.labels.push(""+i);
 }
 revenueChart.labels = profitiablityChart.labels;
 profitChart.labels = profitiablityChart.labels;
 for (let index: number=0;index < this.corpAnalysis.length;index++){
   let profitabilityDataSet : DataSets = new DataSets();
   profitabilityDataSet.borderColor =  this.corpAnalysis[index].borderColor;
   profitabilityDataSet.label = this.corpAnalysis[index].companyName.replace("/","");
   profitabilityDataSet.data = this.corpAnalysis[index].PBDITMarginYOY;
   profitiablityChart.datasets.push(profitabilityDataSet);

   let revenueDataSet : DataSets = new DataSets();
   revenueDataSet.borderColor =  this.corpAnalysis[index].borderColor;
   revenueDataSet.label = this.corpAnalysis[index].companyName.replace("/","");
   revenueDataSet.data = this.corpAnalysis[index].revenueOperationPerShareYOY;
   revenueChart.datasets.push(revenueDataSet);

   let profitDataSet : DataSets = new DataSets();
   profitDataSet.borderColor =  this.corpAnalysis[index].borderColor;
   profitDataSet.label = this.corpAnalysis[index].companyName.replace("/","");
   profitDataSet.data = this.corpAnalysis[index].PBDITPerShareYOY;
   profitChart.datasets.push(profitDataSet);
 }
 this.profitiablityChart = profitiablityChart;
 this.revenueChart = revenueChart;
 this.profitChart = profitChart;
}
private showError(error:any) {
   
    this.httpError = error;
  }
}
