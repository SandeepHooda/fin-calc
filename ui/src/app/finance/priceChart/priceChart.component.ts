import { Component, OnInit, ViewEncapsulation , ViewChild, Input, ElementRef, Renderer  } from '@angular/core';
import {Message} from 'primeng/primeng';
import {chartData} from '../addFund/chart/chartData';
import {ChartDataSets} from '../addFund/chart/ChartDataSets';
import {PriceChartService} from  './priceChart.service'
import {CurrentPriceVO} from './CurrentPriceVO';
import {CurrentPrice} from './CurrentPrice';
import {TickerDBData} from './TickerDBData';
@Component({
 selector : 'price-chart', 
  templateUrl: './priceChart.component.html',
  encapsulation: ViewEncapsulation.None 
})

export class PriceChart implements OnInit {
    @ViewChild('spinnerElement') spinnerElement: ElementRef;
    private msgs : Message[] = [];
    private eqChart: chartData = new chartData();
    private mfChart: chartData = new chartData();
    private stockTrend :Array<TickerDBData> = [];
    
    constructor( private renderer: Renderer, private priceChartService : PriceChartService) {
   }
    ngOnInit(): void {
        this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','block');
        /*this.priceChartService.getChartDataUIForMyProfile(true).subscribe( 
            charData => this.showChartDataUIForMyProfile(charData),
                error => this.showError(error)); */
        this.priceChartService.getStocksTrend(true).subscribe( 
             trend => this.showStockTrend(trend),
                error => this.showError(error));
                       
    }

    private showStockTrend(trend :Array<TickerDBData>){
        this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
         this.stockTrend = trend;
           
    }
    private showChartDataUIForMyProfile(chartDataArray : Array<chartData>){
         this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
          
              this.eqChart = chartDataArray[0];
              this.mfChart = chartDataArray[1];
     }

    private showError(error:any) {
        this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
      }
      
    private toggleInfo() {
        
        if ( this.msgs.length ==0){
          this.msgs.push({severity:'info', summary:'Info: ', detail:"Analyze % shift in your portfolio."});
        }else {
          this.msgs = [];
        }
        
    }


}