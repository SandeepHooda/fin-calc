import { Injectable } from '@angular/core';
import { Http, Response }          from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import  'rxjs/add/operator/map';
import { Headers, RequestOptions } from '@angular/http';
import {StockVO} from './stockVO';
import {StockPortfolioVO} from './StockPortfolioVO';
@Injectable()
export class StockService {
     private hostName:string = '';

  private getAllListedStocksUrl:string = '/web/data/allListedStocks.json';  // URL to web API
  private addStockToProfileUrl:string = '/AddStock';
  private GetStockProfileUrl:string = '/GetStockProfile';
  
 
     constructor (private http: Http) {}
   
    getAllListedStocks(): Observable<Array<StockVO>> {
       return this.http.get(this.getAllListedStocksUrl)
                        .map(this.extractData)
                        .catch(this.handleError);
    }
    getStockProfile(): Observable<StockPortfolioVO> {
       return this.http.get(this.hostName+this.GetStockProfileUrl)
                        .map(this.extractData)
                        .catch(this.handleError);
    }
    deleteFromProfile(profileID : number) : Observable<string>{
      return this.http.delete(this.hostName+'/DeleteStockFromProfile?profileID='+profileID)
            .map(this.extractData)
            .catch(this.handleError);
    }
  private extractData(res: Response) {
    let body = res.json();
    return body;
  }
 
 public addStockToProfile(selectedStock : StockVO) : Observable<string>{
     var headers = new Headers({ 'Content-Type': 'application/json' });
  var options = new RequestOptions({ headers: headers });
  return this.http.post(this.hostName+this.addStockToProfileUrl, { selectedStock }, options)
            .map(this.extractData)
            .catch(this.handleError)
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