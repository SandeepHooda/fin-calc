import { Component, OnInit ,ViewEncapsulation, ViewChild, Input, ElementRef, Renderer} from '@angular/core';
import {Message, SelectItem} from 'primeng/primeng';
import {ChartVO} from './ChartVO'
import {ChartNAV} from './ChartNAV';
import {ChartDataSets} from './ChartDataSets';
import {chartData} from './chartData';
import {ChartService} from './chart.service';
import {Company} from '../company';

@Component({
 selector : 'chart', 
  templateUrl: './chart.component.html',
  encapsulation: ViewEncapsulation.None 
})

export class Chart implements OnInit {
    private houseCodes : any = {"Top Performers":"AllHouses", "ABN  AMRO Mutual Fund":"39", "AEGON Mutual Fund":"50", "Alliance Capital Mutual Fund":"1", "Axis Mutual Fund":"53", "Baroda Pioneer Mutual Fund":"4", "Benchmark Mutual Fund":"36", "Birla Sun Life Mutual Fund":"3", "BNP Paribas Mutual Fund":"59", "BOI AXA Mutual Fund":"46", "Canara Robeco Mutual Fund":"32", "Daiwa Mutual Fund":"60", "DBS Chola Mutual Fund":"31", "Deutsche Mutual Fund":"38", "DHFL Pramerica Mutual Fund":"58", "DSP BlackRock Mutual Fund":"6", "Edelweiss Mutual Fund":"47", "Escorts Mutual Fund":"13", "Fidelity Mutual Fund":"40", "Fortis Mutual Fund":"51", "Franklin Templeton Mutual Fund":"27", "GIC Mutual Fund":"8", "Goldman Sachs Mutual Fund":"49", "HDFC Mutual Fund":"9", "HSBC Mutual Fund":"37", "ICICI Prudential Mutual Fund":"20", "IDBI Mutual Fund":"57", "IDFC Mutual Fund":"48", "IIFCL Mutual Fund (IDF)":"68", "IIFL Mutual Fund":"62", "IL&F S Mutual Fund":"11", "IL&FS Mutual Fund (IDF)":"65", "Indiabulls Mutual Fund":"63", "ING Mutual Fund":"14", "JM Financial Mutual Fund":"16", "JPMorgan Mutual Fund":"43", "Kotak Mahindra Mutual Fund":"17", "L&T Mutual Fund":"56", "LIC Mutual Fund":"18", "Mahindra Mutual Fund":"69", "Mirae Asset Mutual Fund":"45", "Morgan Stanley Mutual Fund":"19", "Motilal Oswal Mutual Fund":"55", "Peerless Mutual Fund":"54", "PineBridge Mutual Fund":"44", "PNB Mutual Fund":"34", "PPFAS Mutual Fund":"64", "PRINCIPAL Mutual Fund":"10", "Quantum Mutual Fund":"41", "Sahara Mutual Fund":"35", "SBI Mutual Fund":"22", "Shinsei Mutual Fund":"52", "Shriram Mutual Fund":"67", "SREI Mutual Fund (IDF)":"66", "Standard Chartered Mutual Fund":"2", "SUN F&C Mutual Fund":"24", "Sundaram Mutual Fund":"33", "Tata Mutual Fund":"25", "Taurus Mutual Fund":"26", "Union Mutual Fund":"61", "UTI Mutual Fund":"28", "Zurich India Mutual Fund":"29", "Reliance Mutual Fund":"21", "Invesco Mutual Fund":"42"};
    private companyNames : SelectItem[];
    private houseProfiles: Object = {};
     private allHouseProfiles: Object = {};
    private allFunds  :Array<Company>;
    private selectedHouse : string ="Top Performers";
    private m_names : Array<String> = new Array("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
    private chartJson : Array<ChartVO> = new Array<ChartVO>();
    private data: chartData = new chartData();
    private range : SelectItem[] = [];
    private selectedRange : string = "&schemeCountFrom=1&schemeCountTo=10";
    msgs: Message[];
@ViewChild('spinnerElement') spinnerElement: ElementRef;
    constructor(private chartService : ChartService, private renderer: Renderer) {
   }

    selectData(event: any) {
      
    }

     private showError(error:any) {
    //this.httpError = error;
    this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
  }
     ngOnInit(): void {
          this.allFunds = JSON.parse(localStorage.getItem('allFunds'));
         this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none'); 
        this.companyNames = JSON.parse(localStorage.getItem('companyNames'));
        this.companyNames.splice(0, 0, {label:"Top Performers",value:"Top Performers"});
        
        this.range.push({label:"1 - 10", value:"&schemeCountFrom=1&schemeCountTo=10"});
        this.range.push({label:"11 - 20", value:"&schemeCountFrom=11&schemeCountTo=20"});
        this.range.push({label:"21 - 30", value:"&schemeCountFrom=21&schemeCountTo=30"});
        this.range.push({label:"31- 40", value:"&schemeCountFrom=31&schemeCountTo=40"});
        this.range.push({label:"41 - 50", value:"&schemeCountFrom=41&schemeCountTo=50"});
        this.range.push({label:"51 - 60", value:"&schemeCountFrom=51&schemeCountTo=60"});
        this.range.push({label:"61 - 70", value:"&schemeCountFrom=61&schemeCountTo=70"});
        this.range.push({label:"71 - 80", value:"&schemeCountFrom=71&schemeCountTo=80"});
        this.range.push({label:"81- 90", value:"&schemeCountFrom=81&schemeCountTo=90"});
        this.range.push({label:"91 - 100", value:"&schemeCountFrom=91&schemeCountTo=100"});
        

        for (let i=0;i< this.allFunds.length;i++){
            let companyProfiles = this.allFunds[i].navs;
        for (let j=0;j<companyProfiles.length;j++){
            this.allHouseProfiles[companyProfiles[j].SchemeCode] = companyProfiles[j].SchemeName;
        }
        }
        
  }
  private fetchData (){
      this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','block');
     let houseCode = "AllHouses";
     
    if (this.selectedHouse != "Top Performers"){
        this.houseProfiles  = {};
        
         let houseID = parseInt(this.selectedHouse.substring(0,this.selectedHouse.indexOf("#")));
         let houseName = this.selectedHouse.substring(this.selectedHouse.indexOf("#")+1);
         houseCode = this.houseCodes[houseName];
         let companyProfiles = this.allFunds[houseID].navs;
        for (let i=0;i<companyProfiles.length;i++){
            this.houseProfiles[companyProfiles[i].SchemeCode] = companyProfiles[i].SchemeName;
        }
       
        
        
    }
    this.chartService.getHouseChartData(houseCode, this.selectedRange).subscribe( 
        charData => this.showChart(charData),
        error => this.showError(error)); 
       
  }
  
private  monthDiff(d1 : Date, d2 : Date) {
    return (d2.getFullYear() - d1.getFullYear()) * 12 + d2.getMonth() - d1.getMonth() ;
    
}
  private showChart(charData : Array<ChartVO>){
      this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
     let houseData: chartData = new chartData();
    this.chartJson = charData;

         let borderColor : Array<string> = ["#000000","#c0c0c0","#800000", "#ff0000","#800080","#ff00ff", "#008000","#00ff00","#808000", "#ffff00","#000080","#0000ff", "#00ffff","#ffa500","#006400"];
         let chartNAV : Array<ChartNAV> = this.chartJson[0].navs;
         let totalLables = chartNAV.length;
         let today : Date = new Date( );
         let labelStartDate : Date = new Date(today.getFullYear() -3, today.getMonth(), 1 );
          let labelEndtDate : Date = new Date(today.getFullYear() , today.getMonth()-1, 1 );
          let labelDate : Date = new Date(today.getFullYear() -3, today.getMonth(), 1 );
        while(labelDate.getTime() <= labelEndtDate.getTime()){
            houseData.labels.push((labelDate.getMonth() +1)+"/"+labelDate.getFullYear());
            labelDate.setMonth(labelDate.getMonth()+1);
        }
            
        
        let colorID : number = 0;
        for (let i=0;i<this.chartJson.length;i++){
            let chartVO : ChartVO = this.chartJson[i];
            let dataSet : ChartDataSets = new ChartDataSets();
            //if (this.selectedHouse == "Top Performers"){
                dataSet.label = this.allHouseProfiles[chartVO._id] || chartVO._id;
           /* }else {
                 dataSet.label = this.houseProfiles[chartVO._id];
            }*/
            
            
            dataSet.borderColor = borderColor[colorID];
            colorID++;
            if (colorID> 14){
                colorID = 0;
            }
            let navStartDate = new Date(parseInt(chartVO.navs[0].dt.substr(7,4)),
            this.m_names.indexOf(chartVO.navs[0].dt.substr(3,3)), parseInt(chartVO.navs[0].dt.substr(0,2)));
            let blankNavsPrefill : number = this.monthDiff(labelStartDate,navStartDate);
            while(blankNavsPrefill > 0){
                blankNavsPrefill --;
                dataSet.data.push(0);
            }
            let navSize = chartVO.navs.length;
            for (let j=0;j<navSize;j++){
                let charNAV : ChartNAV = chartVO.navs[j];
                dataSet.data.push(charNAV.bpi);
            }

            let navEndDate = new Date(parseInt(chartVO.navs[navSize-1].dt.substr(7,4)),
            this.m_names.indexOf(chartVO.navs[navSize-1].dt.substr(3,3)), parseInt(chartVO.navs[navSize-1].dt.substr(0,2)));
            let blankNavsPostfill : number = this.monthDiff(navEndDate, labelEndtDate);
            while(blankNavsPostfill > 0){
                blankNavsPostfill --;
                dataSet.data.push(0);
            }
           houseData.datasets.push(dataSet);
        }

     this.data = houseData;

    }
}