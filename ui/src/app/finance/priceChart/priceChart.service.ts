import { Injectable , Inject} from '@angular/core';
import { Http, Response }          from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import  'rxjs/add/operator/map';
import {CurrentPriceVO} from './CurrentPriceVO';
import { DOCUMENT } from '@angular/platform-browser';

@Injectable()
export class PriceChartService {
    private hostName : string;
    constructor (private http: Http, @Inject(DOCUMENT) document: any) {}
    public getChartDataUIForMyProfile(force : boolean ) : Observable<Array<CurrentPriceVO>>{
        if (document.location.href.indexOf("localhost") > 0){
            this.hostName = "http://localhost:8888"
           }else {
             this.hostName  = '';
           }
           let url = this.hostName+"/GetPortFolioPriceTrend";
           if (force){
             url += "?a="+Math.random();
           }
               return this.http.get(url).map(this.extractData)
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