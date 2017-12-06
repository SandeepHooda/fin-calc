import { Component, OnInit, ViewEncapsulation  } from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {CorpAnalysisService} from './CorpAnalysis-service';
import {Message} from 'primeng/primeng';
import {StockAnalysisVO} from './StockAnalysisVO';

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
}
private showError(error:any) {
   
    this.httpError = error;
  }
}
