import { Injectable } from '@angular/core';
import { Http, Response }          from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import  'rxjs/add/operator/map';
import { Headers, RequestOptions } from '@angular/http';

@Injectable()
export class XirrService {
  private hostName:string = '';
  private signInUrl:string = this.hostName+'/getProfile?data=name';
  private signInUrlEmail:string = this.hostName+'/getProfile?data=email';

     constructor (private http: Http) {}

 
 signedUserName() :Observable<string> {
   return this.http.get(this.signInUrl).map(this.getUserName).catch(this.handleError);
 }
loadChartData () :Observable<string> {
   return this.http.get("/ChartData?noOfMonths=36").map(this.getChartData).catch(this.handleError);
  
}
 signedUserEmail() :Observable<string> {
   return this.http.get(this.signInUrlEmail).map(this.getUserName).catch(this.handleError);
 }
  
  private getUserName(res: Response) {
    let body = res.json().data;
    return body;
  }
 private getChartData (res: Response){
   return "Done";
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