import { Component, OnInit, ViewEncapsulation  } from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {ChartsService} from './Charts-service';
import {Message} from 'primeng/primeng';
import {PriceVO} from './PriceVO'
import {SelectItem} from 'primeng/primeng';
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
  private comparisionData: any;
  private maxDays : number;
  private chartDays : SelectItem[] ;
  private top5 : SelectItem[];
  private tickers: SelectItem[] = [] ;
  private selectedTickers: string[] = [];
  private top5Selection : string;
  private fromDate : Date;
  private toDate : Date;
 constructor( private service : ChartsService) {
  this.chartDays = [
    {label:'2', value:2},
    {label:'5', value:5},
    {label:'10', value:10},
    {label:'30', value:30},
    {label:'90', value:90},
    {label:'180', value:180},
    {label:'365', value:365}
    ];
    this.top5 = [
      {label:'Top 5 winners', value:'Top 5 winners'},
      {label:'Top 5 loosers', value:'Top 5 loosers'},
      {label:'All stocks', value:'All stocks'}
      
      ];
 }

  ngOnInit(): void {
    if (localStorage.getItem('maxDays')){
      this.maxDays = parseInt(localStorage.getItem('maxDays'));
    }
    if (localStorage.getItem('top5Selection')){
      this.top5Selection = localStorage.getItem('top5Selection');
    }else {
      this.top5Selection = 'Top 5 winners';
    }
    
    this.getChartsData(this.maxDays);

    
  }
  private clearToDate(){
    this.toDate = null;
  }
 

  private plotDateRangeChart() {
    this.service.plotDateRangeChart(this.fromDate, this.toDate).subscribe( 
       priceVO => this.showChart(priceVO),
           error => this.showError(error)
         );
   }
  private top5Changed(){
    
    localStorage.setItem('top5Selection',''+this.top5Selection);
    this.addToComparision();
    this.filterBasedOnTop5Selection();
  }
  private filterBasedOnTop5Selection(){
    
    if ( this.comparisionData.datasets && this.comparisionData.datasets.length > 5){
      this.comparisionData.datasets.sort(
        function(a :any, b:any){
          return a.data[a.data.length-1]-b.data[b.data.length-1]
        });
      if (this.top5Selection == 'Top 5 loosers'){
        this.comparisionData.datasets= this.comparisionData.datasets.slice(0, 5);
      }else if (this.top5Selection == 'Top 5 winners'){
        let size = this.comparisionData.datasets.length;
        this.comparisionData.datasets= this.comparisionData.datasets.slice(size-5, size);
      } 
    }
  }
private dateRangeChanged(){
  localStorage.setItem('maxDays',''+this.maxDays);
  this.getChartsData(this.maxDays);
}
private getChartsData(maxDays : number) {
 this.service.getChartsData(maxDays).subscribe( 
    priceVO => this.showChart(priceVO),
        error => this.showError(error)
      );
}

private showChart(priceVO:Array<PriceVO>){
this.priceVO = priceVO;
this.tickers = [];
for (let i=0;i<this.priceVO.length;i++){
  this.tickers.push({label: this.priceVO[i].datasets[0].label, value: this.priceVO[i].datasets[0].label});
  this.selectedTickers.push(this.priceVO[i].datasets[0].label);
}

this.addAllStocksToComparision();
this.filterBasedOnTop5Selection();
}

private clearSelectedTickers(){
  this.selectedTickers =[];
  this.top5Changed();
}
private allAllToSelectedTickers(){
  for (let i=0;i<this.priceVO.length;i++){
    this.selectedTickers.push(this.priceVO[i].datasets[0].label);
  }
  this.top5Changed();
 
}
private addAllStocksToComparision(){
  this.comparisionData = [];
  let comparisionDatatemp : any = [];
  //if (this.selectedTickers.indexOf(this.priceVO[i].datasets[0].label)>=0){
   
 
  for (let i=0;i<this.priceVO.length;i++){
   
      if (!comparisionDatatemp.labels){
        comparisionDatatemp.labels = this.priceVO[i].labels.slice();
        comparisionDatatemp.datasets = this.priceVO[i].datasets.slice();
      }else {
          comparisionDatatemp.datasets.push(this.priceVO[i].datasets[0]);
     }
   
 
  }
 
  this.comparisionData = comparisionDatatemp;
}
private addToComparision(){ 
  this.comparisionData = [];
  let comparisionDatatemp : any = [];

  for (let i=0;i<this.priceVO.length;i++){
    if (this.selectedTickers.indexOf(this.priceVO[i].datasets[0].label)>=0){
      if (!comparisionDatatemp.labels){
        comparisionDatatemp.labels = this.priceVO[i].labels.slice();
        comparisionDatatemp.datasets = this.priceVO[i].datasets.slice();
      }else {
          comparisionDatatemp.datasets.push(this.priceVO[i].datasets[0]);
     }
    }
 
  }
 
  this.comparisionData = comparisionDatatemp;
  this.filterBasedOnTop5Selection();
}
private showError(error:any) {
   
    this.httpError = error;
  }
}
