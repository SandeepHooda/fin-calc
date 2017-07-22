import { Injectable } from '@angular/core';
import { Http, Response }          from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import  'rxjs/add/operator/map';
import { Headers, RequestOptions } from '@angular/http';
import {Company} from './Company';

@Injectable()
export class FundService {
    private  m_names = new Array("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
      private hostName:string = '';
  private funNavUrl:string = this.hostName+'/GetNavs?date=';  // URL to web API
  private allFunds:string = '/web/navdata.json';  // URL to web API
 
     constructor (private http: Http) {}
    getLatestNav(): Observable<Array<Company>> {
        let today :Date = new Date();
        today.setDate(today.getDate()-1);
        this.funNavUrl += today.getDate()+"-"+this.m_names[today.getMonth()] +"-"+today.getFullYear();
        return this.http.get(this.funNavUrl)
                        .map(this.extractData)
                        .catch(this.handleError);
    }
    getAllFunds(): Observable<Array<Company>> {
       return this.http.get(this.allFunds)
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