import { Component, OnInit ,ViewEncapsulation} from '@angular/core';
import {Message} from 'primeng/primeng';
import {ChartVO} from './ChartVO'
import {ChartNAV} from './ChartNAV';
import {ChartDataSets} from './ChartDataSets';
import {chartData} from './chartData';
import {ChartService} from './chart.service';
@Component({
 selector : 'chart', 
  templateUrl: './chart.component.html',
  encapsulation: ViewEncapsulation.None 
})

export class Chart implements OnInit {

private chartJson : Array<ChartVO> = new Array<ChartVO>();
    private data: chartData = new chartData();
    msgs: Message[];

    constructor(private chartService : ChartService) {
       
        
    
    }

    selectData(event: any) {
        
    }

     private showError(error:any) {
    //this.httpError = error;
    //this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
  }
     ngOnInit(): void {
         this.chartService.getHouseChartData().subscribe( 
        charData => this.showChart(charData),
        error => this.showError(error));

  
  }

  private showChart(charData : Array<ChartVO>){
     let houseData: chartData = new chartData();
    this.chartJson = charData;

         let borderColor : Array<string> = ["#000000","#c0c0c0","#800000", "#ff0000","#800080","#ff00ff", "#008000","#00ff00","#808000", "#ffff00","#000080","#0000ff", "#00ffff","#ffa500","#006400"];
         let chartNAV : Array<ChartNAV> = this.chartJson[0].navs;
       
        for (let i=0;i<chartNAV.length;i++){
            houseData.labels.push(chartNAV[i].dt);
        }
        let colorID : number = 0;
        for (let i=0;i<this.chartJson.length;i++){
            let chartVO : ChartVO = this.chartJson[i];
            let dataSet : ChartDataSets = new ChartDataSets();
            dataSet.label = chartVO._id;
            
            dataSet.borderColor = borderColor[colorID];
            colorID++;
            if (colorID> 14){
                colorID = 0;
            }
            for (let j=0;j<chartVO.navs.length;j++){
                let charNAV : ChartNAV = chartVO.navs[j];
                dataSet.data.push(charNAV.bpi);
            }
           houseData.datasets.push(dataSet);
        }
     console.log("Size of char data"+houseData.datasets.length)
     console.log("Size of char data"+houseData.datasets[0].data.length)
     this.data = houseData;

    }
}