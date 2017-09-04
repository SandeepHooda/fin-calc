import { Injectable } from '@angular/core';
import { Http, Response }          from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import  'rxjs/add/operator/map';
import { Headers, RequestOptions } from '@angular/http';
import {StockVO} from './stockVO';

@Injectable()
export class StockService {
     private hostName:string = '';

  private getAllListedStocksUrl:string = '/web/data/allListedStocks.json';  // URL to web API

 
     constructor (private http: Http) {}
   
    getAllListedStocks(): Observable<Array<StockVO>> {
       return this.http.get(this.getAllListedStocksUrl)
                        .map(this.extractData)
                        .catch(this.handleError);
    }
  private extractData(res: Response) {
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