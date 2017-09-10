import { Injectable, Inject } from '@angular/core';
import { Http, Response }          from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import  'rxjs/add/operator/map';
import { Headers, RequestOptions } from '@angular/http';
import {Company} from './Company';
import {Portfolio} from './portfolio';
import { DOCUMENT } from '@angular/platform-browser';

@Injectable()
export class FundService {
    private  m_names = new Array("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
      private hostName:string = '';

  private allFunds:string = '/web/data/navdata.json';  // URL to web API
   private getPortfolioUrl:string = '/GetProfiles';  // URL to web API
 
     constructor (private http: Http, @Inject(DOCUMENT) document: any) {}
   
    getAllFunds(): Observable<Array<Company>> {
       return this.http.get(this.allFunds)
                        .map(this.extractData)
                        .catch(this.handleError);
    }
loadChartData () :Observable<string> {
   return this.http.get("/ChartData?noOfMonths=12").map(this.getChartData).catch(this.handleError);
  
}
private getChartData (res: Response){
   return "Done";
 }
 public   getPortfolio(refresh : boolean) : Observable<Portfolio>{
   
   if (document.location.href.indexOf("localhost") > 0){
    this.hostName = "http://localhost:8888"
   }else {
     this.hostName  = '';
   }
   if (refresh){
  return this.http.get(this.hostName+this.getPortfolioUrl+"?a="+Math.random())
                        .map(this.extractData)
                        .catch(this.handleError);
   }else {
  return this.http.get(this.hostName+this.getPortfolioUrl)
                        .map(this.extractData)
                        .catch(this.handleError);
   }
 
}

  private extractData(res: Response) {
    let body = res.json();
    return body;
  }
 
 public deleteProfile(profileID : number) : Observable<string>{
  if (document.location.href.indexOf("localhost") > 0){
    this.hostName = "http://localhost:8888"
   }else {
     this.hostName  = '';
   }
   return this.http.delete( this.hostName+'/DeleteProfile?profileID='+profileID)
            .map(this.extractData)
            .catch(this.handleError);
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