import { Component, OnInit, ViewEncapsulation , ViewChild, Input, ElementRef, Renderer  } from '@angular/core';
import {Message} from 'primeng/primeng';
import {chartData} from '../addFund/chart/chartData';
import {ChartDataSets} from '../addFund/chart/ChartDataSets';
import {PriceChartService} from  './priceChart.service'
import {CurrentPriceVO} from './CurrentPriceVO';
import {CurrentPrice} from './CurrentPrice';
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
    
    constructor( private renderer: Renderer, private priceChartService : PriceChartService) {
   }
    ngOnInit(): void {
        this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','block');
        this.priceChartService.getChartDataUIForMyProfile(true).subscribe( 
            charData => this.showChartDataUIForMyProfile(charData),
                error => this.showError(error)); 
          
    }

    private showChartDataUIForMyProfile(CurrentPriceVOList : Array<CurrentPriceVO>){
        
           this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
           let eqChartLocal: chartData = new chartData();
           let mfChartLocal: chartData = new chartData();
           for (let i = 0; i< CurrentPriceVOList.length;i++){
                eqChartLocal.labels.push(CurrentPriceVOList[i]._id);
                mfChartLocal.labels.push(CurrentPriceVOList[i]._id);
            }
            this.eqChart.labels = eqChartLocal.labels;
            this.mfChart.labels = mfChartLocal.labels;
       let borderColor : Array<string> = ["#000000","#c0c0c0","#800000", "#ff0000","#800080","#ff00ff", "#008000","#00ff00","#808000", "#ffff00","#000080","#0000ff", "#00ffff","#ffa500","#006400"];
           let colorID : number = 0;
           let allDatasetsEQ : Array<ChartDataSets> = [];
           let allDatasetsMF : Array<ChartDataSets> = [];
           //allDatasets[""] =  new ChartDataSets();
              for (let i=0;i<CurrentPriceVOList.length;i++){
                for (let j=0;j<CurrentPriceVOList[i].currentPrices.length;j++){
                    let aCompany : CurrentPrice = CurrentPriceVOList[i].currentPrices[j];
                    let aDataSet : ChartDataSets;
                    if (aCompany.type == "EQ"){
                        aDataSet = allDatasetsEQ[aCompany.companyName];
                    }else {
                         aDataSet  = allDatasetsMF[aCompany.companyName];
                    }

                    if (!aDataSet){
                        aDataSet = new ChartDataSets();
                    }
                    aDataSet.label = aCompany.companyName;
                    aDataSet.data.push(aCompany.price);
                    aDataSet.borderColor = borderColor[colorID];
                    colorID++;
                    if (colorID> 14){
                        colorID = 0;
                    }
                    if (aCompany.type == "EQ"){
                         allDatasetsEQ[aCompany.companyName] = aDataSet ;
                    }else {
                         allDatasetsMF[aCompany.companyName] = aDataSet;
                    }
                      
                }
                 
              }
              eqChartLocal.datasets =allDatasetsEQ;
              mfChartLocal.datasets =allDatasetsMF;
              let eqCompanies = Object.keys(allDatasetsEQ);
              let mfCompanies = Object.keys(allDatasetsMF);
              if (eqCompanies){
                for (let i=0;i<eqCompanies.length;i++){
                    eqChartLocal.datasets.push(allDatasetsEQ[eqCompanies[i]]);
                    
                   
                }
              }
              if (mfCompanies){
                for (let i=0;i<mfCompanies.length;i++){
                    mfChartLocal.datasets.push(allDatasetsMF[mfCompanies[i]]);
                    
                }
              }
              
              console.log("MF "+this.mfChart); 
              this.eqChart = eqChartLocal
              this.mfChart =mfChartLocal;
              
          
      
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