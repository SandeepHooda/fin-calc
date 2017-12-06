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
  private data: any;
  private maxDays : number;
  private chartDays : SelectItem[] ;

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
 }

  ngOnInit(): void {
    if (localStorage.getItem('maxDays')){
      this.maxDays = parseInt(localStorage.getItem('maxDays'));
    }
    this.getChartsData(this.maxDays);
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
this.data = priceVO[0];
}
private showError(error:any) {
   
    this.httpError = error;
  }
}
