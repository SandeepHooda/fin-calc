import { Component, OnInit, ViewEncapsulation  } from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {CorpAnalysisService} from './CorpAnalysis-service';
import {Message} from 'primeng/primeng';
import {StockAnalysisVO} from './StockAnalysisVO';
import {DataSets} from '../Charts/DataSets';
import {PriceVO} from '../Charts/PriceVO';
import {SelectItem} from 'primeng/primeng';
import {ProFitabilityVO} from './ProFitabilityVO';
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
  private profitabilityAllTimesData : Array<ProFitabilityVO> = [];
  private profitiablityChart : PriceVO;
  private revenueChart : PriceVO;
  private profitChart : PriceVO;
  private profitiablityChartAllStocks : PriceVO;
  private revenueChartAllStocks : PriceVO;
  private profitChartAllStocks : PriceVO;
  private top10 : SelectItem[];
  private maxYearsArray : SelectItem[];
  private top10Selection : string;
  private maxYears : number= 3;
  private categories: SelectItem[];
 constructor( private service : CorpAnalysisService) {
  this.top10 = [
    {label:'Top 10 profitablility', value:'Top 10 profitablility'},
    {label:'Bottom 10 profitablility', value:'Bottom 10 profitablility'},
    {label:'Top 10 revenue', value:'Top 10 revenue'},
    {label:'Bottom 10 revenue', value:'Bottom 10 revenue'},
    {label:'Top 10 profit', value:'Top 10 profit'},
    {label:'Bottom 10 profit', value:'Bottom 10 profit'},
    {label:'All stocks', value:'All stocks'}
    
    ];
    this.maxYearsArray = [
      {label:'3 years', value:3},
      {label:'4 years', value:4},
      {label:'5 years', value:5},
      {label:'6 years', value:6},
      {label:'7 years', value:7},
      {label:'8 years', value:8},
      {label:'9 years', value:9},
      {label:'10 years', value:10},
      {label:'11 years', value:11},
      {label:'12 years', value:12},
      {label:'13 years', value:13},
      {label:'14 years', value:14},
      {label:'15 years', value:15}
    ];

    this.categories = [];
    
 }

  ngOnInit(): void {
    
    if (localStorage.getItem('top10Selection')){
      this.top10Selection = localStorage.getItem('top10Selection');
    }else {
      this.top10Selection = 'Top 10 profitablility';
    }

    if (localStorage.getItem('maxYears')){
      this.maxYears = Number.parseInt(localStorage.getItem('maxYears'));
    }else {
      this.maxYears = 5;
    }
    this.getAnalysis();
  }
 
private getAnalysis() {
 this.service.getAnalysis(this.maxYears).subscribe( 
    corpAnalysis => this.showAnalysis(corpAnalysis),
        error => this.showError(error)
      );
}
private maxYearsChanged(){
  localStorage.setItem('maxYears',''+this.maxYears);
  this.getAnalysis();
}

private calculateYOYPercentage(corpAnalysis:Array<StockAnalysisVO>){

  for (let i=0;i< corpAnalysis.length;i++){
    if (corpAnalysis[i].PBDITMargin.length > this.maxYears){
      corpAnalysis[i].PBDITMargin =corpAnalysis[i].PBDITMargin.slice(corpAnalysis[i].PBDITMargin.length-this.maxYears);
      corpAnalysis[i].PBDITMarginYOY = [];
      for (let j=0; j<corpAnalysis[i].PBDITMargin.length;j++){
        let baseVal: number = corpAnalysis[i].PBDITMargin[0];
        let val = corpAnalysis[i].PBDITMargin[j];
        corpAnalysis[i].PBDITMarginYOY.push((val-baseVal)/baseVal*100);
      }
    }
    if (corpAnalysis[i].PBDITPerShare.length > this.maxYears){
      corpAnalysis[i].PBDITPerShare = corpAnalysis[i].PBDITPerShare.slice(corpAnalysis[i].PBDITPerShare.length-this.maxYears);
      corpAnalysis[i].PBDITPerShareYOY = [];
      for (let j=0; j<corpAnalysis[i].PBDITPerShare.length;j++){
        let baseVal: number = corpAnalysis[i].PBDITPerShare[0];
        let val = corpAnalysis[i].PBDITPerShare[j];
        corpAnalysis[i].PBDITPerShareYOY.push((val-baseVal)/baseVal*100);
      }
    }
    if (corpAnalysis[i].revenueOperationPerShare.length > this.maxYears){
      corpAnalysis[i].revenueOperationPerShare =corpAnalysis[i].revenueOperationPerShare.slice(corpAnalysis[i].revenueOperationPerShare.length-this.maxYears);
      corpAnalysis[i].revenueOperationPerShareYOY = [];
      for (let j=0; j<corpAnalysis[i].revenueOperationPerShare.length;j++){
        let baseVal: number = corpAnalysis[i].revenueOperationPerShare[0];
        let val = corpAnalysis[i].revenueOperationPerShare[j];
        corpAnalysis[i].revenueOperationPerShareYOY.push((val-baseVal)/baseVal*100);
      }
    }
    
    
  }
  
  //maxYears
  //for (let i=0;i<this.this.corpAnalysis)
}

private profitabilityAllTimes(corpAnalysis:Array<StockAnalysisVO>){
  let profitabilityAllTimesData : Array<ProFitabilityVO> = [];
  let allcat : Array<string> = [];
  //this.profitabilityAllTimesData
  let profilabilityRow : ProFitabilityVO;//["Script", "Profitability","ROC Y","ROC Y+1","ROC Y+2","ROC Y+3","ROC Y+4"];
  //Save current year profitability and rate of change in profitability
  for (let i=0; i<corpAnalysis.length;i++){
    
    profilabilityRow = new ProFitabilityVO();
    
    profilabilityRow.name = (corpAnalysis[i].companyName.replace("/","")); //script
    profilabilityRow.category = (corpAnalysis[i].category); //category
    if (allcat.indexOf(profilabilityRow.category) <0){
      allcat.push(profilabilityRow.category);
      this.categories.push({label: profilabilityRow.category, value: profilabilityRow.category});
    }
    
    
    profilabilityRow.margin = (corpAnalysis[i].PBDITMargin[corpAnalysis[i].PBDITMargin.length-1]); //current year Profitability
    let baseVal: number = corpAnalysis[i].PBDITMargin[0];
    profilabilityRow.marginROC1 = (corpAnalysis[i].PBDITMargin[1]-baseVal)/baseVal*100;
    profilabilityRow.marginROC2 = (corpAnalysis[i].PBDITMargin[2]-baseVal)/baseVal*100;
    profilabilityRow.marginROC3 = (corpAnalysis[i].PBDITMargin[3]-baseVal)/baseVal*100;
    profilabilityRow.marginROC4 = (corpAnalysis[i].PBDITMargin[4]-baseVal)/baseVal*100;
    
    profitabilityAllTimesData.push(profilabilityRow);
  }
  this.profitabilityAllTimesData = profitabilityAllTimesData;
  
}
private showAnalysis(corpAnalysis:Array<StockAnalysisVO>){
  this.profitabilityAllTimes(corpAnalysis);

this.calculateYOYPercentage(corpAnalysis);
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
 this.profitiablityChartAllStocks = profitiablityChart;
 this.revenueChartAllStocks = revenueChart;
 this.profitChartAllStocks = profitChart;

 
 this.filterChartDataOnTop10Selection();
}

private top10Changed(){
  localStorage.setItem('top10Selection',''+this.top10Selection);
  this.filterChartDataOnTop10Selection();
}

private filterChartDataOnTop10Selection(){
  // /maxYears

  this.profitiablityChart.datasets.sort(this.compare);
  this.revenueChart.datasets.sort(this.compare);
  this.profitChart.datasets.sort(this.compare);
 
  this.profitiablityChartAllStocks.datasets.sort(this.compare);
  this.revenueChartAllStocks.datasets.sort(this.compare);
  this.profitChartAllStocks.datasets.sort(this.compare);


  let profitiablityChart : PriceVO = new PriceVO();
  let revenueChart : PriceVO = new PriceVO();
  let profitChart : PriceVO= new PriceVO();
  profitiablityChart.labels =this.profitiablityChart.labels;
  revenueChart.labels = this.profitiablityChart.labels;
  profitChart.labels = this.profitiablityChart.labels;
  profitiablityChart.datasets = [];
  revenueChart.datasets = [];
  profitChart.datasets = [];


  
  let filterScriptsNames : string[] = [];
  for (let i=0;i<10;i++){
    if (this.top10Selection === 'Top 10 profitablility'){
      filterScriptsNames.push(this.profitiablityChartAllStocks.datasets[i].label);
    }else
    if (this.top10Selection === 'Bottom 10 profitablility'){
      let index = this.profitiablityChartAllStocks.datasets.length - i-1;
      filterScriptsNames.push(this.profitiablityChartAllStocks.datasets[index].label);
    }else
    if (this.top10Selection === 'Top 10 revenue'){
      filterScriptsNames.push(this.revenueChartAllStocks.datasets[i].label);
    }else
    if (this.top10Selection === 'Bottom 10 revenue'){
      let index = this.profitiablityChartAllStocks.datasets.length - i-1;
      filterScriptsNames.push(this.revenueChartAllStocks.datasets[index].label);
    }else
    if (this.top10Selection === 'Top 10 profit'){
      filterScriptsNames.push(this.profitChartAllStocks.datasets[i].label);
    }else
    if (this.top10Selection === 'Bottom 10 profit'){
      let index = this.profitiablityChartAllStocks.datasets.length - i-1;
      filterScriptsNames.push(this.profitChartAllStocks.datasets[index].label);
    } 
    
    
  }
  
  if (this.top10Selection === 'All stocks'){
    for (let i=0;i<this.profitiablityChartAllStocks.datasets.length;i++){
      profitiablityChart.datasets.push(this.profitiablityChartAllStocks.datasets[i]);
      revenueChart.datasets.push(this.revenueChartAllStocks.datasets[i]);
      profitChart.datasets.push(this.profitChartAllStocks.datasets[i]);
    }
    
  }else {
    for (let i=0;i<this.profitiablityChartAllStocks.datasets.length;i++){
      if (filterScriptsNames.indexOf(this.profitiablityChartAllStocks.datasets[i].label) >=0){
        profitiablityChart.datasets.push(this.profitiablityChartAllStocks.datasets[i]);
      }
      if (filterScriptsNames.indexOf(this.revenueChartAllStocks.datasets[i].label) >=0){
        revenueChart.datasets.push(this.revenueChartAllStocks.datasets[i]);
      }
      if (filterScriptsNames.indexOf(this.profitChartAllStocks.datasets[i].label) >=0){
        profitChart.datasets.push(this.profitChartAllStocks.datasets[i]);
      }
      
    }
  }
  this.profitiablityChart = profitiablityChart;
  this.revenueChart = revenueChart;
  this.profitChart = profitChart;
}

private  compare(a: DataSets,b: DataSets) {
  return   b.data[b.data.length-1] - a.data[a.data.length-1];
}

//objs.sort(compare);
private showError(error:any) {
   
    this.httpError = error;
  }
}
