import { Injectable } from '@angular/core';
import { Http, Response }          from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import  'rxjs/add/operator/map';
import { Headers, RequestOptions } from '@angular/http';
import {CurrentMarketPrice} from './CurrentMarketPrice';

@Injectable()
export class HighLowSrv {
  private hostName:string = ''//'http://localhost:8888';
  
 

     constructor (private http: Http) {
      if (document.location.href.indexOf("localhost") > 0){
        this.hostName = "http://localhost:8888"
       }else {
         this.hostName  = '';
       }
     }

 
     getAnalysis() :Observable<Array<CurrentMarketPrice>> {
     let url:string = this.hostName+'/getCorpAnalysis?maxYears=';
   return this.http.get(url).map(this.extractResult).catch(this.handleError);
 }

 
  
  private extractJsonData(res: Response) {
    let body = res.status;
    return body;
  }
  private extractResult(res: Response) {
    let body = res.json();
    return body;
  }
 
  private handleError (error: Response | any) {
    // In a real world app, you might use a remote logging infrastructure
    let errMsg: string;
    if (error instanceof Response) {
      const body = error.json() || '';
      const err = body.error || JSON.stringify(body);
      errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    console.error(errMsg);
    return Observable.throw(errMsg);
  }
}