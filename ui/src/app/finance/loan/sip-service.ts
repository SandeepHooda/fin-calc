import { Injectable } from '@angular/core';
import { Http, Response }          from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import  'rxjs/add/operator/map';
import { Headers, RequestOptions } from '@angular/http';
import {SipSchemeVO} from './scheme/sip.schemeVO';

@Injectable()
export class LoanService {
  private hostName:string = '';
  private getSipListUrl:string = this.hostName+'/GetLoanList';
  private saveSipListUrl:string = this.hostName+'/SaveLoanList';

     constructor (private http: Http) {}

 
 getSipList() :Observable<Array<SipSchemeVO>> {
   return this.http.get(this.getSipListUrl).map(this.extractResult).catch(this.handleError);
 }

 saveSipList( sipList : Array<SipSchemeVO>) :Observable<string> {
   console.log(" calling post method");
   var headers = new Headers({ 'Content-Type': 'application/json' });
  var options = new RequestOptions({ headers: headers });
    return this.http.post(this.saveSipListUrl, { sipList }, options)
                    .map(this.extractJsonData)
                    .catch(this.handleError);
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