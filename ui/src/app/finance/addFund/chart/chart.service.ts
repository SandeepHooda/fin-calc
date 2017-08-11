import { Injectable } from '@angular/core';
import { Http, Response }          from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import  'rxjs/add/operator/map';
import { Headers, RequestOptions } from '@angular/http';
import {ChartVO} from './ChartVO'
import {ChartNAV} from './ChartNAV';

@Injectable()
export class ChartService {
   
      private hostName:string = '';

  private charUITopPerformer:string = "/TopFundOfAllHouses?schemeCountFrom=1&schemeCountTo=10"

  
     constructor (private http: Http) {}
   
    getHouseChartData(houseCode : string, range: string): Observable<Array<ChartVO>> {
      let url = "/ChartDataUI?houseCode="+houseCode+range;
      if (houseCode == "AllHouses"){
        url = "/TopFundOfAllHouses?houseCode="+houseCode+range;
      }
       return this.http.get(this.hostName+url)
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