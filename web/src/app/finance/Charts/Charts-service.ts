import { Injectable } from '@angular/core';
import { Http, Response }          from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import  'rxjs/add/operator/map';
import { Headers, RequestOptions } from '@angular/http';
import {PriceVO} from './PriceVO'

@Injectable()
export class ChartsService {
  private hostName:string = '';//'http://localhost:8888';
  private url:string = this.hostName+'/getCorpAnalysis';
 

     constructor (private http: Http) {}


     getChartsData() :Observable<Array<PriceVO>> {
   return this.http.get(this.url).map(this.extractResult).catch(this.handleError);
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