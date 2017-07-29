import { Injectable } from '@angular/core';
import { Http, Response }          from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import  'rxjs/add/operator/map';
import { Headers, RequestOptions } from '@angular/http';
import {Profile} from '../profile';
import {Response as ResponseVO} from './response';


@Injectable()
export class NavService {
    private  m_names = new Array("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
      private hostName:string = '';
  private funNavUrl:string = this.hostName+'/GetNav?date=';  // URL to web API
  private addToPortfolioUrl:string = this.hostName+'/AddToProfile';  // URL to web API

 dateToStr(date:Date):string{
    return date.getDate()+"-"+this.m_names[date.getMonth()] +"-"+date.getFullYear();;
 }
     constructor (private http: Http) {}
    getNav(date:Date, companyName:string, schemeCode : string): Observable<number> {
        let today :Date = new Date();
        today.setDate(today.getDate()-1);
        let url =   this.funNavUrl + date.getDate()+"-"+this.m_names[date.getMonth()] +"-"+date.getFullYear();
        url +="&companyName="+companyName +"&schemeCode="+schemeCode;
        return this.http.get(url)
                        .map(this.extractData)
                        .catch(this.handleError);
    }
    
addToPortfolio(profile:Profile) : Observable<ResponseVO>{
  var headers = new Headers({ 'Content-Type': 'application/json' });
  var options = new RequestOptions({ headers: headers });
  return this.http.post(this.addToPortfolioUrl, { profile }, options)
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