import { Injectable } from '@angular/core';
import { Http, Response }          from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import  'rxjs/add/operator/map';
import {XirrRequest} from './xirrRequestVO';
import { Headers, RequestOptions } from '@angular/http';

@Injectable()
export class XirrService {
  private hostName:string = '';
  private xirrUrl:string = this.hostName+'/xirr';  // URL to web API
  private signInUrl:string = this.hostName+'/getProfile?data=email';

     constructor (private http: Http) {}

 
 signedUserEmail() :Observable<string> {
   return this.http.get(this.signInUrl).map(this.getUserEmail).catch(this.handleError);
 }

  getXirr(request: XirrRequest): Observable<number> {
    let headers = new Headers({ 'Content-Type': 'application/json' });
   let options = new RequestOptions({ headers: headers });
    return this.http.post(this.xirrUrl, { request }, options)
                    .map(this.extractData)
                    .catch(this.handleError);
  }

  private extractData(res: Response) {
    let body = res.json();
    return body;
  }
  private getUserEmail(res: Response) {
    let body = res.json().data;
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